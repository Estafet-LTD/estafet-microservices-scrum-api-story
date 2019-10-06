@NonCPS
def getVersions(json) {
	def tags = new groovy.json.JsonSlurper().parseText(json).status.tags
	def versions = []
	for (int i = 0; i < tags.size(); i++) {
		versions << tags[i]['tag']
	}
	return versions
}

@NonCPS
def getNamespace(json) {
	def matcher = new groovy.json.JsonSlurper().parseText(json).items[0].spec.to.name =~ /(green|blue)(\-basic\-ui)/
	String namespace = matcher[0][1]
	return namespace.equals("green") ? "prod-blue" : "prod-green" 
}

@NonCPS
def getImageStreamHash(json, version) {
	def tags = new groovy.json.JsonSlurper().parseText(json).status.tags
	for (int i = 0; i < tags.size(); i++) {
		if (tags[i]['tag'].equals(version)) {
			def image = tags[i]['items'][0]['image']
			def matcher = image =~ /(sha256\:)(\w+)/
			return matcher[0][2]
		}
	}
	throw new RuntimeException("cannot find image for version $version")
}

@NonCPS
def getPodImageHash(json) {
	def imageId = new groovy.json.JsonSlurper().parseText(json).items[0].status.containerStatuses[0].imageID
	def matcher = imageId =~ /(.*\@sha256\:)(\w+)/
	return matcher[0][2]
}

def recentVersion( versions ) {
	def size = versions.size()
	return versions[size-1]
}

def getLatestVersion(microservice) {
	sh "oc get is ${microservice} -o json -n staging > image.json"
	def image = readFile('image.json')
	def versions = getVersions(image)
	if (versions.size() == 0) {
		throw new RuntimeException("There are no images for ${microservice}")
	}
	return recentVersion(versions)
}

boolean isLatestVersionDeployed(project, microservice, version) {
	sh "oc get is ${microservice} -o json -n staging > image.json"
	def image = readFile('image.json')
	def imageStreamHash = getImageStreamHash(image, version)
	println "image stream hash $imageStreamHash"
	sh "oc get pods --selector deploymentconfig=${microservice} -n ${project} > exists.json"
	def exists = readFile('exists.json')
	if (exists.indexOf("No resources")>= 0) {
		sh "oc get pods --selector deploymentconfig=${microservice} -n ${project} -o json > pod.json"
		def pod = readFile('pod.json')
		def podImageHash = getPodImageHash(pod)
		println "pod image hash $podImageHash"
		return imageStreamHash.equals(podImageHash)	
	} else {
		return false
	}
}

node("maven") {
	
	def project
	def version
	def microservice = "story-api"

	properties([
	  parameters([
	     string(name: 'GITHUB'),
	  ])
	])
	
	stage("determine the environment to deploy to") {
		sh "oc get route -o json -n live > route.json"
		def route = readFile('route.json')
		project = getNamespace(route)
		println "the target namespace is $project"
	}
	
	stage("determine which image is to be deployed") {
		version = getLatestVersion microservice
		println "latest version is $version"
	}
	
	stage("checkout release version") {
		checkout scm: [$class: 'GitSCM', 
      userRemoteConfigs: [[url: "https://github.com/${params.GITHUB}/estafet-microservices-scrum-api-story"]], 
      branches: [[name: "refs/tags/${version}"]]], changelog: false, poll: false
	}
	
	stage("ensure the exists database") {
		withMaven(mavenSettingsConfig: 'microservices-scrum') {
	      sh "mvn clean package -P create-prod-db -Dmaven.test.skip=true -Dproject=${project}"
	    } 
	}		
	
	stage("create deployment config") {
		sh "oc process -n ${project} -f openshift/templates/${microservice}-config.yml -p NAMESPACE=${project} -p DOCKER_NAMESPACE=staging -p DOCKER_IMAGE_LABEL=${version} | oc apply -f -"
		sh "oc set env dc/${microservice} STORY_API_JDBC_URL=jdbc:postgresql://postgresql.${project}.svc:5432/prod-${microservice} JAEGER_AGENT_HOST=jaeger-agent.${project}.svc JAEGER_SAMPLER_MANAGER_HOST_PORT=jaeger-agent.${project}.svc:5778 JAEGER_SAMPLER_PARAM=1 JAEGER_SAMPLER_TYPE=const -n ${project}"	
	}
	
	stage("execute deployment") {
		if (!isLatestVersionDeployed(project, microservice, version)) {
			openshiftDeploy namespace: project, depCfg: microservice,  waitTime: "3000000"
			openshiftVerifyDeployment namespace: project, depCfg: microservice, replicaCount:"1", verifyReplicaCount: "true", waitTime: "300000" 
		} else {
			println "version $version of $microservice is already deployed and running"
		}
	}

}

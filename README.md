# Estafet Microservices Scrum Story API
Microservices api for managing stories and their lifecycle for the scrum demo application.
## What is this?
This application is a microservice provides an API to add stories to a backlog for a given project and to add these stories to a specified sprint. Acceptance criteria can also be added to stories.

Each microservice has it's own git repository, but there is a master git repository that contains links to all of the repositories [here](https://github.com/Estafet-LTD/estafet-microservices-scrum).
## Getting Started
You can find a detailed explanation of how to install this (and other microservices) [here](https://github.com/Estafet-LTD/estafet-microservices-scrum#getting-started).
## API Interface

### Messaging

|Topic   |Event    |Message Type |
|--------|---------|-------------|
|new.story.topic|When a new story is created, it is published to this topic|Story JSON Object|
|update.story.topic|When an existing story has been modified, it is published to this topic|Story JSON Object|

### Story JSON object

```json
{
    "id": 1,
    "title": "some test story",
    "description": "hghghg",
    "storypoints": 5,
    "sprintId": 1,
    "projectId": 1,
    "criteria": [
        {
            "id": 1,
            "description": "hghghg"
        },
        {
            "id": 2,
            "description": "jhjhjh"
        }
    ],
    "status": "Completed"
}
```

### Restful Operations

To retrieve an example the story object (useful for testing to see the microservice is online).

```
GET http://story-api/story
```

To retrieve a story that has an id = 1

```
GET http://story-api/story/1
```

To add a new story to the backlog of a project. It returns a story object.

```
POST http://story-api/project/1/story
{
	"title" : "my 21st story",
	"description" : "this is my 21st story",
	"storypoints" : "13"
}
```

To add a new acceptance criteria to a story. It returns a story object.

```
POST http://story-api/story/1/criteria
{
	"description": "hghghg"
}
```

To retrieve all of the stories for a project that has an id = 1

```
GET http://story-api/project/1/stories
```

To add a story to a sprint. Returns the story object.

```
POST http://story-api/add-story-to-sprint
{
	"storyId" : "1",
	"sprintId" : "2"
}
```

## Environment Variables
```
JBOSS_A_MQ_BROKER_URL=tcp://localhost:61616
JBOSS_A_MQ_BROKER_USER=estafet
JBOSS_A_MQ_BROKER_PASSWORD=estafet

STORY_API_JDBC_URL=jdbc:postgresql://localhost:5432/story-api
STORY_API_DB_USER=postgres
STORY_API_DB_PASSWORD=welcome1

SPRINT_API_SERVICE_URI=http://localhost:8080/sprint-api
```

## Domain Model States
A story has four states. It can only progress from each state via the specific actions or events illustrated.

![alt tag](https://github.com/Estafet-LTD/estafet-microservices-scrum-api-story/blob/master/StoryStateModel.png)



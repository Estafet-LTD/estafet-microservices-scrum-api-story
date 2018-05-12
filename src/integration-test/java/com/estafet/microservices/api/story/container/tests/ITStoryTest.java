package com.estafet.microservices.api.story.container.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.HttpURLConnection;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class ITStoryTest {

	@Before
	public void before() {
		RestAssured.baseURI = System.getenv("STORY_API_SERVICE_URI");
	}

	@After
	public void after() {
	}

	@Test
	public void testGetAPI() {
		get("/api").then()
			.body("id", is(1))
			.body("title", is("some test story"))
			.body("description", is("hghghg"))
			.body("storypoints", is(5))
			.body("sprintId", is(1))
			.body("projectId", is(1))
			.body("criteria.id", hasItems(1,2))
			.body("criteria.description", hasItems("hghghg","jhjhjh"));
	}

	@Test
	@DatabaseSetup("ITStoryTest-data.xml")
	public void testGetStory() {
		get("/story/1000").then()
			.body("id", is(1000))
			.body("title", is("Story #1"))
			.body("description", is("Story #1"))
			.body("storypoints", is(13))
			.body("projectId", is(1))
			.body("status", is("Not Started"));
	}

	@Test
	@DatabaseSetup("ITStoryTest-data.xml")
	public void testCreateStory() {
		given()
			.contentType(ContentType.JSON)
			.body("{\"title\":\"My Story\",\"description\":\"My Story\",\"storypoints\":5, \"criteria\": [{\"description\": \"Crtieria #1\"}, {\"description\": \"Crtieria #2\"}, {\"description\": \"Crtieria #3\"} ]}")
		.when()
			.post("/project/1/story")
		.then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(1))
			.body("title", is("My Story"))
			.body("description", is("My Story"))
			.body("storypoints", is(5))
			.body("projectId", is(1))
			.body("status", is("Not Started"))
			.body("crtieria.id", hasItems(1,2,3))
			.body("crtieria.description", hasItems("Crtieria #1","Crtieria #2","Crtieria #3"));
	
		get("/story/1").then()
			.body("id", is(1))
			.body("title", is("My Story"))
			.body("description", is("My Story"))
			.body("storypoints", is(5))
			.body("projectId", is(1))
			.body("status", is("Not Started"))
			.body("crtieria.id", hasItems(1,2,3))
			.body("crtieria.description", hasItems("Crtieria #1","Crtieria #2","Crtieria #3"));
	}

	@Test
	@DatabaseSetup("ITStoryTest-data.xml")
	public void testGetStories() {
		get("/project/1/stories").then()
		.body("id", is(1000))
		.body("title", is("Task #1"))
		.body("description", is("Task #1"))
		.body("initialHours", is(13))
		.body("remainingHours", is(13))
		.body("status", is("Not Started"));
	}

	@Test
	@DatabaseSetup("ITStoryTest-data.xml")
	public void testAddAcceptanceCriteria() {
		given()
		.contentType(ContentType.JSON)
		.body("{\"title\":\"Task #3\",\"description\":\"Task #3\",\"initialHours\":5}")
		.when()
			.post("/story/1000/task")
		.then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(1))
			.body("title", is("Task #3"))
			.body("description", is("Task #3"))
			.body("initialHours", is(5))
			.body("remainingHours", is(5))
			.body("status", is("Not Started"));
	
		get("/task/1").then()
			.body("id", is(1))
			.body("title", is("Task #3"))
			.body("description", is("Task #3"))
			.body("initialHours", is(5))
			.body("remainingHours", is(5))
			.body("status", is("Not Started"));

	}

	@Ignore
	@Test
	@DatabaseSetup("ITStoryTest-data.xml")
	public void testAddSprintStory() {
		given()
			.contentType(ContentType.JSON)
			.body("{\"remainingHours\":5}")
		.when()
			.put("/task/1001/remainingHours")
		.then()
			.statusCode(HttpURLConnection.HTTP_OK)
			.body("id", is(1001))
			.body("title", is("Task #2"))
			.body("description", is("Task #2"))
			.body("initialHours", is(20))
			.body("remainingHours", is(5));
	
		get("/task/1001").then()
			.body("id", is(1001))
			.body("title", is("Task #2"))
			.body("description", is("Task #2"))
			.body("initialHours", is(20))
			.body("remainingHours", is(5))
			.body("status", is("Not Started"));
		
	}

}

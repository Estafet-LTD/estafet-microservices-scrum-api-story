package com.estafet.microservices.api.story.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class StoryTest {

	@Test
	public void testNewStory() {
		assertEquals("Not Started", new Story().getStatus());
	}

	@Test
	public void testAddTask() {
		assertEquals("In Progress", new Story().start(1).addTask(new Task()).getStatus());
	}

	@Test
	public void testStart() {
		assertEquals("In Progress", new Story().start(1).getStatus());
	}

	@Test(expected = RuntimeException.class)
	public void testStartFailed() {
		new Story().start(1).start(2).getStatus();
	}

	@Test(expected = RuntimeException.class)
	public void testReopenFailed() {
		new Story().reopen().getStatus();
	}

}

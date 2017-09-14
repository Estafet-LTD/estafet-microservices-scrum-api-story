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

	@Test(expected = RuntimeException.class)
	public void testAddTaskFailed() {
		new Story().start(1).complete().addTask(new Task()).getStatus();
	}

	@Test
	public void testStart() {
		assertEquals("In Progress", new Story().start(1).getStatus());
	}

	@Test(expected = RuntimeException.class)
	public void testStartFailed() {
		new Story().start(1).start(2).getStatus();
	}

	@Test
	public void testReopen() {
		assertEquals("Planning", new Story().start(1).complete().reopen().getStatus());
	}

	@Test(expected = RuntimeException.class)
	public void testReopenFailed() {
		new Story().reopen().getStatus();
	}

	@Test
	public void testComplete() {
		assertEquals("Completed", new Story().start(1).complete().getStatus());
	}

	@Test(expected = RuntimeException.class)
	public void testCompleteFailed() {
		new Story().complete().complete().getStatus();
	}

}

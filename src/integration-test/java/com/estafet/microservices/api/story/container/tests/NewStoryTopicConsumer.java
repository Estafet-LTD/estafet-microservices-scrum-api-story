package com.estafet.microservices.api.story.container.tests;

import com.estafet.microservices.scrum.lib.commons.jms.TopicConsumer;

public class NewStoryTopicConsumer extends TopicConsumer {

	public NewStoryTopicConsumer() {
		super("new.story.topic");
	}

}

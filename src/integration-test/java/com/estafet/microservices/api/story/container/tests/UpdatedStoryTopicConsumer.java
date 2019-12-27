package com.estafet.microservices.api.story.container.tests;

import com.estafet.microservices.scrum.lib.commons.jms.TopicConsumer;

public class UpdatedStoryTopicConsumer extends TopicConsumer {

	public UpdatedStoryTopicConsumer() {
		super("update.story.topic");
	}

}

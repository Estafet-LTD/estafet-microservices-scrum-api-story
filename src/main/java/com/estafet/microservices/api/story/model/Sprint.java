package com.estafet.microservices.api.story.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprint {

	private Integer id;


	public Integer getId() {
		return id;
	}

}

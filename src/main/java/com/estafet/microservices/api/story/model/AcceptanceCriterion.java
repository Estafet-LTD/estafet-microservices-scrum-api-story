package com.estafet.microservices.api.story.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACCEPTANCE_CRITERION")
public class AcceptanceCriterion {

	@Id
	@SequenceGenerator(name = "ACCEPTANCE_CRITERION_ID_SEQ", sequenceName = "ACCEPTANCE_CRITERION_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCEPTANCE_CRITERION_ID_SEQ")
	@Column(name = "ACCEPTANCE_CRITERION_ID")
	private int id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STORY_ID", nullable = false, referencedColumnName = "STORY_ID")
	private Story criterionStory;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	public int getId() {
		return id;
	}

	public Story getCriterionStory() {
		return criterionStory;
	}

	public String getDescription() {
		return description;
	}

	public void setId(int id) {
		this.id = id;
	}

	void setCriterionStory(Story criterionStory) {
		this.criterionStory = criterionStory;
	}

	public AcceptanceCriterion setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcceptanceCriterion other = (AcceptanceCriterion) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public static AcceptanceCriterion getAPI() {
		AcceptanceCriterion criterion = new AcceptanceCriterion();
		criterion.id = 1;
		criterion.description = "my criterion";
		return criterion;
	}

}

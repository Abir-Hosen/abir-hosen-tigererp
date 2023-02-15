package com.tigerslab.tigererp.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="gender")
public class Gender {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genderIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "genderIdGenerator", sequenceName = "genderSeq")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Gender [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	

}

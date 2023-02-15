package com.tigerslab.tigererp.model.org;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class PaperType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paperTypeIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "paperTypeIdGenerator", sequenceName = "paperTypeSeq")
	private Long id;

	private String name;
	private Float width;
	private Float height;
	private String description;
	
	public PaperType() {
		super();
	}

	public PaperType(Long id, String name, Float width, Float height, String description) {
		super();
		this.id = id;
		this.name = name;
		this.width = width;
		this.height = height;
		this.description = description;
	}

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

	public Float getWidth() {
		return width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "PaperType [id=" + id + ", name=" + name + ", width=" + width + ", height=" + height + ", description="
				+ description + "]";
	}
	
}

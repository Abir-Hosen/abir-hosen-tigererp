package com.tigerslab.tigererp.model.financial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="AccountEquation")
public class AccountEquation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "accountIdGenerator", sequenceName = "accountEquationSeq")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="deltaSign")
	private String deltaSign;
	
	@Column(name="alius")
	private String alius;
	
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

	public String getDeltaSign() {
		return deltaSign;
	}

	public void setDeltaSign(String deltaSign) {
		this.deltaSign = deltaSign;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlius() {
		return alius;
	}

	public void setAlius(String alius) {
		this.alius = alius;
	}

	@Override
	public String toString() {
		return "AccountEquation [id=" + id + ", name=" + name + ", deltaSign=" + deltaSign + ", description="
				+ description + "]";
	}
	

}

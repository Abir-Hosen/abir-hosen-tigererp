package com.tigerslab.tigererp.model.financial;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class VoucherType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VoucherTypeIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "VoucherTypeGenerator", sequenceName = "VoucherTypeSeq")
	private long id;
	private String name;
	private String description;
	
	public VoucherType() {
		super();
	}
	public VoucherType(long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
		return "VoucherType [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}

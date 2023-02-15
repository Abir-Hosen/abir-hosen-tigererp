package com.tigerslab.tigererp.model.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "StockCategory")
public class StockCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stockCategoryIdgenerator")
	@SequenceGenerator(initialValue = 1, name = "stockCategoryIdgenerator", sequenceName = "stockCategorySeq")
	private Long id;
	
	@Column(name="parentId",  nullable = false, columnDefinition="bigint(20) default 0")
	private Long parentId;
	
	@Column(name="name",  nullable = false)
	private String name;
	
	@Column(name="alius")
	private String alius;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlius() {
		return alius;
	}

	public void setAlius(String alius) {
		this.alius = alius;
	}

	@Override
	public String toString() {
		return "StockCategory [id=" + id + ", parentId=" + parentId + ", name=" + name + ", alius=" + alius + "]";
	}
	

}

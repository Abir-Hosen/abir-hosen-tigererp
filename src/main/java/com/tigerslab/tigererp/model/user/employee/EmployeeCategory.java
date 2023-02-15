package com.tigerslab.tigererp.model.user.employee;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tigerslab.tigererp.model.AbstractData;
import com.tigerslab.tigererp.model.User;

@Entity
@Table(name = "employeeCategory")
public class EmployeeCategory extends AbstractData {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empCategoryIdgenerator")
	@SequenceGenerator(initialValue = 1, name = "empCategoryIdgenerator", sequenceName = "employeesCategoryIdSeq")
	private long id;

	@NotBlank
	@Size(min = 1, max = 255)
	@Column(name = "categoryName")
	private String categoryName;
	
	@Column(name = "alius")
	private String alius;

	@Column(name = "categoryDescription")
	private String categoryDescription;

	public EmployeeCategory() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getAlius() {
		return alius;
	}

	public void setAlius(String alius) {
		this.alius = alius;
	}

	@Override
	public String toString() {
		return "EmployeeCategory [id=" + id + ", categoryName=" + categoryName + ", categoryDescription="
				+ categoryDescription + "]";
	}
	
}

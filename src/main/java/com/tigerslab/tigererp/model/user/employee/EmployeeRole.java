package com.tigerslab.tigererp.model.user.employee;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tigerslab.tigererp.model.AbstractData;
import com.tigerslab.tigererp.model.User;

@Entity
@Table(name="employeeRole")
public class EmployeeRole extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeRoleidgenerator")
	@SequenceGenerator(initialValue = 1, name = "employeeRoleidgenerator", sequenceName = "employeeRoleSeq")
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	@NotBlank
	@Size(min = 1, max = 255)
	@Column(name="roleName", unique = true)
	protected String roleName;
	
	@Column(name="alius")
	private String alius;
	
	@Column(name="roleDescription")
	protected String roleDescription;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "empRoles")
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
	@JsonIgnoreProperties({"password","empRoles", "employee", "roles"})
	private Set<User> user = new HashSet<>();

	public EmployeeRole() {
		super();
	}
	
	public EmployeeRole(String roleName, String roleDescription) {
		super();
		this.roleName = roleName;
		this.roleDescription = roleDescription;
	}


	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName.trim().toString();
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription.trim().toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	public String getAlius() {
		return alius;
	}

	public void setAlius(String alius) {
		this.alius = alius;
	}

	@Override
	public String toString() {
		return "EmployeeRole [id=" + id + ", roleName=" + roleName + ", roleDescription=" + roleDescription + "]";
	}

}

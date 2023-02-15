package com.tigerslab.tigererp.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tigerslab.tigererp.model.user.employee.Employee;
import com.tigerslab.tigererp.model.user.employee.EmployeeCategory;
import com.tigerslab.tigererp.model.user.employee.EmployeeRole;

@Entity
@Table(name="user")
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "useridgenerator")
	@SequenceGenerator(initialValue = 1, name = "useridgenerator", sequenceName = "usersSeq")
	private long id;
		
	@Column(name="company_id", unique = true)
	private String companyId;
		
	@Column(name = "password")
	private String password;
	
	@Transient
	private String newPassword;
	
	@Column(name= "firstname")
	private String firstName;
		
	@Column(name = "lastname")
	private String lastName;
	
	// This is username of this application
	@Column(name = "email", unique = true)
	private String email;
		
	@Column(name = "active")
	private int active;
		
	@ManyToMany(cascade = CascadeType.MERGE)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
	@JoinTable(name = "user_has_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "emp_role_id"))
	@JsonIgnoreProperties("user")
	private Set<EmployeeRole> empRoles;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name= "employee")
	private Employee employee;

	public User() {
		super();
		this.companyId = "COMP"+ UUID.randomUUID().toString().substring(26).toUpperCase();
	}

	public User(long id, String companyId, String password, String newPassword, String firstName, String lastName,
			String email, int active, Set<Role> roles, Set<EmployeeRole> empRoles, Employee employee) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.password = password;
		this.newPassword = newPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.active = active;
		this.roles = roles;
		this.empRoles = empRoles;
		this.employee = employee;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<EmployeeRole> getEmpRoles() {
		return empRoles;
	}

	public void setEmpRoles(Set<EmployeeRole> empRoles) {
		this.empRoles = empRoles;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", companyId=" + companyId + ", password=" + password + ", newPassword=" + newPassword
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", active=" + active
				+ ", roles=" + roles + ", empRoles=" + empRoles + ", employee=" + employee + "]";
	}
	
}
package com.tigerslab.tigererp.model.user.employee;

import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigerslab.tigererp.model.AbstractData;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.inventory.Order;
import com.tigerslab.tigererp.model.org.Branch;
import com.tigerslab.tigererp.model.user.AddressV2;
import com.tigerslab.tigererp.model.user.Gender;

@Entity
@Table(name = "employee")
public class Employee extends AbstractData {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeidgenerator")
	@SequenceGenerator(initialValue = 1, name = "employeeidgenerator", sequenceName = "employeesidSeq")
	private long id;
	
	@Size(min = 0, max = 255)
	@Column(name = "alius")
	private String alius;

	@Size(min = 2, max = 255)
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "joiningDate")
	private Date joiningDate;
	
	@Column(name = "description")
	private String description;

	// ----GENERAL INFORMATION----
	@Column(name = "dateOfBirth")
	private Date dateOfBirth;
	
	@Column(name = "fathersName")
	@Size(min = 0, max = 255)
	private String fatherName;
	
	@Size(min = 0, max = 255)
	@Column(name = "motherName")
	private String motherName;
	
	@Column(name = "spouseName")
	private String spouseName;
	
	@Size(min = 0, max = 100)
	@Column(name = "nid")
	private String nid;
	
	@OneToMany(mappedBy = "salesHandlyBy", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonIgnoreProperties("salesHandlyBy")
	private List<Order> order;

	// ----CONTACT INFORMATION----
	@OneToMany(cascade = CascadeType.PERSIST)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JoinTable(name = "employee_Addressv2", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
	private Set<AddressV2> employeeAddress;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
	@JoinTable(name = "emp_has_category", joinColumns = @JoinColumn(name = "emp_id"), inverseJoinColumns = @JoinColumn(name = "ctg_id"))
	private Set<EmployeeCategory> employeeCategory;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JoinTable(name = "employee_work_branch", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "branch_id"))
	private Set<Branch> branch;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JoinColumn(name = "employee_ledger_account_id")
	private LedgerAccounts ledgerAccounts;
	
	@OneToOne
	@JoinColumn(name = "gender")
	private Gender gender;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAlius() {
		return alius;
	}

	public void setAlius(String alius) {
		this.alius = alius;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public Set<AddressV2> getEmployeeAddress() {
		return employeeAddress;
	}

	public void setEmployeeAddress(Set<AddressV2> employeeAddress) {
		this.employeeAddress = employeeAddress;
	}

	public Set<EmployeeCategory> getEmployeeCategory() {
		return employeeCategory;
	}

	public void setEmployeeCategory(Set<EmployeeCategory> employeeCategory) {
		this.employeeCategory = employeeCategory;
	}

	public Set<Branch> getBranch() {
		return branch;
	}

	public void setBranch(Set<Branch> branch) {
		this.branch = branch;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public LedgerAccounts getLedgerAccounts() {
		return ledgerAccounts;
	}

	public void setLedgerAccounts(LedgerAccounts ledgerAccounts) {
		this.ledgerAccounts = ledgerAccounts;
	}

//	@Override
//	public String toString() {
//		return "Employee [id=" + id + ", alius=" + alius + ", userName=" + userName
//				+ ", joiningDate=" + joiningDate + ", description=" + description
//				+ ", dateOfBirth=" + dateOfBirth + ", fatherName=" + fatherName + ", motherName=" + motherName
//				+ ", spouseName=" + spouseName + ", nid=" + nid + ", employeeAddress=" + employeeAddress
//				+ ", employeeCategory=" + employeeCategory + ", branch=" + branch + ", gender=" + gender + "]";
//	}

}

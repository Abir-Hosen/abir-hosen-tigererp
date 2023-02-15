package com.tigerslab.tigererp.model.org;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tigerslab.tigererp.model.AbstractData;
import com.tigerslab.tigererp.model.user.Address;

@Entity
public class Branch  extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branchIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "branchIdGenerator", sequenceName = "branchSeq")
	private long id;
	private String branchName;
	private String alius;
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
	@JoinTable(name = "branchAddress", joinColumns = @JoinColumn(name = "branchId"), inverseJoinColumns = @JoinColumn(name = "addressId"))
	private Address branchAddress;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
	@JoinTable(name = "companyBranches", joinColumns = @JoinColumn(name = "companyId"), inverseJoinColumns = @JoinColumn(name = "branchId"))
	private Company company;
	
	@JsonIgnoreProperties(value = {"branch"}, allowSetters = true)
	@OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
	private Set<Room> rooms;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAlius() {
		return alius;
	}

	public void setAlius(String alius) {
		this.alius = alius;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(Address branchAddress) {
		this.branchAddress = branchAddress;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}
	
	

//	@Override
//	public String toString() {
//		return "Branch [id=" + id + ", branchName=" + branchName + ", alius=" + alius + ", description=" + description
//				+ ", branchAddress=" + branchAddress + ", company=" + company + ", rooms=" + rooms + "]";
//	}
}
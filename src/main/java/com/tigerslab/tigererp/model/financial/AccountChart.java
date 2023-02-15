package com.tigerslab.tigererp.model.financial;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tigerslab.tigererp.model.AbstractData;

@Entity
@Table(name="AccountChart")
public class AccountChart extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountChartIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "accountChartIdGenerator", sequenceName = "accountChartSeq")
	private Long id;
	
	private String name;
	
	private String description;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="parentAccountId")
	private AccountEquation parentAccount;

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

	public AccountEquation getParentAccount() {
		return parentAccount;
	}

	public void setParentAccount(AccountEquation parentAccount) {
		this.parentAccount = parentAccount;
	}

	@Override
	public String toString() {
		return "AccountChart [id=" + id + ", name=" + name + ", description=" + description + "Parent= "+parentAccount+"]";
	}

}

package com.tigerslab.tigererp.model.financial;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tigerslab.tigererp.model.AbstractData;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.inventory.Order;
import com.tigerslab.tigererp.model.user.AddressV2;
import com.tigerslab.tigererp.model.user.Gender;

@Entity
@Table(name = "LedgerAccounts")
@JsonIgnoreProperties({"orders"})
public class LedgerAccounts extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LedgerAccountIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "LedgerAccountIdGenerator", sequenceName = "ledgerAccountsSeq")
	private Long id;
	
	@Column(name="accountName")
	private String accountName;
	
	@Column(name="alius")
	private String alius;
	
	@Column(name="ledgerCode")
	private String ledgerCode;
	
	@Column(name="creationDate")
	private Date creationDate;
	
	@Column(name="description")
	private String description;
	
	@Column(name="dob")
	private Date dob;
	
	@Column(name="fathersName")
	private String fathersName;
	
	@Column(name="mothersName")
	private String mothersName;
	
	@Column(name="spouceName")
	private String spouceName;
	
	@Column(name="nid")
	private String nid;
	
	@Column(name="openingBalance")
	private BigDecimal openingBalance;
	
	@Column(name="accountLimit")
	private BigDecimal accountLimit;
	
	@Transient
	private Boolean sendSMS;
	
	@OneToMany(mappedBy = "party", cascade = CascadeType.PERSIST)//, orphanRemoval = true
	private List<Order> orders;
	
	@OneToOne
	@JoinColumn(name = "gender")
	private Gender gender;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="parent_account_id")
	private AccountChart parentAccount;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JoinTable(name = "account_group", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private List<LedgerGroup> accountGroup;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JoinTable(name = "account_address", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
	private Set<AddressV2> accountAddress;
	
	@OneToMany(mappedBy = "ledgerAccounts")
	@JsonIgnoreProperties("ledgerAccounts")
	private List<LedgerEntries> ledgerEntries;

	public LedgerAccounts() {
		super();
		this.ledgerCode = "LEDGER"+ UUID.randomUUID().toString().substring(26).toUpperCase();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAlius() {
		return alius;
	}

	public void setAlius(String alius) {
		this.alius = alius;
	}

	public String getLedgerCode() {
		return ledgerCode;
	}

	public void setLedgerCode(String ledgerCode) {
		this.ledgerCode = ledgerCode;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public String getMothersName() {
		return mothersName;
	}

	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}

	public String getSpouceName() {
		return spouceName;
	}

	public void setSpouceName(String spouceName) {
		this.spouceName = spouceName;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public BigDecimal getAccountLimit() {
		return accountLimit;
	}

	public void setAccountLimit(BigDecimal accountLimit) {
		this.accountLimit = accountLimit;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public AccountChart getParentAccount() {
		return parentAccount;
	}

	public void setParentAccount(AccountChart parentAccount) {
		this.parentAccount = parentAccount;
	}

	public List<LedgerGroup> getAccountGroup() {
		return accountGroup;
	}

	public void setAccountGroup(List<LedgerGroup> accountGroup) {
		this.accountGroup = accountGroup;
	}

	public Set<AddressV2> getAccountAddress() {
		return accountAddress;
	}

	public void setAccountAddress(Set<AddressV2> accountAddress) {
		this.accountAddress = accountAddress;
	}

	public List<LedgerEntries> getLedgerEntries() {
		return ledgerEntries;
	}

	public void setLedgerEntries(List<LedgerEntries> ledgerEntries) {
		this.ledgerEntries = ledgerEntries;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	

	public Boolean getSendSMS() {
		return sendSMS;
	}

	public void setSendSMS(Boolean sendSMS) {
		this.sendSMS = sendSMS;
	}


	@Override
	public String toString() {
		return "LedgerAccounts [id=" + id + ", accountName=" + accountName + ", alius=" + alius + ", ledgerCode="
				+ ledgerCode + ", creationDate=" + creationDate + ", description=" + description + ", dob=" + dob
				+ ", fathersName=" + fathersName + ", mothersName=" + mothersName + ", spouceName=" + spouceName
				+ ", nid=" + nid + ", openingBalance=" + openingBalance + ", accountLimit=" + accountLimit + ", orders="
				+ orders + ", gender=" + gender + ", parentAccount=" + parentAccount + ", accountGroup=" + accountGroup
				+ ", accountAddress=" + accountAddress+ "]";
	}
	
}

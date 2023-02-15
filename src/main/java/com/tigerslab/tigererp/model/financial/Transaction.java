package com.tigerslab.tigererp.model.financial;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Transaction")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TransactionIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "TransactionIdGenerator", sequenceName = "TransactionSeq")
	private Long id;
	
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new Date();

	@Column(name="originalDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date originalDate = new Date();
	
	@Column(name="amount")
	private BigDecimal amount;
	
	@Column(name="balance")
	private BigDecimal balance;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(mappedBy = "transactions")
	@JsonIgnoreProperties("transactions")
	private List<LedgerEntries> ledgerEntries;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<LedgerEntries> getLedgerEntries() {
		return ledgerEntries;
	}

	public void setLedgerEntries(List<LedgerEntries> ledgerEntries) {
		this.ledgerEntries = ledgerEntries;
	}

	public Date getOriginalDate() {
		return originalDate;
	}

	public void setOriginalDate(Date originalDate) {
		this.originalDate = originalDate;
	}

//	@Override
//	public String toString() {
//		return "Transaction [id=" + id + ", date=" + date + ", originalDate=" + originalDate + ", amount=" + amount
//				+ ", balance=" + balance + ", description=" + description + ", ledgerEntries=" + ledgerEntries + "]";
//	}

}

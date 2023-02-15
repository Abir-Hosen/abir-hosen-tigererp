package com.tigerslab.tigererp.model.financial;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigerslab.tigererp.model.AbstractData;
import com.tigerslab.tigererp.model.User;
import com.tigerslab.tigererp.model.inventory.Order;

@Entity
@Table(name="LedgerEntries")
public class LedgerEntries extends AbstractData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LedgerEntriesIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "LedgerEntriesIdGenerator", sequenceName = "LedgerEntriesSeq")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="ledgerAccountId")
	@JsonIgnoreProperties("ledgerEntries")
	private LedgerAccounts ledgerAccounts;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="secondLedgerAccountId")
	@JsonIgnoreProperties("ledgerEntries")
	private LedgerAccounts secondLedgerAccounts;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="transactionId")
	@JsonIgnoreProperties("ledgerEntries")
	private Transaction transactions;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="transactionEntryTypeId")
	private TransactionEntryType transactionEntryType;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="voucherTypeId")
	private VoucherType voucherType;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "createdBy")
	private User createdBy;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "approvedBy")
	private User approvedBy;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "lockedBy")
	private User lockedBy;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Order order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LedgerAccounts getLedgerAccounts() {
		return ledgerAccounts;
	}

	public void setLedgerAccounts(LedgerAccounts ledgerAccounts) {
		this.ledgerAccounts = ledgerAccounts;
	}

	public LedgerAccounts getSecondLedgerAccounts() {
		return secondLedgerAccounts;
	}

	public void setSecondLedgerAccounts(LedgerAccounts secondLedgerAccounts) {
		this.secondLedgerAccounts = secondLedgerAccounts;
	}

	public Transaction getTransactions() {
		return transactions;
	}

	public void setTransactions(Transaction transactions) {
		this.transactions = transactions;
	}

	public TransactionEntryType getTransactionEntryType() {
		return transactionEntryType;
	}

	public void setTransactionEntryType(TransactionEntryType transactionEntryType) {
		this.transactionEntryType = transactionEntryType;
	}


	public VoucherType getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}

	public User getLockedBy() {
		return lockedBy;
	}

	public void setLockedBy(User lockedBy) {
		this.lockedBy = lockedBy;
	}
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String toString() {
	    return "transaction: "+this.transactions;
	}
// deadlock issue
//	@Override
//	public String toString() {
//		return "LedgerEntries [id=" + id + ", ledgerAccounts=" + ledgerAccounts + ", transactions=" + transactions
//				+ ", transactionEntryType=" + transactionEntryType + ", voucherType=" + voucherType + ", createdBy="
//				+ createdBy + ", approvedBy=" + approvedBy + ", lockedBy=" + lockedBy + "]";
//	}

}

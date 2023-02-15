package com.tigerslab.tigererp.model.inventory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigerslab.tigererp.model.AbstractData;
import com.tigerslab.tigererp.model.financial.LedgerAccounts;
import com.tigerslab.tigererp.model.user.employee.Employee;

@Entity
@Table(name="Orders")
public class Order{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "OrderIdGenerator", sequenceName = "OrderSeq")
	private Long id;
	
	@Column(name="offlineinvoiceNumber")
	private String offlineInvoiceNumber;
	
	@Temporal(TemporalType.DATE)
	private Date deliveryDate;
	
	@Temporal(TemporalType.DATE)
	private Date orderDate;
	
	@Temporal(TemporalType.DATE)
	private Date nextPaymentDate;
	
	@Column(name="description")
	private String description;
	
	@Column(name="paidAmount")
	private BigDecimal paidAmount;
	
	@Column(name="totalAmount")
	private BigDecimal totalAmount;
	
	@Transient
	private Boolean sendSMS;
	
	// Check for SALE account or PURCHASE ACCOUNT
	@JsonIgnoreProperties({"ledgerEntries"})
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn
	private LedgerAccounts orderAccounts;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
	@JoinTable(name = "Order_has_Product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "stock_item_id"))
	private List<OrderStockItem> stockItems;

	@JsonIgnoreProperties({"ledgerEntries"})
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn
	private LedgerAccounts party;
	
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn
	private Employee salesHandlyBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOfflineInvoiceNumber() {
		return offlineInvoiceNumber;
	}

	public void setOfflineInvoiceNumber(String offlineInvoiceNumber) {
		this.offlineInvoiceNumber = offlineInvoiceNumber;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public List<OrderStockItem> getProduct() {
//		return stockItems;
//	}
//
//	public void setProduct(List<OrderStockItem> stockItems) {
//		this.stockItems = stockItems;
//	}

	public LedgerAccounts getParty() {
		return party;
	}

	public void setParty(LedgerAccounts party) {
		this.party = party;
	}

	public Employee getSalesHandlyBy() {
		return salesHandlyBy;
	}

	public void setSalesHandlyBy(Employee salesHandlyBy) {
		this.salesHandlyBy = salesHandlyBy;
	}

	public List<OrderStockItem> getStockItems() {
		return stockItems;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public void setStockItems(List<OrderStockItem> stockItems) {
		this.stockItems = stockItems;
	}

	public Date getNextPaymentDate() {
		return nextPaymentDate;
	}

	public void setNextPaymentDate(Date nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public LedgerAccounts getOrderAccounts() {
		return orderAccounts;
	}

	public void setOrderAccounts(LedgerAccounts orderAccounts) {
		this.orderAccounts = orderAccounts;
	}
	
	public Boolean getSendSMS() {
		return sendSMS;
	}

	public void setSendSMS(Boolean sendSMS) {
		this.sendSMS = sendSMS;
	}
	
	public String toString() {
		return "ORD Stk Item: "+getStockItems().iterator().next().getProductTransactionEntryType().getName();
	}
	
}

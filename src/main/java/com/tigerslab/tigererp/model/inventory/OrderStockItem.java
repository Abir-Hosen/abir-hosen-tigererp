package com.tigerslab.tigererp.model.inventory;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tigerslab.tigererp.model.AbstractData;
import com.tigerslab.tigererp.model.order.Batch;
import com.tigerslab.tigererp.model.org.Room;

@Entity
@Table(name="OrderStockItem")
public class OrderStockItem extends AbstractData{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderStockItemIdgenerator")
	@SequenceGenerator(initialValue = 1, name = "OrderStockItemIdgenerator", sequenceName = "OrderStockItemSeq")
	private Long id;
	
	@Column(name="quantity")
	private Double quantity;
	
	@Column(name="rate")
	private BigDecimal rate;
	
	@Column(name="discount")
	private BigDecimal discount;
	
	@Column(name="totalAmount")
	private BigDecimal totalAmount;
	
	@Column(name="totalQuantity")
	private int totalQuantity;
	
	@OneToOne
	@JoinColumn(name = "productTransactionEntryTypeId")
	private ProductTransactionEntryType productTransactionEntryType;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "batch")
	private Batch batch;
	
	@OneToOne
	@JoinColumn(name = "stockItemId")
	private StockItem stockItem;
	
	@OneToOne
	@JoinColumn(name = "room")
	private Room room;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StockItem getStockItem() {
		return stockItem;
	}

	public void setStockItem(StockItem stockItem) {
		this.stockItem = stockItem;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public ProductTransactionEntryType getProductTransactionEntryType() {
		return productTransactionEntryType;
	}

	public void setProductTransactionEntryType(ProductTransactionEntryType productTransactionEntryType) {
		this.productTransactionEntryType = productTransactionEntryType;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}

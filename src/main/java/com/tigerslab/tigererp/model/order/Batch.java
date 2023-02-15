package com.tigerslab.tigererp.model.order;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tigerslab.tigererp.model.inventory.StockItem;

@Entity
public class Batch {
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BatchIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "BatchIdGenerator", sequenceName = "BatchSeq")
	private Long id;
	private Long batchNo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date mfg;
	@Temporal(TemporalType.TIMESTAMP)
	private Date expDate;
	@OneToOne
	@JoinColumn(name="stock_item_id")
	private StockItem stockItem;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
	public Date getMfg() {
		return mfg;
	}
	public void setMfg(Date mfg) {
		this.mfg = mfg;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	public StockItem getStockItem() {
		return stockItem;
	}
	public void setStockItem(StockItem stockItem) {
		this.stockItem = stockItem;
	}

}

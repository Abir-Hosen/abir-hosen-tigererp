package com.tigerslab.tigererp.model.inventory;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tigerslab.tigererp.model.org.StockCategory;
import com.tigerslab.tigererp.model.org.inventory.Units;

@Entity
@Table(name="StockItem")
public class StockItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "StockItemIdGenerator")
	@SequenceGenerator(initialValue = 1, name = "StockItemIdGenerator", sequenceName = "StockItemSeq")
	private Long id;
	
	private String name;
	
	private String alius;
	
	private int reOrderQuantity;
	
	private Boolean saleOnline;
	
	private Boolean mainTainInBatch;
	
	private String description;
	
	private BigDecimal sellingPrice;
	
	private BigDecimal minimumSellingPrice;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JoinTable(name = "stockItemCategory", joinColumns = @JoinColumn(name = "stock_item_id"), inverseJoinColumns = @JoinColumn(name = "stock_category_id"))
	private List<StockCategory> stockCategory;
	
	@OneToOne
	@JoinColumn(name = "unitId")
	private Units unit;

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

	public int getReOrderQuantity() {
		return reOrderQuantity;
	}

	public void setReOrderQuantity(int reOrderQuantity) {
		this.reOrderQuantity = reOrderQuantity;
	}

	public Boolean getSaleOnline() {
		return saleOnline;
	}

	public void setSaleOnline(Boolean saleOnline) {
		this.saleOnline = saleOnline;
	}

	public Boolean getMainTainInBatch() {
		return mainTainInBatch;
	}

	public void setMainTainInBatch(Boolean mainTainInBatch) {
		this.mainTainInBatch = mainTainInBatch;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getAlius() {
		return alius;
	}

	public void setAlius(String alius) {
		this.alius = alius;
	}

	public BigDecimal getMinimumSellingPrice() {
		return minimumSellingPrice;
	}

	public void setMinimumSellingPrice(BigDecimal minimumSellingPrice) {
		this.minimumSellingPrice = minimumSellingPrice;
	}

	public List<StockCategory> getStockCategory() {
		return stockCategory;
	}

	public void setStockCategory(List<StockCategory> stockCategory) {
		this.stockCategory = stockCategory;
	}

	public Units getUnit() {
		return unit;
	}

	public void setUnit(Units unit) {
		this.unit = unit;
	}
	
}

package com.tigerslab.tigererp.helper;

import java.math.BigDecimal;
import java.sql.Date;

public class SalesOrderWrapper {
	
	private Long orderId;
	private Date orderDate;
	private BigDecimal totalAmount;
	private Long stockItemid;
	private String productName;
	private Long productId;
	private BigDecimal rate;
	private Double TotalSaleQuantity;
	private BigDecimal average;
	
	
	public Long getOrderId() {
		return orderId;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getStockItemid() {
		return stockItemid;
	}

	public void setStockItemid(Long stockItemid) {
		this.stockItemid = stockItemid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Double getTotalSaleQuantity() {
		return TotalSaleQuantity;
	}

	public void setTotalSaleQuantity(Double totalSaleQuantity) {
		TotalSaleQuantity = totalSaleQuantity;
	}

	public BigDecimal getAverage() {
		return average;
	}

	public void setAverage(BigDecimal average) {
		this.average = average;
	}

	@Override
	public String toString() {
		return "SalesOrderWrapper [orderId=" + orderId + ", orderDate=" + orderDate + ", totalAmount=" + totalAmount
				+ ", stockItemid=" + stockItemid + ", productName=" + productName + ", productId=" + productId
				+ ", rate=" + rate + ", TotalSaleQuantity=" + TotalSaleQuantity + ", average=" + average + "]";
	}
	

}

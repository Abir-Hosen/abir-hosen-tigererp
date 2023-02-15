package com.tigerslab.tigererp.model.custom;

import java.math.BigDecimal;

import com.tigerslab.tigererp.model.inventory.Order;

public class OrderTotalByDateRange {
	
	private Order u;
	private Double quantity;
	private Double rate;
	private Double totalAmount;
	
	public OrderTotalByDateRange(Order u, Double quantity, Double rate, Double totalAmount) {
		this.u = u;
		this.quantity = quantity;
		this.rate = rate;
		this.totalAmount = totalAmount;
	}

	public Order getOrder() {
		return u;
	}

	public void setOrder(Order u) {
		this.u = u;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "OrderTotalByDateRange [u=" + u + ", quantity=" + quantity + ", rate=" + rate + ", totalAmount="
				+ totalAmount + "]";
	}
}

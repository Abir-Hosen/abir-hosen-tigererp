package com.tigerslab.tigererp.model.httphelper;

public class RequestParameter {
	
	private int page;
	private int limit;
	private String filter;
	private String order;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	@Override
	public String toString() {
		return "RequestParameter [page=" + page + ", limit=" + limit + ", filter=" + filter + ", order=" + order + "]";
	}
	
}

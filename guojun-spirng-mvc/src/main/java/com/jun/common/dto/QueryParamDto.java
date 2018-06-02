package com.jun.common.dto;

public class QueryParamDto<T> {
	/**
	 * 每页大小
	 */
	private int pageSize = 10;
	
	/**
	 * 页码
	 */
	private int pageNo = 1;
	
	/**
	 * 排序字段
	 */
	private String sortField;
	
	/**
	 * 排序类型
	 */
	private String orderBy ;
	
	/**
	 * 数据
	 */
	private T queryParam;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public T getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(T queryParam) {
		this.queryParam = queryParam;
	}
}

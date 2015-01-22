package com.artigence.smarthome.service.core.dto;


public class SearchColumns {
	/*data source's name*/
	private String data;
	/* column's name*/
	private String name;
	/* flag to indicate if this column is searchable(true) or not (false)*/
	private boolean searchable;
	/* flag to indicate if this column is orderable(true) or not (false)*/
	private boolean orderable;
	/* search to apply to this column*/
	private Search search;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSearchable() {
		return searchable;
	}
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	public boolean isOrderable() {
		return orderable;
	}
	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	
	
}
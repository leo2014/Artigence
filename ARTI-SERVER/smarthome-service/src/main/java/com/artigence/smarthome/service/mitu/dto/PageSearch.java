package com.artigence.smarthome.service.mitu.dto;

public class PageSearch {
	
	private int currentPage;
	
	private int totalPage;
	
	private int pageSize;
	
	public PageSearch(){}
	
	public PageSearch(int current,int total,int size){
		this.currentPage = current;
		this.totalPage = total;
		this.pageSize = size;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}

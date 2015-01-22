package com.artigence.smarthome.service.core.dto;

import java.util.List;

public class SearchResult<E> {
	private Integer draw;
	private Integer recordsTotal;
	private Integer recordsFiltered;
	private List<E> data;
	
	public SearchResult(){}
	
	public SearchResult(int recordsTotal,int recordsFiltered, List<E> data){
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
	}
	
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List<E> getData() {
		return data;
	}
	public void setData(List<E> data) {
		this.data = data;
	}
	
	
}

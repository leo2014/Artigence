package com.artigence.smarthome.service.core.dto;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * datatable search param class
 * @author ripon
 *
 */
public class SearchParam {
	/* draw counter*/
	private Integer draw;
	/* page first record indicator*/
	private Integer start;
	/* number of records that tables can display in the current draw*/
	private Integer length;
	/* global search*/
	private Search search;
	/* datatable columns*/
	private List<SearchColumns> columns;
	/* order direction for columns*/
	private List<OrderColumns> order;
	

	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	public List<SearchColumns> getColumns() {
		return columns;
	}
	public void setColumns(List<SearchColumns> columns) {
		this.columns = columns;
	}
	public List<OrderColumns> getOrder() {
		return order;
	}
	public void setOrder(List<OrderColumns> order) {
		this.order = order;
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

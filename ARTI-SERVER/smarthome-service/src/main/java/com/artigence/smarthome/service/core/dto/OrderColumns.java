package com.artigence.smarthome.service.core.dto;

public class OrderColumns {
	/* cloumn to which ordering should be applied*/
	private Integer column;
	/* ordering direction for this cloumn*/
	private String dir;
	public Integer getColumn() {
		return column;
	}
	public void setColumn(Integer column) {
		this.column = column;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	
}

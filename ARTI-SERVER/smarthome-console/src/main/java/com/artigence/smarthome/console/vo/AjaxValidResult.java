package com.artigence.smarthome.console.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AjaxValidResult {
	private String value;
	private String feild;
	private int valid;
	private String message;
	
	public AjaxValidResult(){};
	
	public AjaxValidResult(String value, int valid){
		this.valid = valid;
		this.value = value;
	}
	
	public AjaxValidResult(String value, int valid, String message){
		this.valid = valid;
		this.value = value;
		this.message = message;
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFeild() {
		return feild;
	}

	public void setFeild(String feild) {
		this.feild = feild;
	}
	
	
}

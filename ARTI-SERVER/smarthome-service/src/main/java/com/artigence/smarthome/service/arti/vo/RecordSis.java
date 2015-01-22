package com.artigence.smarthome.service.arti.vo;

public class RecordSis {

	private String nodeid;
	
	private int errorCount;
	
	private int total;
	
	private int duibao;
	
	private float error;
	
	private float diubaob;

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}


	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public float getError() {
		return error;
	}

	public void setError(float error) {
		this.error = error;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getDuibao() {
		return duibao;
	}

	public void setDuibao(int duibao) {
		this.duibao = duibao;
	}

	public float getDiubaob() {
		return diubaob;
	}

	public void setDiubaob(float diubaob) {
		this.diubaob = diubaob;
	}
	
	
}

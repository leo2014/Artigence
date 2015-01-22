package com.artigence.smarthome.service.arti.dto;

import com.artigence.smarthome.persist.model.Node;

public class NodeVo extends Node {

	private static final long serialVersionUID = 8021590941204264416L;

	private String artiMac;

	private boolean data;
	public String getArtiMac() {
		if(this.getArti()!=null)
			artiMac = this.getArti().getMac();
		return artiMac;
	}

	public void setArtiMac(String artiMac) {
		this.artiMac = artiMac;
	}

	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}
	
	
}

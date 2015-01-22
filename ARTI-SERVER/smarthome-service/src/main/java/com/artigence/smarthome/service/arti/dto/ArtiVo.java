package com.artigence.smarthome.service.arti.dto;


import java.util.List;



import com.artigence.smarthome.persist.model.Arti;


public class ArtiVo extends Arti {
	private String username;
	private String password;
	private boolean onLine;
	private List<NodeVo> nodeVos;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isOnLine() {
		return onLine;
	}
	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}
	public List<NodeVo> getNodeVos() {
		return nodeVos;
	}
	public void setNodeVos(List<NodeVo> nodeVos) {
		this.nodeVos = nodeVos;
	}


	
}

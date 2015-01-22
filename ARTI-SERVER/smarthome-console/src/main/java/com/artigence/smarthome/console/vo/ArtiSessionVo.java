package com.artigence.smarthome.console.vo;

import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.persist.model.Arti;

public class ArtiSessionVo {
	private SessionClient ArtiSession;
	private Arti arti;
	public SessionClient getArtiSession() {
		return ArtiSession;
	}
	public void setArtiSession(SessionClient artiSession) {
		ArtiSession = artiSession;
	}
	public Arti getArti() {
		return arti;
	}
	public void setArti(Arti arti) {
		this.arti = arti;
	}
	
}

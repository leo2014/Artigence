package com.artigence.smarthome.communication.handler;

import org.springframework.context.ApplicationEvent;

import com.artigence.smarthome.communication.protocol.ArtiProtocol;

public class DataPersistEvent extends ApplicationEvent {


	private static final long serialVersionUID = 2873481962167752860L;

	public DataPersistEvent(ArtiProtocol artiProtocol) {
		super(artiProtocol);
	}
}

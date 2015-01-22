package com.artigence.smarthome.communication.core;

import org.apache.mina.core.session.IoSession;

public interface DataHandler {
	public void setIoSession(IoSession ioSession);
	public void process(Object message);
}

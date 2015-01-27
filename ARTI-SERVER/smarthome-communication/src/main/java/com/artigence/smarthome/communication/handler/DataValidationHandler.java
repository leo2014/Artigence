package com.artigence.smarthome.communication.handler;

import com.artigence.smarthome.communication.core.DataHandler;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.server.CommunicationServer;
import org.apache.mina.core.session.IoSession;


public abstract class DataValidationHandler implements DataHandler{
	protected IoSession ioSession;

	@Override
	public void setIoSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}

	@Override
	public void process(Object message) {
		boolean isAuth = (Boolean)ioSession.getAttribute("isAuth");
		boolean isSafeAuth = true;//(Boolean)ioSession.getAttribute("isSafeAuth");
		ArtiProtocol artiProtocol = (ArtiProtocol)message;
		if(isAuth && isSafeAuth){
			//SessionClient session = (SessionClient)ioSession.getAttribute("sessionClient");

			CommunicationServer.getAc().publishEvent(new DataPersistEvent(artiProtocol));
		}
		doProcess(artiProtocol);
	}
	
	
	public abstract void doProcess(ArtiProtocol artiProtocol);

	public IoSession getIoSession() {
		return ioSession;
	}

}

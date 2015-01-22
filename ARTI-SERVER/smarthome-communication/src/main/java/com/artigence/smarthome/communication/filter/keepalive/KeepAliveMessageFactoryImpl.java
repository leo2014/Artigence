package com.artigence.smarthome.communication.filter.keepalive;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.springframework.stereotype.Component;

import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.Destination;
import com.artigence.smarthome.communication.session.ClientType;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.persist.model.code.DataType;
@Component("keepAliveMessageFactory")
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {

	@Override
	public boolean isRequest(IoSession session, Object message) {
		ArtiProtocol artiProtocol = (ArtiProtocol)message;
		if(artiProtocol.getDataType() == DataType.HEARTBEAT){
			boolean isAuth = true;//(Boolean)session.getAttribute("isAuth");
			if(isAuth)
				return true;
			else
				return false;
		}
			
		return false;
	}

	@Override
	public boolean isResponse(IoSession session, Object message) {
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
		return null;
	}

	@Override
	public Object getResponse(IoSession session, Object request) {
		return getHeartbeat(session);
	}

	private ArtiProtocol getHeartbeat(IoSession ioSession){
		ArtiProtocol artiProtocol = null;
		byte[] data = null;
		SessionClient session = (SessionClient)ioSession.getAttribute("sessionClient");
		if(session != null){
			ClientType ct = session.getClient().getClientType();
			if(ct == ClientType.ARTI)
				artiProtocol = new ArtiProtocol(Destination.ARTI,DataType.HEARTBEAT,data,0);
			else if(ct == ClientType.USER)
				artiProtocol = new ArtiProtocol(Destination.USER,DataType.HEARTBEAT,data,0);
		}else{
			//測試時使用心跳
			artiProtocol = new ArtiProtocol(Destination.ARTI,DataType.HEARTBEAT,data,0);
		}
		return artiProtocol;
	}
}

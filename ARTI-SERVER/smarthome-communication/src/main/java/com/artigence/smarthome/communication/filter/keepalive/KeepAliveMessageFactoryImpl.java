package com.artigence.smarthome.communication.filter.keepalive;

import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.ArtiProtocolFactory;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.persist.model.code.DataType;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.springframework.stereotype.Component;
@Component("keepAliveMessageFactory")
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {

	@Override
	public boolean isRequest(IoSession session, Object message) {
		ArtiProtocol artiProtocol = (ArtiProtocol)message;
		if(artiProtocol.getDataType() == DataType.HEARTBEAT){
			boolean isAuth = (Boolean)session.getAttribute("isAuth");
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
		//获取客户端
		CID client = null;
		SessionClient sessionClient = (SessionClient)session.getAttribute("sessionClient");
		if(sessionClient!=null)
			client = sessionClient.getClient();
		return ArtiProtocolFactory.artiProtocolInstance(DataType.HEARTBEAT,null,client);
	}

}

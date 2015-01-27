package com.artigence.smarthome.communication.handler;


import com.artigence.smarthome.communication.core.DataHandler;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.ArtiProtocolFactory;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.persist.model.code.DataType;
import org.springframework.stereotype.Component;
@Component("createSafeAuthHandler")
public class CreateSafeAuthHandler extends DataValidationHandler implements
		DataHandler {
	
	
	@Override
	public void doProcess(ArtiProtocol artiProtocol) {
		//获取客户端
		CID client = null;
		SessionClient sessionClient = (SessionClient)ioSession.getAttribute("sessionClient");
		if(sessionClient!=null)
			client = sessionClient.getClient();
		if(artiProtocol.getDataType() == DataType.KEY){
			String validData = (String)ioSession.getAttribute("validData");
			String vd = new String(artiProtocol.getData());

			if(validData.equals(vd)){
				ioSession.write(ArtiProtocolFactory.artiProtocolInstance(DataType.AUTH_REPLY, new byte[]{0x00}, client));
			}else{				
				ioSession.write(ArtiProtocolFactory.artiProtocolInstance(DataType.AUTH_REPLY, new byte[]{0x01}, client));
				ioSession.close(true);
			}
		}else{
			ioSession.write(ArtiProtocolFactory.artiProtocolInstance(DataType.AUTH_REPLY, new byte[]{0x01}, client));
		}

	}

}

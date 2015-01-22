package com.artigence.smarthome.communication.handler;


import org.springframework.stereotype.Component;

import com.artigence.smarthome.communication.core.DataHandler;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;

import com.artigence.smarthome.communication.protocol.Destination;
import com.artigence.smarthome.communication.session.ClientType;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.persist.model.code.DataType;
@Component("createSafeAuthHandler")
public class CreateSafeAuthHandler extends DataValidationHandler implements
		DataHandler {
	
	
	@Override
	public void doProcess(ArtiProtocol artiProtocol) {
		if(artiProtocol.getDataType() == DataType.KEY){
			String validData = (String)ioSession.getAttribute("validData");
			String vd = new String(artiProtocol.getData());
			if(validData.equals(vd)){
				ioSession.write(getAuthResult(true,artiProtocol));
			}else{				
				ioSession.write(getAuthResult(false,artiProtocol));
				ioSession.close(true);
			}
		}else{
			ioSession.write(getAuthResult(false,artiProtocol));
		}

	}
	private ArtiProtocol getAuthResult(boolean auth,ArtiProtocol ap){
		ArtiProtocol artiProtocol = null;
		byte[] data = null;
		if(auth)
			data=new byte[]{0x00};
		else
			data=new byte[]{0x01};
		SessionClient session = (SessionClient)ioSession.getAttribute("sessionClient");
		ClientType ct = session.getClient().getClientType();
		if(ct == ClientType.ARTI)
			artiProtocol = new ArtiProtocol(Destination.ARTI,DataType.AUTH_REPLY,data,1);
		else if(ct == ClientType.USER)
			artiProtocol = new ArtiProtocol(Destination.USER,DataType.AUTH_REPLY,data,1);
		return artiProtocol;
	}
}

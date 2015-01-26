package com.artigence.smarthome.communication.handler;


import com.artigence.smarthome.communication.core.DataHandler;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.persist.model.code.DataType;
import org.springframework.stereotype.Component;
@Component("createSafeAuthHandler")
public class CreateSafeAuthHandler extends DataValidationHandler implements
		DataHandler {
	
	
	@Override
	public void doProcess(ArtiProtocol artiProtocol) {
		CID client = CID.getDefalutId();
		if(artiProtocol.getDataType() == DataType.KEY){
			String validData = (String)ioSession.getAttribute("validData");
			String vd = new String(artiProtocol.getData());

			if(validData.equals(vd)){
				ioSession.write(getAuthResult(true,client));
			}else{				
				ioSession.write(getAuthResult(false,client));
				ioSession.close(true);
			}
		}else{
			ioSession.write(getAuthResult(false,client));
		}

	}
	private ArtiProtocol getAuthResult(boolean auth,CID destination){
		ArtiProtocol artiProtocol = null;
		byte[] data = null;
		if(auth)
			data=new byte[]{0x00};
		else
			data=new byte[]{0x01};

		artiProtocol = new ArtiProtocol(CID.getServerId(),destination,DataType.AUTH_REPLY,data,3);

		return artiProtocol;
	}
}

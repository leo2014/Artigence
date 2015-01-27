package com.artigence.smarthome.communication.handler;


import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.DestinationType;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.ClientType;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.communication.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

@Component("plainDataHandler")
public class PlainDataHandler extends DataValidationHandler {
	private static final Log log = LogFactory.getLog(PlainDataHandler.class);


	@Override
	@Transactional
	public void doProcess(ArtiProtocol artiProtocol) {
		log.debug("CMD Record is processing");

		CID client = new CID();
		client.setClientId(artiProtocol.getDestination());
		if(artiProtocol.getDestinationType() == DestinationType.ARTI)
			client.setClientType(ClientType.ARTI);
		else if(artiProtocol.getDestinationType() == DestinationType.USER)
			client.setClientType(ClientType.USER);

		SessionManager sm = SessionManager.getInstance();
		SessionClient sc = null;
		if(client.getClientType()!=null) {
			sc = sm.getSessionClient(client);
			sc.write(artiProtocol);
		}else{
			Iterator<SessionClient> sessionsIter = sm.getSessionClients().iterator();
			while (sessionsIter.hasNext()){
				sc = sessionsIter.next();
				sc.write(artiProtocol);
			}
		}
		log.debug("CMD Record is processed");

	}


}

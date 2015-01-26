package com.artigence.smarthome.communication.handler;


import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.communication.session.SessionManager;
import com.artigence.smarthome.persist.model.code.DataTransferDirection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("plainDataHandler")
public class PlainDataHandler extends DataValidationHandler {
	private static final Log log = LogFactory.getLog(PlainDataHandler.class);


	@Override
	@Transactional
	public void doProcess(ArtiProtocol artiProtocol) {
		log.debug("CMD Record is processing");
		DataTransferDirection dtd = DataTransferDirection.ArtiToServer;
		//CID cid = artiProtocol.getSource();
		CID did = artiProtocol.getDestination();

		SessionManager sm = SessionManager.getInstance();
		SessionClient desSession = sm.getSessionClient(did);

		

		log.debug("CMD Record is processed");
		if(desSession!=null)desSession.write(artiProtocol);
	}


}

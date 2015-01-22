package com.artigence.smarthome.communication.handler;


import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.Destination;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.ClientType;
import com.artigence.smarthome.persist.model.code.DataTransferDirection;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.service.arti.DataTransferRecordService;

@Component("plainDataHandler")
public class PlainDataHandler extends DataValidationHandler {
	private static final Log log = LogFactory.getLog(PlainDataHandler.class);

	@Resource
	private DataTransferRecordService dataTransferRecordService;

	@Override
	@Transactional
	public void doProcess(ArtiProtocol artiProtocol) {
		log.debug("CMD Record is processing");
		DataTransferDirection dtd = DataTransferDirection.ArtiToServer;
		CID cid = artiProtocol.getFrom();
		String clientType = "";
		if (cid.getClientType() == ClientType.ARTI) {	
			clientType = "ARTI";
			if (artiProtocol.getDestination() == Destination.SERVER) {
				dtd = DataTransferDirection.ArtiToServer;
			} else if (artiProtocol.getDestination() == Destination.USER) {
				dtd = DataTransferDirection.artiToUser;
			}
		} else if (cid.getClientType() == ClientType.USER) {
			clientType = "USER";
			if (artiProtocol.getDestination() == Destination.SERVER) {
				dtd = DataTransferDirection.userToServer;
			} else if (artiProtocol.getDestination() == Destination.ARTI) {
				dtd = DataTransferDirection.userToArti;
			}
		}
		
		dataTransferRecordService.dataRecord(clientType, cid.getClientId(), dtd, artiProtocol.getDataType(), artiProtocol.getData());
		log.debug("CMD Record is processed");
		ioSession.write(getValidResult(true));
	}

	private ArtiProtocol getValidResult(boolean valid) {
		ArtiProtocol validResult = new ArtiProtocol();

		validResult.setDestination(Destination.ARTI);
		validResult.setLength(1);
		validResult.setDataType(DataType.AUTH_REPLY);
		if (valid)
			validResult.setData(new byte[] {0x00});
		else
			validResult.setData(new byte[] {0x01});

		return validResult;

	}

	public void setDataTransferRecordService(
			DataTransferRecordService dataTransferRecordService) {
		this.dataTransferRecordService = dataTransferRecordService;
	}



}

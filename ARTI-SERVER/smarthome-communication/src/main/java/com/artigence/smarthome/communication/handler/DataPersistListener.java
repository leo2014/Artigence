package com.artigence.smarthome.communication.handler;

import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.Destination;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.ClientType;
import com.artigence.smarthome.persist.dao.ArtiDao;
import com.artigence.smarthome.persist.dao.DataTransferRecordDao;
import com.artigence.smarthome.persist.dao.UserDao;
import com.artigence.smarthome.persist.model.DataTransferRecord;
import com.artigence.smarthome.persist.model.code.DataTransferDirection;


@Component
public class DataPersistListener implements
		ApplicationListener<DataPersistEvent> {

	@Resource
	private UserDao userDao;
	@Resource
	private ArtiDao artiDao;
	@Resource
	private DataTransferRecordDao dataTransferRecordDao;
	
	@Async
	@Override
	@Transactional
	public void onApplicationEvent(final DataPersistEvent event) {
		ArtiProtocol artiProtocol= (ArtiProtocol)event.getSource();
		System.out.println("收到数据传输记录DataTransferRecord:"+protocolToRecord(artiProtocol));
	}


	private DataTransferRecord protocolToRecord(ArtiProtocol artiProtocol){
		DataTransferRecord dtr = new DataTransferRecord();
		dtr.setTransferDate(new Date());
		dtr.setCmd(artiProtocol.getData());
		DataTransferDirection dtd = DataTransferDirection.ArtiToServer;
		CID cid = artiProtocol.getFrom();
		if(cid.getClientType() == ClientType.ARTI){
			dtr.setGateway(artiDao.getById(cid.getClientId()));
			if(artiProtocol.getDestination() == Destination.SERVER){
				dtd = DataTransferDirection.ArtiToServer;
			}else if(artiProtocol.getDestination() == Destination.USER){
				dtd = DataTransferDirection.artiToUser;
			}
		}else if(cid.getClientType() == ClientType.USER){
			dtr.setUser(userDao.getById(cid.getClientId()));
			if(artiProtocol.getDestination() == Destination.SERVER){
				dtd = DataTransferDirection.userToServer;
			}else if(artiProtocol.getDestination() == Destination.ARTI){
				dtd = DataTransferDirection.userToArti;
			}
		}
		dtr.setDataTransferDirection(dtd);
		dtr.setDataType(artiProtocol.getDataType());
		dataTransferRecordDao.save(dtr);
		return dtr;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public void setArtiDao(ArtiDao artiDao) {
		this.artiDao = artiDao;
	}


	public void setDataTransferRecordDao(DataTransferRecordDao dataTransferRecordDao) {
		this.dataTransferRecordDao = dataTransferRecordDao;
	}

}

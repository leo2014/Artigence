package com.artigence.smarthome.service.imp.arti;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.artigence.smarthome.persist.model.code.DataTransferDirection;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.persist.model.communication.code.ClientType;
import com.artigence.smarthome.service.arti.DataTransferRecordService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-service.xml")
public class DataTransferRecordServiceImpTest {

	@Resource
	private DataTransferRecordService dataTransferRecordService;
	
	@Test
	public void testDataRecord() {
		this.dataTransferRecordService.dataRecord("ARTI",
				1l, DataTransferDirection.ArtiToServer , DataType.CMD, 
				new byte[]{0x00,0x00,0x00,0x01,0x00,0x00});
		this.dataTransferRecordService.dataRecord("ARTI",
				1l, DataTransferDirection.ArtiToServer , DataType.CMD, 
				new byte[]{0x00,0x00,0x00,0x01,0x00,0x01});
		this.dataTransferRecordService.dataRecord("ARTI",
				1l, DataTransferDirection.ArtiToServer , DataType.CMD, 
				new byte[]{0x00,0x00,0x00,0x02,0x00,0x00});
		this.dataTransferRecordService.dataRecord("ARTI",
				1l, DataTransferDirection.ArtiToServer , DataType.CMD, 
				new byte[]{0x00,0x00,0x00,0x02,0x00,0x01});
		this.dataTransferRecordService.dataRecord("ARTI",
				1l, DataTransferDirection.ArtiToServer , DataType.CMD, 
				new byte[]{0x00,0x00,0x00,0x03,0x00,0x00});
		this.dataTransferRecordService.dataRecord("ARTI",
				1l, DataTransferDirection.ArtiToServer , DataType.CMD, 
				new byte[]{0x00,0x00,0x00,0x03,0x00,0x01});
	}

}

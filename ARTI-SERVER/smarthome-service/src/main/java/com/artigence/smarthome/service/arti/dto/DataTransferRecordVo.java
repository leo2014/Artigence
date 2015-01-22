package com.artigence.smarthome.service.arti.dto;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.artigence.smarthome.persist.model.DataTransferRecord;

public class DataTransferRecordVo extends DataTransferRecord {

	private String artiMac;
	
	private String nodeSerialNum;
	
	private boolean status;
	
	private String transferDateString;

	public String getArtiMac() {
		if(this.getGateway()!=null)
			this.artiMac = this.getGateway().getMac();
		return artiMac;
	}

	public void setArtiMac(String artiMac) {
		this.artiMac = artiMac;
	}

	public String getNodeSerialNum() {
		if(this.getCmd()!=null&&this.getCmd().length>=6){
			this.nodeSerialNum = Hex.encodeHexString(ArrayUtils.subarray(getCmd(), 2, 4));
		}
		return nodeSerialNum;
	}

	public void setNodeSerialNum(String nodeSerialNum) {
		this.nodeSerialNum = nodeSerialNum;
	}

	public boolean getStatus() {
		if(this.getCmd()!=null&&this.getCmd().length>=6){
			byte[] st = ArrayUtils.subarray(getCmd(), 4, 6);
			if((st[0]<<8|st[1]) == 0x0000)
				this.status = true;
			else
				this.status = false;
		}
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTransferDateString() {
		if(this.getTransferDate()!=null)
			this.transferDateString = DateFormatUtils.ISO_DATETIME_FORMAT
		.format(this.getTransferDate());
		return transferDateString;
	}

	public void setTransferDateString(String transferDateString) {
		this.transferDateString = transferDateString;
	}
	
}

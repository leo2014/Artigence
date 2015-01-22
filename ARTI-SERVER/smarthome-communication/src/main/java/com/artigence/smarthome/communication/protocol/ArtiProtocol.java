package com.artigence.smarthome.communication.protocol;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.mina.core.buffer.IoBuffer;

import com.artigence.smarthome.communication.codec.arithmetic.CRC16;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.persist.model.code.DataType;

public class ArtiProtocol {

	private CID from;
	private Destination destination;
	private DataType dataType;
	private byte[] data;
	private int length;
	private long headCrc;
	private long bodyCrc;
	private int retryCount;
	

	public ArtiProtocol(){retryCount = 0;}

	public ArtiProtocol(Destination des,DataType dataType,byte[] data,int length){
	
		this.destination = des;
		this.dataType = dataType;
		this.data = data;
		this.length = length;
		retryCount = 0;
	}
	
	public ArtiProtocol(Destination des,DataType dataType,byte[] data,int length,long headCrc,long bodyCrc){
		this.destination = des;
		this.dataType = dataType;
		this.data = data;
		this.length = length;
		this.headCrc = headCrc;
		this.bodyCrc = bodyCrc;
		retryCount = 0;
	}
	//public ArtiProtocol()

	public IoBuffer toIoBuffer(){
		IoBuffer buf = IoBuffer.allocate(8).setAutoExpand(true);
		buf.putEnumShort(destination);
		buf.putShort((short)(8+length));
		CRC16 crc16 = new CRC16();
		crc16.update(buf.array());
		buf.putShort((short)crc16.getValue());
		crc16.reset();
		if(data!=null){
			crc16.update(data);
			buf.put(data);
			buf.putShort((short)crc16.getValue());
		}else{
			buf.putShort((short)0);
		}
		buf.flip();
		return buf;
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Destination getDestination() {
		return destination;
	}

	public byte[] getData() {
		return data;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public long getHeadCrc() {
		return headCrc;
	}

	public void setHeadCrc(long headCrc) {
		this.headCrc = headCrc;
	}

	public long getBodyCrc() {
		return bodyCrc;
	}

	public void setBodyCrc(long bodyCrc) {
		this.bodyCrc = bodyCrc;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public CID getFrom() {
		return from;
	}

	public void setFrom(CID from) {
		this.from = from;
	}

	
}

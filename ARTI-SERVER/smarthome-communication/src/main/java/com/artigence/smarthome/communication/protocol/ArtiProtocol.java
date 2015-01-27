package com.artigence.smarthome.communication.protocol;

import com.artigence.smarthome.communication.codec.arithmetic.CheckSum;
import com.artigence.smarthome.persist.model.code.DataType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.mina.core.buffer.IoBuffer;


public class ArtiProtocol {

	public final static int PROTOCOL_HEAD_LENGTH = 8;
	public final static int PROTOCOL_MIN_LENGTH = 12;


	private DestinationType destinationType;
	private Long destination;
	private DataType dataType;
	private byte[] data;
	private int length;
	private long headCrc;
	private long bodyCrc;
	private int retryCount;
	

	public ArtiProtocol(){retryCount = 0;}

	public ArtiProtocol(DestinationType destinationType,Long destination,DataType dataType,byte[] data,int length){
		this.destinationType = destinationType;
		this.destination = destination;
		this.dataType = dataType;
		this.data = data;
		this.length = length;
		retryCount = 0;
	}
	
	public ArtiProtocol(DestinationType destinationType,Long destination,DataType dataType,byte[] data,int length,long headCrc,long bodyCrc){
		this.destinationType = destinationType;
		this.destination = destination;
		this.dataType = dataType;
		this.data = data;
		this.length = length;
		this.headCrc = headCrc;
		this.bodyCrc = bodyCrc;
		retryCount = 0;
	}
	//public ArtiProtocol()

	public IoBuffer toIoBuffer(){
		IoBuffer buf = IoBuffer.allocate(PROTOCOL_MIN_LENGTH).setAutoExpand(true);

		//DestinationType | destination
		short desType = (short)destinationType.ordinal();
		short des = destination.shortValue();
		buf.putShort(desType);
		buf.putShort(des);

		//length
		buf.putShort((short)(length));

		//header crc
		CheckSum checkSum = new CheckSum();
		buf.putShort((short)checkSum.check(buf.array()));


		//dataType
		short dataT = (short)dataType.nCode();
		buf.putShort(dataT);
		byte[] dt = new byte[]{(byte)(dataT >>> 8),(byte)dataT};


		//data | body crc
		if(data!=null){

			buf.put(data);
			byte[] bodybytes = ArrayUtils.addAll(dt,data);
			buf.putShort((short) checkSum.check(bodybytes));
		}else{
			buf.putShort((short) checkSum.check(dt));
		}
		buf.flip();
		return buf;
	}

	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}


	public DestinationType getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(DestinationType destinationType) {
		this.destinationType = destinationType;
	}

	public Long getDestination() {
		return destination;
	}

	public void setDestination(Long destination) {
		this.destination = destination;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
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

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
}

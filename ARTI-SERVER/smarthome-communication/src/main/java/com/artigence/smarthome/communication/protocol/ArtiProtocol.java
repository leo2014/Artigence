package com.artigence.smarthome.communication.protocol;

import com.artigence.smarthome.communication.codec.arithmetic.CheckSum;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.mina.core.buffer.IoBuffer;

import com.artigence.smarthome.communication.codec.arithmetic.CRC16;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.persist.model.code.DataType;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;


public class ArtiProtocol {

	public final static int PROTOCOL_HEAD_LENGTH = 12;
	public final static int PROTOCOl_CID_LENGTH = 4;
	public final static int CRC_LENGTH = 2;


	private CID source;
	private CID destination;
	private DataType dataType;
	private byte[] data;
	private int length;
	private long headCrc;
	private long bodyCrc;
	private int retryCount;
	

	public ArtiProtocol(){retryCount = 0;}

	public ArtiProtocol(CID source,CID des,DataType dataType,byte[] data,int length){
		this.source = source;
		this.destination = des;
		this.dataType = dataType;
		this.data = data;
		this.length = length;
		retryCount = 0;
	}
	
	public ArtiProtocol(CID source,CID des,DataType dataType,byte[] data,int length,long headCrc,long bodyCrc){
		this.source = source;
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
		IoBuffer buf = IoBuffer.allocate(PROTOCOL_HEAD_LENGTH+4).setAutoExpand(true);

		//source | destination
		try {
			int dlen = destination.getClientId().length();
			int slen = source.getClientId().length();
			buf.putString(source.getClientId().subSequence(0, slen), 32, Charset.forName("UTF-8").newEncoder());
			buf.putString(destination.getClientId().subSequence(0, dlen), 32 , Charset.forName("UTF-8").newEncoder());
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}

		//length
		buf.putShort((short)(length));

		//header crc
		CRC16 crc16 = new CRC16();
		crc16.update(buf.array());
		buf.putShort((short)crc16.getValue());
		crc16.reset();

		//data | body crc
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

	/**
	 * 获取校验结果
	 * @param valid 校验成功true或失败false
	 * @return Artiprotol
	 */
	public static ArtiProtocol getValidResult(boolean valid,CID source,CID destination){
		ArtiProtocol validResult = new ArtiProtocol();
		CheckSum check = new CheckSum();
		validResult.setSource(source);
		validResult.setDestination(destination);
		validResult.setLength(1);
		validResult.setDataType(DataType.PLAIN_REPLY);
		if(valid)
			validResult.setData(new byte[]{0x00});
		else
			validResult.setData(new byte[]{0x01});

		//header crc
		ByteBuffer headerBuffer = ByteBuffer.allocate(ArtiProtocol.PROTOCOL_HEAD_LENGTH-2);
		try {
			headerBuffer.put(source.getClientId().getBytes("UTF-8")).put(destination.getClientId().getBytes("UTF-8"));
			headerBuffer.putShort((short)validResult.getLength());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		validResult.setHeadCrc(check.check(headerBuffer.array()));
		int ncode = validResult.getDataType().nCode();
		byte[] bodycrc = new byte[]{(byte)ncode,(byte)(ncode >>> 8)};

		if(validResult.getData()!=null)
			bodycrc = ArrayUtils.addAll(bodycrc, validResult.getData());
		validResult.setBodyCrc(check.check(bodycrc));
		return validResult;
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}


	public CID getSource() {
		return source;
	}

	public void setSource(CID source) {
		this.source = source;
	}

	public CID getDestination() {
		return destination;
	}

	public void setDestination(CID destination) {
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

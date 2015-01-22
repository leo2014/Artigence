package com.artigence.smarthome.communication.protocol;


import org.apache.commons.codec.binary.Hex;
import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Test;

import com.artigence.smarthome.communication.codec.arithmetic.CRC16;
import com.artigence.smarthome.communication.codec.arithmetic.CheckSum;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.ClientType;
import com.artigence.smarthome.persist.model.code.DataType;

public class ArtiProtocolTest {
	
	private ArtiProtocol artiProtocol;
	
	@Test
	public void testToIoBuffer() {
		byte[] data = new byte[]{0x00};
		artiProtocol = new ArtiProtocol(Destination.ARTI,DataType.AUTH_REPLY,data,3,0,0);
		
		System.out.println(Hex.encodeHexString(data));
		System.out.println(Hex.encodeHexString(artiProtocol.toIoBuffer().array()));
		System.out.println(artiProtocol.toIoBuffer().getHexDump());
		IoBuffer buff = artiProtocol.toIoBuffer();
		System.out.println(buff.getShort());
		System.out.println(buff.getShort());
		System.out.println(buff.getShort());
		System.out.println(buff.getShort());
	}
	
	@Test
	public void testArray(){
		byte[] data = {0x01,0x02,0x03,0x04};
		switch(data[0]<<8 | data[1]){
		case 0x0102:
			System.out.println("0x0102");
			break;
		case 0x0203:
			System.out.println("0x0203");
			break;
		default:
			System.out.println("0x0304");
			break;
		}		
	}
	@Test
	public void testLong(){
		Long l1 = 100L;
		ClientType c1 = ClientType.ARTI;
		CID cid = new CID(c1,l1);
		System.out.println(cid);
	}
	
	@Test
	public void testCrc16(){
		CRC16 crc16 = new CRC16();
		crc16.update(new byte[]{0x00,0x00,0x00,0x10});
		System.out.println(crc16.getValue() +" : "+ Long.toHexString(crc16.getValue()));
		crc16.reset();crc16.update(new byte[]{0x00,0x00,0x01,0x02,0x03,0x04,0x05,0x06});
		System.out.println(crc16.getValue() +" : "+ Long.toHexString(crc16.getValue()));
		
		ArtiProtocol ap = new ArtiProtocol();
		ap.setData(new byte[]{0x01,0x02,0x03,0x04,0x05,0x06});
		byte[] data = new byte[]{0x00,0x00};
		int nCode = (int)(data[0]<<8 | data[1]);
		DataType dataType = DataType.getDataType(nCode);ap.setDataType(dataType);
		crc16.reset();
		crc16.update(ap.getDataType().nCode());crc16.update(ap.getData());
		System.out.println(nCode+" "+crc16.getValue() +" : "+ Long.toHexString(crc16.getValue()));
		
	}
	
	@Test
	public void testCrc(){
		CRC16 crc16 = new CRC16();
		crc16.update(new byte[]{0x00,0x00,0x00,0x10});
		System.out.println(crc16.getValue() +" : "+ Long.toHexString(crc16.getValue()));
	}
	
	@Test
	public void testCheck(){
		CheckSum check = new CheckSum();

		//byte[] ss = new byte[]{(byte)0xff,(byte)0xff};
		long sum1 = check.check(new byte[]{0x00,0x00,0x00,0x0e});
		long sum = check.check(new byte[]{0x10,0x03,0x00,0x01});//check.check(new byte[]{0x00,0x11,0x00});

		System.out.println("sum1 : "+sum1+"  "+ Long.toHexString(sum1));
		System.out.println("sum : "+ sum+" "+Long.toHexString(sum));

		
	}
}

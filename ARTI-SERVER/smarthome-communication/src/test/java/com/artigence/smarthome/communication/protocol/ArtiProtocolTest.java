package com.artigence.smarthome.communication.protocol;


import com.artigence.smarthome.communication.codec.arithmetic.CRC16;
import com.artigence.smarthome.communication.codec.arithmetic.CheckSum;
import com.artigence.smarthome.persist.model.code.DataType;
import org.apache.commons.codec.binary.Hex;
import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Test;

public class ArtiProtocolTest {
	
	private ArtiProtocol artiProtocol;
	
	@Test
	public void testToIoBuffer() {
		byte[] data = new byte[]{0x00};
		artiProtocol = new ArtiProtocol(DestinationType.USER,1l,DataType.AUTH_REPLY,data,3,0,0);
		
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

		ArtiProtocol ap = new ArtiProtocol();
		ap.setDestination(1l);
		System.out.println(ap.getLength());
		getArti(ap);
		System.out.println(ap.getLength());
	}
	private void getArti(ArtiProtocol ap){
		ap.setLength(1);
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
	public void testCrc() throws Exception{
		CRC16 crc16 = new CRC16();
		crc16.update(new byte[]{0x00,0x00,0x00,0x00,0x00,0x13});
		System.out.println(crc16.getValue() +" : "+ Long.toHexString(crc16.getValue()));

		String mac = "010203040506&123456";
		System.out.println(Hex.encodeHexString(mac.getBytes("UTF-8")));
	}
	
	@Test
	public void testCheck(){
		CheckSum check = new CheckSum();

		//byte[] ss = new byte[]{(byte)0xff,(byte)0xff};
		long sum1 = check.check(new byte[]{0x00,0x00,0x00,0x00,0x00,0x13});
		long sum = check.check(new byte[]{0x00, 0x00, 0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x26, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36});//check.check(new byte[]{0x00,0x11,0x00});

		System.out.println("sum1 : "+sum1+"  "+ Long.toHexString(sum1));
		System.out.println("sum : "+ sum+" "+Long.toHexString(sum));

		
	}
}

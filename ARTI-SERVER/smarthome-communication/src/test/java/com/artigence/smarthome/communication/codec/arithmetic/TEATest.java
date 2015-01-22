package com.artigence.smarthome.communication.codec.arithmetic;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

public class TEATest {

	@Test
	public void test() {
	        byte[] pnt = new byte[] {
	        		(byte)0x81, 0x72, 0x63, 0x54, 
	                0x45, 0x36, 0x27, 0x18
	                };
	        int[] k = new int[] {
	                0x759f5845, 
	                0xf48ba5a3, 
	                0x81243faa, 
	                0x458facd5
	                };
	   
	        byte[] miwen=TEA.encrypt(pnt, 0, k, 32);
	        System.out.println("miwen:"+Hex.encodeHexString(miwen));
	        byte[] mingwen = TEA.decrypt(miwen, 0, k, 32);
	        System.out.println("yuan mingwen:"+Hex.encodeHexString(pnt));
	        System.out.println("mingwen:"+Hex.encodeHexString(mingwen));
	}

 
	@Test
	public void testByte(){
		System.out.println("byte max:"+Byte.MAX_VALUE);
		System.out.println("(int)0xff:"+(int)0xff);
		System.out.println("(byte)0xff:"+(byte)0xff);
		int i = (int)0xff;
		byte b = (byte)0xff;
		System.out.println("(int)0xff:0x"+Hex.encodeHexString(new byte[]{(byte)i}));
		System.out.println("(byte)0xff:0x"+Hex.encodeHexString(new byte[]{b}));
	}

	public void testKeyGenerator() throws NoSuchAlgorithmException{
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		keygen.init(128);
		SecretKey key = keygen.generateKey();
		byte[] k = key.getEncoded();
		System.out.println("(byte)key:"+Hex.encodeHexString(k));
		
		int[] key16 = TEA.byteToInt(k, 0);
		System.out.println("(int->byte)key:"+Integer.toHexString(key16[2]));
		System.out.println("(int)key:"+Arrays.toString(key16));
	}
	
	@Test
	public void testUUID(){
		long ss=new Random().nextLong();
		System.out.println(ss);
		byte[] s = ByteBuffer.allocate(8).putLong(ss).array();
		System.out.println(Hex.encodeHexString(s));
	
		
	}
	
	@Test
	public void testData() throws NoSuchAlgorithmException{
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		keygen.init(128);
		SecretKey key = keygen.generateKey();
		byte[] k = key.getEncoded();
		int[] key16 = TEA.byteToInt(k, 0);
		System.out.println(Hex.encodeHexString(k));
		//发送动态密钥和校验数据
		String validData=Long.toHexString(new Random().nextLong());			
		byte[] data=ArrayUtils.addAll(k,validData.getBytes());
		System.out.println(Hex.encodeHexString(validData.getBytes()));
		System.out.println(Hex.encodeHexString("&".getBytes()));
		System.out.println(Hex.encodeHexString(data));
	}
	
	@Test
	public void testTea32() throws UnsupportedEncodingException{
		 byte[] pnt = //new String("dhjikjhglp").getBytes();
				 new byte[]{(byte)0xff,(byte)0xff};
	        int[] k = new int[] {
	        		0x759f5845, 
	                0xf48ba5a3, 
	                0x81243faa, 
	                0x458facd5
	                };
	   org.bouncycastle.jce.provider.JCEBlockCipher.TEA d ;
	        byte[] miwen=TEA.processEncrypt(pnt, 0, k, 32);
	        System.out.println("miwen:"+Hex.encodeHexString(miwen));
	        byte[] mingwen = TEA.processDecrypt(miwen, 0, k, 32);
	        System.out.println("yuan mingwen:"+Hex.encodeHexString(pnt));
	        System.out.println("mingwen:"+Hex.encodeHexString(mingwen));
	}
	
	@Test
	public void testEnData(){
		byte[] head = new byte[]{0x00,0x01,0x00,0x10};
				//new byte[]{0x00,0x00,0x00,0x10};
		CRC16 crc16 = new CRC16();
		crc16.update(head);
		byte[] headcrc = new byte[]{(byte)(crc16.getValue() >>> 8),(byte)crc16.getValue()};
		System.out.println("头校验:"+Hex.encodeHexString(headcrc));
		
		crc16.reset();
		byte[] data = new byte[]{0x00,0x11,0x00};
				//new byte[]{0x00,0x00,0x01,0x02};
		
        int[] k = new int[] {
                0x759f5845, 
                0xf48ba5a3, 
                0x81243faa, 
                0x458facd5
                };
		byte[] miwen=TEA.processEncrypt(data, 0, k, 32);
		System.out.println("密文:"+Hex.encodeHexString(miwen));
        byte[] mingwen = TEA.processDecrypt(miwen, 0, k, 32);
        System.out.println("明文:"+Hex.encodeHexString(mingwen));
		crc16.update(miwen);
		byte[] bodycrc = new byte[]{(byte)(crc16.getValue() >>> 8),(byte)crc16.getValue()};
		System.out.println("体校验:"+Hex.encodeHexString(bodycrc));
		
	}
}

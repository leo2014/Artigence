package com.artigence.smarthome.communication.codec.arithmetic;

import org.apache.commons.lang.ArrayUtils;

public class TEA {
	
	public static byte[] processEncrypt(byte[] content, int offset, int[] key, int times){
		int len = content.length%8==0?0:8-(content.length%8);
		byte[] data = null;
		if(len>0){
			byte b[] = new byte[len];
			data = ArrayUtils.addAll(content, b);
		}else
			data = content;
		
		int length = content.length+len;
		byte result[] = null;
		for(int i=0;i<length;i+=8){
			if(i==0)
				result = encrypt(data,i,key,times);
			else
				result=ArrayUtils.addAll(result, encrypt(data,i,key,times));
			
		}
		return result;
	}
	
	
	public static byte[] processDecrypt(byte[] content, int offset, int[] key, int times){
		int len = content.length%8==0?0:8-(content.length%8);
		byte[] data = null;
		if(len>0){
			byte b[] = new byte[len];
			data = ArrayUtils.addAll(content, b);
		}else
			data = content;
		int length = content.length+len;
		byte result[] = null;
		for(int i=0;i<length;i+=8){
			if(i==0)
				result = decrypt(data,i,key,times);
			else
				result=ArrayUtils.addAll(result, decrypt(data,i,key,times));
		}
		return result;
	}
	// 加密
	public static byte[] encrypt(byte[] content, int offset, int[] key, int times) {// times为加密轮数
		int[] tempInt = byteToInt(content, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0, i;
		int delta = 0x9e3779b9; // 这是算法标准给的值
		int a = key[0], b = key[1], c = key[2], d = key[3];

		for (i = 0; i < times; i++) {
			sum += delta;
			y += ((z << 4) + a) ^ (z + sum) ^ ((z >>> 5) + b);
			z += ((y << 4) + c) ^ (y + sum) ^ ((y >>> 5) + d);
		}
		tempInt[0] = y;
		tempInt[1] = z;
		return intToByte(tempInt, 0);
	}

	// 解密
	public static byte[] decrypt(byte[] encryptContent, int offset, int[] key,
			int times) {
		int[] tempInt = byteToInt(encryptContent, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0xC6EF3720, i;
		int delta = 0x9e3779b9; // 这是算法标准给的值
		int a = key[0], b = key[1], c = key[2], d = key[3];

		for (i = 0; i < times; i++) {
			z -= ((y << 4) + c) ^ (y + sum) ^ ((y >>> 5) + d);
			y -= ((z << 4) + a) ^ (z + sum) ^ ((z >>> 5) + b);
			sum -= delta;
		}
		tempInt[0] = y;
		tempInt[1] = z;

		return intToByte(tempInt, 0);
	}

	// byte[]型数据转成int[]型数据
	public static int[] byteToInt(byte[] content, int offset) {

		int[] result = new int[2]; // 除以2的n次方 == 右移n位 即
														// content.length / 4 ==
														// content.length >> 2
		for (int i = 0, j = offset; j < offset+8; i++, j += 4) {
			result[i] = (content[j + 3] & 255)
					| ((content[j + 2] & 255) << 8)
					| ((content[j + 1] & 255) << 16)
					| ((int) content[j] << 24);
		}
		return result;

	}

	// int[]型数据转成byte[]型数据
	public static byte[] intToByte(int[] content, int offset) {
		byte[] result = new byte[8]; // 乘以2的n次方 == 左移n位 即
														// content.length * 4 ==
														// content.length << 2
		for (int i = 0, j = offset; j < offset+8; i++, j += 4) {
			result[j + 3] = (byte) (content[i] & 0xff);
			result[j + 2] = (byte) ((content[i] >>> 8) & 0xff);
			result[j + 1] = (byte) ((content[i] >>> 16) & 0xff);
			result[j] = (byte) ((content[i] >>> 24) & 0xff);
		}
		return result;
	}

	// 若某字节被解释成负的则需将其转成无符号正数
	private static int transform(byte temp) {
		int tempInt = (int) temp;
		if (tempInt < 0) {
			tempInt += 256;
		}
		return tempInt;
	}

}

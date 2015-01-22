package com.artigence.smarthome.communication.codec.arithmetic;

public class CheckSum {

	public long check(byte[] data){
		int sum = 0;
		int tmp = 0;
		byte tmp8 = 0;

		if(data!=null && data.length>0){
			int i = 0;
			int len = data.length;
			while(len > 1){
				tmp8 = data[i++];
				tmp = (data[i++]&0xFF);
				sum += (tmp << 8);
				sum += (tmp8&0xFF);
				len -= 2;
			}
			
			if(len>0){
				sum += (int)data[i];
			}
			
			sum = (sum >>> 16) + (sum & 0xFFFF);
			sum += (sum >>> 16);
		}
		
		
		return (~sum) & 0x0000FFFFL;
	}
}

package com.artigence.smarthome.communication.session;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CIDTest {

	@Test
	public void test() {
		CID c1 = new CID(ClientType.ARTI,1l);
		CID c2 = new CID(ClientType.ARTI,1l);
		CID c3 = new CID(ClientType.ARTI,1l);
		CID c4 = new CID(ClientType.USER,1l);
		Map<CID,String> map = new HashMap<CID,String>();
		map.put(c1, "c1");
		System.out.println("c1 == c2:"+(c1 == c2));
		System.out.println("c1 equals c2:"+(c1.equals(c2)));
		System.out.println("map.get(c2):"+map.get(c2));
		System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
		
		System.out.println("c1 == c3:"+(c1 == c3));
		System.out.println("c1 equals c3:"+(c1.equals(c3)));
		System.out.println("map.get(c3):"+map.get(c3));
		System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
		
		System.out.println("c1 == c4:"+(c1 == c4));
		System.out.println("c1 equals c4:"+(c1.equals(c4)));
		System.out.println("map.get(c4):"+map.get(c4));
	}

	@Test
	public void testHex() throws DecoderException{
		String str = "0001";
		String str1 = "f002";
		String str2 = "feee";
		System.out.println(Hex.encodeHexString(Hex.decodeHex(str.toCharArray())));
		System.out.println(Hex.encodeHexString(Hex.decodeHex(str1.toCharArray())));
		System.out.println(Hex.encodeHexString(Hex.decodeHex(str2.toCharArray())));
	}
}

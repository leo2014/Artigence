package com.artigence.smarthome.service;

import java.security.KeyPair;
import org.junit.Test;

import com.artigence.smarthome.service.util.RSAUtil;

public class RSAUtilTest {

	@Test
	public void testDecryptStringByJs() {
		String plainText = "123456";
		for(char c : plainText.toCharArray())
			System.out.println("encrypttext.toCharArray():"+c);
		//Hex.decodeHex();
		String miwen = "";
		KeyPair keyPair = RSAUtil.generateKeyPair();
		miwen = RSAUtil.encryptString(plainText, keyPair);
		System.out.println("miwen: "+miwen);
		String jiemi = RSAUtil.decryptString(miwen, keyPair);
		String jiemijs = RSAUtil.decryptString(keyPair.getPrivate(),miwen );
		
		System.out.println("jiemi: "+jiemi);
		System.out.println("jiemijs: "+jiemijs);
	}

}

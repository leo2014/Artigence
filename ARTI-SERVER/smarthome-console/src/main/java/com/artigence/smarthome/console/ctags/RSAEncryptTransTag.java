package com.artigence.smarthome.console.ctags;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;



import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.artigence.smarthome.service.user.LoginAndRegisterService;


public class RSAEncryptTransTag extends RequestContextAwareTag {

	private static final long serialVersionUID = -6657028318113235421L;

	@Override
	public int doStartTagInternal() throws Exception {
		
		HttpSession session = this.pageContext.getSession();	
		LoginAndRegisterService lars =  (LoginAndRegisterService) this.getRequestContext().getWebApplicationContext().getBean("loginAndRegisterServiceImp");
		KeyPair keyPair=lars.createRsaKey(session.getId());
		RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
		//System.out.println(session.getId());
		session.setAttribute("modulus", new String(Hex.encodeHex(publicKey.getModulus().toByteArray())));
		session.setAttribute("exponent", new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray())));
		
		return EVAL_PAGE; // 表示处理完标签后继续执行以下的JSP网页  BigInteger 
		// return SKIP_PAGE; //表示不处理接下来的JSP网页  
	}
	

}

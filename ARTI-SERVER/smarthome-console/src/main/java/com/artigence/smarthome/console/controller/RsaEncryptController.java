package com.artigence.smarthome.console.controller;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.console.vo.RsaPublicKey;
import com.artigence.smarthome.service.user.LoginAndRegisterService;
@Controller
@RequestMapping("/encrypt")
public class RsaEncryptController extends BaseSpringController {

	@Resource
	private LoginAndRegisterService loginAndRegisterService;
	
	@RequestMapping
	public @ResponseBody RsaPublicKey getPublicKey(HttpSession session){
		
		KeyPair keyPair = loginAndRegisterService.createRsaKey(session.getId());
		RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
		return new RsaPublicKey(publicKey.getModulus().toString(16),
				publicKey.getPublicExponent().toString(16));
	}

	public LoginAndRegisterService getLoginAndRegisterService() {
		return loginAndRegisterService;
	}

	public void setLoginAndRegisterService(
			LoginAndRegisterService loginAndRegisterService) {
		this.loginAndRegisterService = loginAndRegisterService;
	}
	
	
}

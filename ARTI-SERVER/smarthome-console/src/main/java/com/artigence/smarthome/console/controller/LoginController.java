package com.artigence.smarthome.console.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.service.exception.BusinessException;
import com.artigence.smarthome.service.user.LoginAndRegisterService;
import com.artigence.smarthome.service.user.dto.UserVo;
@Controller

public class LoginController extends BaseSpringController {

	@Resource
	private LoginAndRegisterService loginAndRegisterService;
	
	@RequestMapping("/login")
	public String lgoin(HttpSession session, Model model){
		return "/signin";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(UserVo userVo, Model model,HttpSession session){
		userVo.setSessionId(session.getId());
		try{
			userVo = this.loginAndRegisterService.login(userVo);
		}catch(BusinessException bex){
			System.out.println("bussiness exception login field"+bex.getCode());
			model.addAttribute("validResult",bex);
			return "/signin";
		}
		session.setAttribute("userVo", userVo);
		if(userVo.getEmailValidation())
			return "/user/home";
		else
			return "/emailvali";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("userVo");
		session.invalidate();
		return "/signin";
	}
}

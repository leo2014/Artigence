package com.artigence.smarthome.console.controller;

import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.console.vo.AjaxValidResult;
import com.artigence.smarthome.service.exception.BusinessException;
import com.artigence.smarthome.service.user.LoginAndRegisterService;
import com.artigence.smarthome.service.user.UserService;
import com.artigence.smarthome.service.user.dto.UserVo;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseSpringController {
	
	@Resource
	private UserService userService;
	@Resource
	private LoginAndRegisterService loginAndRegisterService;
	@Resource
	private UserDetailsService userDetailsService;
	@RequestMapping
	public String register(HttpSession session, Model model){
		return "/signup";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String signUp(@Valid UserVo userVo, BindingResult validResult,HttpSession session, Model model){
		if(validResult.hasErrors()){
			model.addAttribute("validResult",validResult);
			return "/signup";
		}
		session.setAttribute("securityCode", new Random().nextInt(9999)%(9999-1000+1)+1000);
		System.out.println("securityCode:"+session.getAttribute("securityCode"));
		userVo.setSessionId(session.getId());
		try{
			this.loginAndRegisterService.register(userVo);
		}catch(BusinessException bex){
			model.addAttribute("validResultEx",bex);
			return "/signup";
		}
		userVo = userService.getUserVo(userVo.getUsername());
		session.setAttribute("userVo", userVo);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(userVo.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetails,userDetails.getPassword(),userDetails.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		return "emailvali";
	}
	
	@RequestMapping(value="/ajaxValid",method = RequestMethod.POST)
	public @ResponseBody AjaxValidResult usernameValid(@Valid AjaxValidResult result){
		if(userService.userIsUnique(result.getValue()))
			result.setValid(1);
		else
			result.setValid(0);
		return result;
	}
	
}

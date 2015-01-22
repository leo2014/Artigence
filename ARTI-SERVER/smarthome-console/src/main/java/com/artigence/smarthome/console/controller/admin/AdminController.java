package com.artigence.smarthome.console.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.console.vo.AjaxValidResult;
import com.artigence.smarthome.service.user.UserService;
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseSpringController {

	@Resource
	private UserService userService;
	
	@RequestMapping
	public String admin(){
		return "/admin/home";
	}
	
	@RequestMapping("/userinfo")
	public String userinfo(HttpSession session){
		return "/admin/userinfo";
	}
	
	@RequestMapping(value="/isEmailExist",method=RequestMethod.POST)
	public @ResponseBody AjaxValidResult isEmailExist(@Valid AjaxValidResult result){
		result.setValid(0);
		if(this.userService.emailIsUnique(result.getValue()))
			result.setValid(1);
		return result;
	}
}

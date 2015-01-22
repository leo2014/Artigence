package com.artigence.smarthome.console.controller.user;

import java.util.Random;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.console.vo.AjaxValidResult;
import com.artigence.smarthome.service.exception.BusinessException;
import com.artigence.smarthome.service.user.UserService;
import com.artigence.smarthome.service.user.dto.UserVo;
@Controller
@RequestMapping("/user")
public class UserController extends BaseSpringController {

	Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private UserService userService;
	
	@RequestMapping
	public String home(){
		return "/user/home";
	}
	
	@RequestMapping("/userinfo")
	public String userinfo(HttpSession session){
		return "/user/userinfo";
	}
	
	@RequestMapping("/{email}/sendEmailValid")
	public String sendEmailValid(@PathVariable String email,HttpSession session) throws MessagingException{
		Integer securityCode = (Integer) session.getAttribute("securityCode");
		if(securityCode == null){
			securityCode = new Random().nextInt(9999)%(9999-1000+1)+1000;
			session.setAttribute("securityCode", securityCode);
		}
		
		UserVo userVo = (UserVo) session.getAttribute("userVo");
		if(userVo==null)
			throw new BusinessException("session","session is desitory");
		userVo.setEmail(email);
		try{
		userService.sendEmailValid(securityCode, userVo);
		}catch(BusinessException be){
			return be.getCode();
		}
		return null;
	}

	@RequestMapping(value="/isEmailExist",method=RequestMethod.POST)
	public @ResponseBody AjaxValidResult isEmailExist(@Valid AjaxValidResult result){
		result.setValid(0);
		if(this.userService.emailIsUnique(result.getValue()))
			result.setValid(1);
		return result;
	}
	
	@RequestMapping(value="/emailValid", method=RequestMethod.POST)
	public String emailValid(@RequestParam("emailCode") Integer secrityCode,HttpSession session){
		if(secrityCode==null)
			return "/emailVali";
		if(secrityCode.intValue() == ((Integer)session.getAttribute("securityCode")).intValue()){
			UserVo userVo = (UserVo)session.getAttribute("userVo");
			this.userService.emailValid(userVo);
			return "/user/home";
		}
		return "/emailVali";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(UserVo userVo,HttpSession session){
		UserVo user = (UserVo)session.getAttribute("userVo");
		userVo.setUsername(user.getUsername());
		user.setName(userVo.getName());
		user.setEmail(userVo.getEmail());
		user.setAge(userVo.getAge());
		user.setAddress(userVo.getAddress());
		user.setCellphone(userVo.getCellphone());
		this.userService.updateUser(user);
		session.setAttribute("userVo",user);
		return "/user/userinfo";
	}
	
}

package com.artigence.smarthome.console.controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.artigence.smarthome.persist.dao.UserDao;
import com.artigence.smarthome.persist.dao.UserLoginRecordDao;
import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.UserLoginRecord;
import com.artigence.smarthome.persist.model.security.Role;
import com.artigence.smarthome.service.user.UserService;
import com.artigence.smarthome.service.user.dto.UserVo;
@Service
public class LoginSuccessHandle implements AuthenticationSuccessHandler {

	@Resource
	private UserLoginRecordDao userLoginRecordDao;
	@Resource
	private UserDao userDao;
	@Resource
	private UserService userService;
	
	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String path = request.getContextPath();								
		
//		Collection<? extends GrantedAuthority> authCollection = authentication.getAuthorities();
//		GrantedAuthority[] a = new SimpleGrantedAuthority[]{};  
//		if(authCollection.toArray(a)[0].toString()=="ROLE_ADMIN"){
//			response.sendRedirect(path+"/admin");
//		}else{
//			response.sendRedirect(path+"/user");
//		}
//		System.out.println(authentication.getName());
		User user = userDao.getUserByUsername(authentication.getName());
		boolean isRed = false;
		for(Role role : user.getRoles()){
			if(role.getName().equals("ROLE_ADMIN")){
				response.sendRedirect(path+"/admin");
				isRed = true;
			}
		}
		if(!isRed)
			response.sendRedirect(path+"/user");
		UserLoginRecord ulr = new UserLoginRecord();
		ulr.setUser(user);
		ulr.setLoginTime(new Date());
		userLoginRecordDao.save(ulr);
		
		UserVo userVo=userService.getUserVo(user.getUsername());
		request.getSession().setAttribute("userVo", userVo);
	}

	public void setUserLoginRecordDao(UserLoginRecordDao userLoginRecordDao) {
		this.userLoginRecordDao = userLoginRecordDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}

package com.artigence.smarthome.console.controller;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.service.GatewayService;
import org.androidpn.server.service.UserService;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.artigence.smarthome.persist.dao.DataTransferRecordDao;
import com.artigence.smarthome.persist.exception.UserExistsException;
import com.artigence.smarthome.persist.exception.UserNotFoundException;
import com.artigence.smarthome.persist.model.DataTransferRecord;
import com.artigence.smarthome.persist.model.Arti;
import com.artigence.smarthome.persist.model.User;


public class UserController {
	
    private NotificationManager notificationManager;

	private UserService userService;

	private GatewayService gatewayService;

	private DataTransferRecordDao commandDao;
	
	public ModelAndView register(HttpServletRequest request,
            HttpServletResponse response) throws UserExistsException{
		User user = new User();
		user.setName("ripon");
		user.setPassword("123456");
		user.setUsername("ripon");
		user.setEmail("ripon@qq.com");
		userService.saveUser(user);
		return null;
	}
	

	public ModelAndView login(String name, String passsword){
		return null;
	}
	
	@RequestMapping(params="method=notification")
	public void notification() throws UserNotFoundException, UnsupportedEncodingException{
		DataTransferRecord command = new DataTransferRecord();
		User user = userService.getUserByUsername("ripon");
		Arti gateway = gatewayService.getByUser(user.getId()).get(0);
//		command.setUser(user);
//		command.setGateway(gateway);
//		command.setRemark("test");
//		command.setCmd(new Byte[]{11});
//		command.setCreatedDate(new Date());
//		command.setExec(false);
//		command.setSuccess(false);
		commandDao.saveCommand(command);
		new NotificationManager().sendCommandToGateway(command);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setGatewayService(GatewayService gatewayService) {
		this.gatewayService = gatewayService;
	}
	
	
}

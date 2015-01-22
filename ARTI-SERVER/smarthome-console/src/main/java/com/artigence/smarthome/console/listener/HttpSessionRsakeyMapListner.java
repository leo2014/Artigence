package com.artigence.smarthome.console.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.artigence.smarthome.service.user.LoginAndRegisterService;
/**
 * HttpSessionRsakeyMapListner listen user session status
 * @author Leipeng
 *
 */
public class HttpSessionRsakeyMapListner implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * if session is destoryed ,login and register RSAkey is Destroy
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		LoginAndRegisterService lars =  
				(LoginAndRegisterService)WebApplicationContextUtils.getWebApplicationContext(
						session.getServletContext()).getBean("loginAndRegisterServiceImp");
	
			
		lars.getRsaKeyPairMap().remove(session.getId());
		

	}

}

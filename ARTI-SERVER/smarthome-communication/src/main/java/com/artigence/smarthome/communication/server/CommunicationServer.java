package com.artigence.smarthome.communication.server;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class CommunicationServer implements ApplicationContextAware {
	private static CommunicationServer cs;
	private static ApplicationContext ac;
	private CommunicationServer(){}

	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		CommunicationServer.ac = ac;
	}
	
	public static CommunicationServer getInstance(){
		if(cs == null)
			cs = (CommunicationServer) ac.getBean("communicationServer");
		return cs;
	}
	
	public static Object getBean(String name){
		return CommunicationServer.ac.getBean(name);
	}
	
	public static ApplicationContext getAc(){
		return CommunicationServer.ac;
	}
	
}

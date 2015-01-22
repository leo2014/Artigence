package com.artigence.smarthome.communication.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartUp {
	
	public static void main(String arg[]){
		ApplicationContext context =
				new ClassPathXmlApplicationContext(new String[] {"spring-communication.xml"});
	
	}
}

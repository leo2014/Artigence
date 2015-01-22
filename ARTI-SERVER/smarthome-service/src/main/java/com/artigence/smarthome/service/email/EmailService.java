package com.artigence.smarthome.service.email;

import javax.mail.MessagingException;

import com.artigence.smarthome.service.imp.email.MailBean;

/**
 * @date 2014年4月22日 下午6:00:19
 * @author Leipeng
 *	email send service interface
 */
public interface EmailService {
	
	
	/**
	 * send email
	 * @param text
	 * @throws MessagingException 
	 */
	public boolean send(MailBean mailBean) throws MessagingException;
	

}

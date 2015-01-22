package com.artigence.smarthome.service.email;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.artigence.smarthome.service.imp.email.MailBean;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-service.xml")
public class EmailServiceTest {

	@Resource
	private EmailService emailService;
	
	@Ignore
	@Test
	public void test() {
		 //创建邮件  
        MailBean mailBean = new MailBean();  
        mailBean.setFrom("leipeng@artigence.com.cn");  
        mailBean.setSubject("hello world");  
        mailBean.setToEmails(new String[]{"lopoo1@qq.com"});  
        mailBean.setTemplate("hello ${userName} !<a href='www.baidu.com' >baidu</a>");  
        Map map = new HashMap();  
        map.put("userName", "leipeng");  
        mailBean.setData(map);  
          
        //发送邮件  
        try {  
        	emailService.send(mailBean);  
        } catch (MessagingException e) {  
            e.printStackTrace();  
        }  
	}

}

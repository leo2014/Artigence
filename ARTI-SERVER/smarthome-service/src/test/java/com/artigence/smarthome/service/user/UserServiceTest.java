package com.artigence.smarthome.service.user;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.service.arti.ArtiService;
import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.user.dto.UserVo;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-test.xml")
@Ignore
public class UserServiceTest {

	@Resource
	private UserService userService;
	@Resource
	private LoginAndRegisterService loginAndRegisterService;
	@Resource
	private UserGroupService userGroupService;
	@Resource
	private ArtiService artiService;
	

	
	
	public void createTestData(){
	
		for(int i=0; i<5; i++){
			UserVo userVo = new UserVo();
			userVo.setEmail(UUID.randomUUID().toString().substring(0,10)+"@qq.com");
			
			userVo.setUsername(UUID.randomUUID().toString().substring(0,10)+"");
			userVo.setPassword(UUID.randomUUID().toString().substring(0,10)+"");
			System.out.println(userVo.getEmail()+"  "+userVo.getUsername()+"  "+userVo.getPassword());
			loginAndRegisterService.register(userVo);
		}
	}
	

	public void createTestUserGroup(){
		ArtiVo artiVo = new ArtiVo();
		artiVo.setMac("234567");
		artiVo.setName("dddd");
		artiVo.setUsername("admin");
		artiVo.setSerialNum("2313134532452314");
		artiVo.setPassword("123456");
		artiService.register(artiVo);
	}
	
	public void createTestRole(){
		
	}
	
	@Test
	public void testUserPage() {
		PageList<User> userPage = userService.getUsers(1, 10);
		for(User user : userPage){
			System.out.println(user.getId());
		}
	}
	@Test
	public void testUserVoPage(){
		PageList<UserVo> userVoPage = userService.getUserVos(1, 10);
		for(UserVo userVo : userVoPage){
			System.out.println(userVo);
		}
	}

}

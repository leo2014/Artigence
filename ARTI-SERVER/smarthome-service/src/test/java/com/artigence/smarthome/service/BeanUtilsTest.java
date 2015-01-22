package com.artigence.smarthome.service;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.artigence.smarthome.persist.model.User;

public class BeanUtilsTest {

	@Test
	public void test() {
		User user = new User();
		User copyUser = new User();
		
		user.setId(1l);
		user.setAge(12);
		
		copyUser.setAge(200);
		System.out.println("user:Id"+user.getId()+",age:"+user.getAge());
		BeanUtils.copyProperties(copyUser,user);
		System.out.println("user:Id"+user.getId()+",age:"+user.getAge());

	}

}

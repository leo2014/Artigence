package com.artigence.smarthome.persist.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.UserGroup;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-test.xml")
@TransactionConfiguration(transactionManager="transactionManager")
@Ignore
public class UserDaoTest {

	@Resource
	private UserDao dao;

	
	public void testGetUser() {
		fail("Not yet implemented");
	}

	@Test
	@Transactional
	@Rollback(false)
	public void testSaveUser() {
        User user = null;
   
		user = dao.getUserByUsername("admin");
		if(user==null){
			
		
        user = new User();
        user.setEmail("admin@domain.com");
        user.setName("admin");
        user.setUsername("admin");
        user.setPassword("123456");
        user.setAddress("admin's home");
        user.setAge(22);
        user.setCellphone("11111111111");
        //user.setLastLoginDate(new Date());
        user.setEmailValidation(true);
        user.setRegisterDate(new Date());
        user = dao.saveUser(user);
		}
        assertNotNull(user.getId());
	}


	@Transactional
	public void testGetUserVos(){
		UserGroup userGroup = new UserGroup();
		userGroup.setId(1l);
		System.out.println(dao.pageQuery(1, 5,userGroup , "userGroup").get(0).getEmail());
	}
	public void testRemoveUser() {
		fail("Not yet implemented");
	}

	
	public void testExists() {
		fail("Not yet implemented");
	}


	public void testGetUsers() {
		fail("Not yet implemented");
	}


	public void testGetUserByUsername() {
		fail("Not yet implemented");
	}


	public void testGetById() {
		fail("Not yet implemented");
	}


	public void testDeleteById() {
		fail("Not yet implemented");
	}


	public void testDelete() {
		fail("Not yet implemented");
	}


	public void testSave() {
		fail("Not yet implemented");
	}


	public void testUpdate() {
		fail("Not yet implemented");
	}


	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}


	public void testFlush() {
		fail("Not yet implemented");
	}


	public void testClear() {
		fail("Not yet implemented");
	}


	public void testFindAll() {
		fail("Not yet implemented");
	}


	public void testFindByKey() {
		fail("Not yet implemented");
	}


	public void testFindAllStringBoolean() {
		fail("Not yet implemented");
	}


	public void testFindAllFieldValue() {
		fail("Not yet implemented");
	}


	public void testPageQueryIntIntDetachedCriteria() {
		fail("Not yet implemented");
	}


	public void testPageQueryIntIntDetachedCriteriaOrderArray() {
		fail("Not yet implemented");
	}


	public void testFindByExample() {
		fail("Not yet implemented");
	}


	public void testGetByExample() {
		fail("Not yet implemented");
	}

}

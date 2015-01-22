package com.artigence.smarthome.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.artigence.smarthome.persist.dao.UserDao;
import com.artigence.smarthome.persist.model.User;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-test.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
@Ignore
public class UserDaoHibernateTest {

	@Resource
	private UserDao dao;

//    @Test
//    @Transactional
//    public void testGetUser() throws Exception {
//        User user = dao.getUser(1L);
//        assertNotNull(user);
//        assertEquals("admin@domain.com", user.getEmail());
//    }
    
    @Test
    @Transactional
    public void testAddAndRemoveUser() throws Exception {
        User user = new User();
        user.setEmail("testuser@domain.com");
        user.setName("Test User");
        user.setUsername("testuser");
        user.setPassword("test1234");
        
        user = dao.saveUser(user);

        assertNotNull(user.getId());
        Long d = user.getId();
        user=null;
        user = dao.getUser(d);
        assertEquals("test1234", user.getPassword());
//
//        dao.removeUser(user.getId());
//        user = dao.getUser(user.getId());
//        assertNotNull(user);
    }
//    @Transactional
//    @Test
//    public void testUpdateUser() throws Exception {
//        User user = dao.getUser(1L);
//        dao.saveUser(user);
//        user = dao.getUser(1L);
//        user.setId(null);
//     
//       
//        dao.saveUser(user);
//
//    }

//    @Transactional
//    @Test
//    public void testUserExists() throws Exception {
//        assertTrue(dao.exists(1L));
//    }
//    @Transactional
//    @Test
//    public void testUserNotExists() throws Exception {
//        assertFalse(dao.exists(-1L));
//    }
}

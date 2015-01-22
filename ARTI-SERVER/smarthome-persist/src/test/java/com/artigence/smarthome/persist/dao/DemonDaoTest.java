package com.artigence.smarthome.persist.dao;


import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-test.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class DemonDaoTest {
	@Resource
	private DemonDao demonDao;

	@Test
	@Rollback(false)
	public void testDeleteAll() {
		demonDao.deleteAll();
	}

}

package com.artigence.smarthome.persist.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.artigence.smarthome.persist.dao.DemonDao;
import com.artigence.smarthome.persist.dao.base.hibernate.EntityDaoSupport;
import com.artigence.smarthome.persist.model.Demon;
@Repository
public class DemonDaoHibernate extends EntityDaoSupport<Demon, Long> implements
		DemonDao {

	@Override
	public void deleteAll() {
		this.currentSession().createQuery("delete Demon").executeUpdate();
		
	}

	
}

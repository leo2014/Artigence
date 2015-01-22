package com.artigence.smarthome.persist.dao.security.hibernate;

import org.springframework.stereotype.Repository;

import com.artigence.smarthome.persist.dao.base.hibernate.EntityDaoSupport;
import com.artigence.smarthome.persist.dao.security.ResourceDao;
import com.artigence.smarthome.persist.model.security.Resource;
@Repository
public class ResourceDaoHibernate extends EntityDaoSupport<Resource, Long> implements
		ResourceDao {

}

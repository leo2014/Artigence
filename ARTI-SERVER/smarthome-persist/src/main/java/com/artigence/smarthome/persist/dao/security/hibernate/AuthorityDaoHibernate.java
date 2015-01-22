package com.artigence.smarthome.persist.dao.security.hibernate;

import org.springframework.stereotype.Repository;

import com.artigence.smarthome.persist.dao.base.hibernate.EntityDaoSupport;
import com.artigence.smarthome.persist.dao.security.AuthorityDao;
import com.artigence.smarthome.persist.model.security.Authority;
@Repository
public class AuthorityDaoHibernate extends EntityDaoSupport<Authority, Long> implements
		AuthorityDao {


}

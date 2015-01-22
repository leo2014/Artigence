package com.artigence.smarthome.persist.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.artigence.smarthome.persist.dao.UserGroupDao;
import com.artigence.smarthome.persist.dao.base.hibernate.EntityDaoSupport;
import com.artigence.smarthome.persist.model.UserGroup;
@Repository
public class UserGroupDaoHibernate extends EntityDaoSupport<UserGroup, Long>
		implements UserGroupDao {

	

}

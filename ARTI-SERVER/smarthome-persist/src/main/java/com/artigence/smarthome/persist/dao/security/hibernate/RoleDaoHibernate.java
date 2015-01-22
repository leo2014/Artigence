package com.artigence.smarthome.persist.dao.security.hibernate;

import org.springframework.stereotype.Repository;

import com.artigence.smarthome.persist.dao.base.hibernate.EntityDaoSupport;
import com.artigence.smarthome.persist.dao.security.RoleDao;
import com.artigence.smarthome.persist.model.security.Role;
@Repository
public class RoleDaoHibernate extends EntityDaoSupport<Role, Long> implements
		RoleDao {


}

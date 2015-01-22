package com.artigence.smarthome.persist.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.artigence.smarthome.persist.dao.UserLoginRecordDao;
import com.artigence.smarthome.persist.dao.base.hibernate.EntityDaoSupport;
import com.artigence.smarthome.persist.model.UserLoginRecord;
@Repository
public class UserLoginRecordDaoHibernate extends EntityDaoSupport<UserLoginRecord, Long>
	implements UserLoginRecordDao {


}

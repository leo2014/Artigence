package com.artigence.smarthome.persist.dao;

import com.artigence.smarthome.persist.dao.base.EntityDao;
import com.artigence.smarthome.persist.model.Demon;

public interface DemonDao extends EntityDao<Demon, Long> {
	public void deleteAll();
}

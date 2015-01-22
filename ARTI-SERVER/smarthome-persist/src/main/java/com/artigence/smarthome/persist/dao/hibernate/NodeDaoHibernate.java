package com.artigence.smarthome.persist.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.artigence.smarthome.persist.dao.NodeDao;
import com.artigence.smarthome.persist.dao.base.hibernate.EntityDaoSupport;
import com.artigence.smarthome.persist.model.Node;
@Repository
public class NodeDaoHibernate extends EntityDaoSupport<Node, Long>
		implements NodeDao {

}

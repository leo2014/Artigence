package com.artigence.smarthome.persist.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.artigence.smarthome.persist.dao.ArtiDao;
import com.artigence.smarthome.persist.dao.base.hibernate.EntityDaoSupport;
import com.artigence.smarthome.persist.model.Arti;
@Repository
public class ArtiDaoHibernate extends EntityDaoSupport<Arti, Long> implements
		ArtiDao {

	@Override
	public Arti getGateway(Long id) {
		return (Arti) getHibernateTemplate().get(Arti.class, id);
	}

	@Override
	public Arti saveGateway(Arti gateway) {
		getHibernateTemplate().saveOrUpdate(gateway);
        getHibernateTemplate().flush();
        return gateway;
	}

	@Override
	public void removeGateway(Long id) {
		getHibernateTemplate().delete(getGateway(id));

	}

	@Override
	public boolean exists(Long id) {
		Arti gateway = (Arti) getHibernateTemplate().get(Arti.class, id);
        return gateway != null;
	}

	@Override
	public List<Arti> getGateway() {
		return (List<Arti>)getHibernateTemplate().find(
                "from Gateway g order by g.id desc");
	}

	@Override
	public Arti getGatewayByUser(Long userid) {
		List gateways = getHibernateTemplate().find("from Gateway where uid=?",
				userid);
       
        return (Arti) gateways.get(0);
       
	}

}

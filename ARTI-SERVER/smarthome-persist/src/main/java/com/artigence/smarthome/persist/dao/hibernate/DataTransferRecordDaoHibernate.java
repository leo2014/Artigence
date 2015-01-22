package com.artigence.smarthome.persist.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.artigence.smarthome.persist.dao.DataTransferRecordDao;
import com.artigence.smarthome.persist.dao.base.hibernate.EntityDaoSupport;
import com.artigence.smarthome.persist.model.Arti;
import com.artigence.smarthome.persist.model.DataTransferRecord;
@Repository
public class DataTransferRecordDaoHibernate extends EntityDaoSupport<DataTransferRecord, Long> implements
		DataTransferRecordDao {

	@Override
	public DataTransferRecord getCommand(Long id) {
		return (DataTransferRecord) getHibernateTemplate().get(DataTransferRecord.class, id);
	}

	@Override
	public DataTransferRecord saveCommand(DataTransferRecord command) {
		getHibernateTemplate().saveOrUpdate(command);
        getHibernateTemplate().flush();
        return command;
	}

	@Override
	public void removeCommand(Long id) {
		getHibernateTemplate().delete(getCommand(id));

	}

	@Override
	public boolean exists(Long id) {
		DataTransferRecord command = (DataTransferRecord) getHibernateTemplate().get(DataTransferRecord.class, id);
        return command != null;
	}

	@Override
	public List<DataTransferRecord> getCommand() {
		return (List<DataTransferRecord>)getHibernateTemplate().find(
                "from Command c order by c.id desc");
	}

}

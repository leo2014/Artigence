package com.artigence.smarthome.persist.operate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.artigence.smarthome.persist.model.Arti;
import com.artigence.smarthome.persist.model.DataTransferRecord;
import com.artigence.smarthome.persist.model.User;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	private static SessionFactory buildSessionFactory(){
		return new Configuration().configure()
				.addClass(User.class)
				.addClass(Arti.class)
				.addClass(DataTransferRecord.class)
				.buildSessionFactory(new StandardServiceRegistryBuilder().build());
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}

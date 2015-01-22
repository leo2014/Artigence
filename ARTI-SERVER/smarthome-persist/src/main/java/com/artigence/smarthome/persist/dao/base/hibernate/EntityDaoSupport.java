package com.artigence.smarthome.persist.dao.base.hibernate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Id;

import org.bouncycastle.asn1.isismtt.x509.Restriction;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import cn.org.rapid_framework.page.PageUtils;
import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.dao.base.EntityDao;

public abstract class EntityDaoSupport<E, PK extends Serializable> extends HibernateDaoSupport
	implements EntityDao<E, PK> {

	private Class<E> entityClass;
	private String idFieldName;
	private Class<?> idFieldType;
	private boolean idOrderable;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EntityDaoSupport(){
		Class<?> typeCls = getClass();
		Type genType = typeCls.getGenericSuperclass();
		
		while(!(genType instanceof ParameterizedType)){
			typeCls = typeCls.getSuperclass();
			genType = typeCls.getGenericSuperclass();
		}
		
		entityClass = (Class)((ParameterizedType)genType).getActualTypeArguments()[0];
		
		Field[] fields = entityClass.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(Id.class)){
				this.idFieldName = field.getName();
				this.idFieldType = field.getType();
			}
		}
		this.idOrderable = ((this.idFieldType!=null)
				&&(this.idFieldType.isAssignableFrom(Integer.class)
						||this.idFieldType.isAssignableFrom(String.class)
						||this.idFieldType.isAssignableFrom(Long.class)));
		
	}
	
	@Resource(name="sessionFactory")
	public void setSupperSessionFactory(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public E getById(PK id) {
		if(id==null)
			return null;
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public void deleteById(PK id) {
		E entity = getById(id);
		if(entity!=null)
			delete(entity);
	}

	@Override
	public void batchDelete(PK[] ids) {
		for(int i=0; i<ids.length;i++){
			this.deleteById(ids[i]);
			if(i % 20 == 0){
				this.currentSession().flush();
				this.currentSession().clear();
			}
		}
	}
	
	@Override
	public void delete(E entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public void save(E entity) {
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void update(E entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public void saveOrUpdate(E entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void flush() {
		this.getHibernateTemplate().flush();
	}

	@Override
	public void clear() {
		this.getHibernateTemplate().clear();
	}
	
	public long count(Class clazz){
		return (Long)this.currentSession()
				.createQuery("select count(*) from "+clazz.getName())
				.uniqueResult();
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		Criteria criteria = this.currentSession().createCriteria(entityClass);
		if(this.idOrderable)
			criteria.addOrder(Order.asc(this.idFieldName));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByKey(String fieldName, Object value) {
		Criteria criteria = this.currentSession().createCriteria(entityClass)
				.add(Restrictions.eq(fieldName, value));
		if(this.idOrderable)
			criteria.addOrder(Order.asc(this.idFieldName));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll(String fieldname, boolean isDesc) {
		Criteria criteria = this.currentSession().createCriteria(entityClass)
				.addOrder(isDesc?Order.desc(fieldname):Order.asc(fieldname));
		if((!(fieldname.equals(idFieldName)))&&(idOrderable))
			criteria.addOrder(Order.asc(this.idFieldName));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findAllFieldValue(String fieldName) {
		Criteria criteria = this.currentSession().createCriteria(entityClass)
				.setProjection(Projections.property(fieldName));
		if(idOrderable){
			criteria.addOrder(Order.asc(idFieldName));
		}
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public E getByUniqueField(String fieldName, Object value){
		Criteria criteria = this.currentSession().createCriteria(entityClass)
				.add(Restrictions.eq(fieldName, value));
		List<E> list = criteria.list();
		return list!=null?list.isEmpty()?null:list.get(0):null;
	}

	@Override
	public PageList<E> pageQuery(int pageNumber, int pageSize,
			DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(this.currentSession());
		int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		if(idOrderable)
			criteria.addOrder(Order.asc(idFieldName));
		
		@SuppressWarnings("unchecked")
		List<E> items = criteria.setFirstResult(PageUtils.getFirstResult(pageNumber<=0?1:pageNumber, pageSize))
				.setMaxResults(pageSize).list();
		return new PageList<E>(items, pageNumber, pageSize, totalCount);
	}

	@Override
	public PageList<E> pageQuery(int start, int pageSize,
			DetachedCriteria detachedCriteria, Order[] orders) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(this.currentSession());
		int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		boolean defaultOrder = true;
		for(Order order : orders){
			criteria.addOrder(order);
			if((order.toString().split(" "))[0].equals(idFieldName))
				defaultOrder = false;
		}
		
		if(defaultOrder && idOrderable)
			criteria.addOrder(Order.asc(idFieldName));
		
		@SuppressWarnings("unchecked")
		List<E> items = criteria.setFirstResult(start)
				.setMaxResults(pageSize).list();
		return new PageList<E>(items, start/pageSize+1, pageSize, totalCount);
	}

	@Override
	public List<E> findByExample(E entity) {
		Criteria criteria = this.currentSession().createCriteria(entityClass)
				.add(Example.create(entity));
		if(idOrderable)
			criteria.addOrder(Order.asc(idFieldName));
		@SuppressWarnings("unchecked")
		List<E> items = criteria.list();
		return items;
	}

	@Override
	public E getByExample(E entity) {
		Criteria criteria = this.currentSession().createCriteria(entityClass)
				.add(Example.create(entity));
		if(idOrderable)
			criteria.addOrder(Order.asc(idFieldName));
		@SuppressWarnings("unchecked")
		List<E> items = criteria.list();
		return items.size()==0?null:items.get(0);
	}

	@SuppressWarnings("unchecked")
	public PageList<E> pageQuery(int page, int pageSize, Object feild, String feildname){
		Criteria criteria = this.currentSession().createCriteria(entityClass);
		List<E> lists = null;
		int totalCount = 0;
		if(feild==null){
			totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		}else{
			totalCount = ((Long)criteria.add(Restrictions.eq(feildname, feild))
				.setProjection(Projections.rowCount())
				.uniqueResult())
				.intValue();
		}
		criteria.setProjection(null);
		lists = criteria.setFirstResult(PageUtils.getFirstResult(page<=0?1:page, pageSize))
				.setMaxResults(pageSize)
				.list();
		return new PageList<E>(lists,page,pageSize,totalCount);
	}
}

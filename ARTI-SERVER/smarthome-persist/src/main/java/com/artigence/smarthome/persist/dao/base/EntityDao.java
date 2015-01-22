package com.artigence.smarthome.persist.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import cn.org.rapid_framework.util.page.PageList;

public interface EntityDao<E, PK extends Serializable> {
	 public E getById(PK id);

	  public void deleteById(PK id);
	  
	  public void batchDelete(PK[] ids);

	  public void delete(E entity);

	  public void save(E entity);

	  public void update(E entity);

	  public void saveOrUpdate(E entity);

	  public void flush();

	  public void clear();
	  
	  public long count(Class clazz);

	  public List<E> findAll();

	  public List<E> findByKey(String fieldName, Object value);
	  
	  public E getByUniqueField(String fieldName, Object value);

	  public List<E> findAll(String fieldname, boolean isDesc);

	  public List<E> findAllFieldValue(String fieldName);
	  
	  PageList<E> pageQuery(int pageNumber, int pageSize, DetachedCriteria detachedCriteria);
	  
	  PageList<E> pageQuery(int pageNumber, int pageSize, DetachedCriteria detachedCriteria, Order[] orders);
	  
	  public List<E> findByExample(E entity);
	  
	  public E getByExample(E entity);
	  
	  public PageList<E> pageQuery(int page, int pageSize, Object feild, String feildname);
}

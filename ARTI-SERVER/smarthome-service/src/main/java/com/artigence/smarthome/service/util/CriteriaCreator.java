package com.artigence.smarthome.service.util;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.artigence.smarthome.service.core.dto.Search;
import com.artigence.smarthome.service.core.dto.SearchParam;

/**
 * Search Parammter switch to hibernate criteria
 * @author ripon
 *
 */
public class CriteriaCreator {
	public static DetachedCriteria createSearchCriteria(Class clazz, SearchParam searchParam){
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		Search search = searchParam.getSearch();
		if(search!=null && search.getValue()!=null){
			dc.add(Restrictions.or());
		}
		return null;
	}
}

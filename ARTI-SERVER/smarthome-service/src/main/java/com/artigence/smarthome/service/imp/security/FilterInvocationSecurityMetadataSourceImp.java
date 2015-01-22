package com.artigence.smarthome.service.imp.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.artigence.smarthome.persist.dao.security.ResourceDao;
import com.artigence.smarthome.persist.model.security.Authority;
import com.artigence.smarthome.persist.model.security.Resource;

@Service("filterInvocationSecurityMetadataSource")
public class FilterInvocationSecurityMetadataSourceImp implements
		FilterInvocationSecurityMetadataSource{

	@Autowired
	private ResourceDao resourceDao;

	private PathMatcher urlMatcher = new AntPathMatcher();

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public FilterInvocationSecurityMetadataSourceImp() {
		//loadResourceDefine();
	}

	public void loadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<Resource> resources = resourceDao.findAll();
		for (Resource r : resources) {
			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			for (Authority auth : r.getAuthorities()) {
				atts.add(new SecurityConfig(auth.getName()));
			}
			resourceMap.put(r.getUrl(), atts);
			System.out.println("resourece Map:url("+r.getUrl()+")auth:("+atts.toString()+")");
		}
	}

	// 根据URL，找到相关的权限配置。
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// object 是一个URL，被用户请求的url。
		String url = ((FilterInvocation) object).getRequestUrl();
		//System.out.println(url);
		int firstQuestionMarkIndex = url.indexOf("?");

		if (firstQuestionMarkIndex != -1) {
			url = url.substring(0, firstQuestionMarkIndex);
		}

		Iterator<String> ite = resourceMap.keySet().iterator();

		while (ite.hasNext()) {
			String resURL = ite.next();
			System.out.println(url);
			if (urlMatcher.match(resURL, url)) {
				System.out.println("Request URL:"+url+" reources:"+resourceMap.get(resURL));
				return resourceMap.get(resURL);
			}
		}
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		atts.add(new SecurityConfig("NO_ACCESS"));
		return atts;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {

		return true;
	}

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
		
	}

}

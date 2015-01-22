package com.artigence.smarthome.service.util;

import java.util.List;

import cn.org.rapid_framework.util.page.PageList;

public class PageUtils {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PageList getPage(List totalList,int page,int pageSize){
		if(page<=0)
			page=1;
		int first = cn.org.rapid_framework.page.PageUtils.getFirstResult(page, pageSize);
		if(totalList==null || totalList.isEmpty() )
			return new PageList(null,0,0,0);
		else if(first>totalList.size()){
			page = totalList.size()/pageSize;
			return new PageList(totalList.subList(page, totalList.size()-1),page,pageSize,totalList.size());
		}else if(page*pageSize>totalList.size()){
			return new PageList(totalList.subList(first-1, totalList.size()),page,pageSize,totalList.size());
		}else{
			return new PageList(totalList.subList(first-1, page*pageSize-1),page,pageSize,totalList.size());
		}
	}
}

package com.artigence.smarthome.service.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

public abstract class DtoBeanUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List copyProperties(List source, Class<?> target, 
			String... ignoreProperties){
		List lists = new ArrayList<Object>();
		for(Object o : source){
			try {
				Object no = target.newInstance();
				BeanUtils.copyProperties(o, no, ignoreProperties);
				lists.add(no);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return lists;
	}
}

package com.artigence.smarthome.console.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cn.org.rapid_framework.beanutils.BeanUtils;
import cn.org.rapid_framework.util.page.PageList;
import cn.org.rapid_framework.util.page.PageQuery;


public class BaseSpringController extends MultiActionController{
	
	static {
	
		ConvertRegisterHelper.registerConverters();
	}
	
	public void copyProperties(Object target,Object source) {
		BeanUtils.copyProperties(target, source);
	}

	public <T> T copyProperties(Class<T> destClass,Object orig) {
		return BeanUtils.copyProperties(destClass, orig);
	}
	
    /**
     * 
     *
     * @see MultiActionController#createBinder(HttpServletRequest,Object)
     */
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
    	binder.registerCustomEditor(Short.class, new CustomNumberEditor(Short.class, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
        binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
    }
	
	public ModelAndView toModelAndView(PageList page,PageQuery pageRequest) {
		return toModelAndView("",page, pageRequest);
	}
	
	public ModelAndView toModelAndView(String tableId,PageList page,PageQuery pageRequest) {
		ModelAndView model = new ModelAndView();
		saveIntoModelAndView(tableId,page,pageRequest,model);
		return model;
	}
	/**
	 * one page has many extremeTable
	 * @param tableId ==extremeTable tableId attribute
	 */
	public void saveIntoModelAndView(String tableId,PageList page,PageQuery pageRequest,ModelAndView model){
		Assert.notNull(tableId,"tableId must be not null");
		Assert.notNull(page,"page must be not null");
		
		model.addObject(tableId+"page", page);
		model.addObject(tableId+"totalRows", new Integer(page.getTotalItems()));
		model.addObject(tableId+"pageRequest", pageRequest);
	}
	
	/**
	 * 
	 */
    protected static void saveMessage(HttpServletRequest request, String message) {
        if (StringUtils.isNotBlank(message)) {
        	List list = getOrCreateRequestAttribute(request, "springMessages",ArrayList.class);
            list.add(message);
        }
    }
    /**
	 * 
	 */
    protected static void saveError(HttpServletRequest request, String errorMsg) {
        if (StringUtils.isNotBlank(errorMsg)) {
        	List list = getOrCreateRequestAttribute(request, "springErrors",ArrayList.class);
            list.add(errorMsg);
        }
    }
    
	public static <T> T getOrCreateRequestAttribute(HttpServletRequest request, String key,Class<T> clazz) {
		Object value = request.getAttribute(key);
		if(value == null) {
			try {
				value = clazz.newInstance();
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
			}
			request.setAttribute(key, value);
		}
		return (T)value;
	}
	
}

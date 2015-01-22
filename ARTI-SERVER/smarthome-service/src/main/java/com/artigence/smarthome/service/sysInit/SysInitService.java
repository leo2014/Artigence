package com.artigence.smarthome.service.sysInit;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.artigence.smarthome.service.imp.security.FilterInvocationSecurityMetadataSourceImp;
@Service
public class SysInitService implements ApplicationListener<ApplicationEvent> {
	private Log logger = LogFactory.getLog(SysInitService.class);
    
    private static boolean isStart = false;

    @Resource
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;
	@Override
	@Transactional
	public void onApplicationEvent(ApplicationEvent arg0) {
		if(!isStart){
			isStart = true;
			((FilterInvocationSecurityMetadataSourceImp)filterInvocationSecurityMetadataSource).loadResourceDefine();
			logger.info("filterInvocationSecurityMetadataSource init");
		}
		
	}

}

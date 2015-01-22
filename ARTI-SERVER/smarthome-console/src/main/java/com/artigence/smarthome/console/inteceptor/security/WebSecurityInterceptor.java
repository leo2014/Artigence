package com.artigence.smarthome.console.inteceptor.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * this class with getting SecurityMetadataSource
 * 
 * @author ripon
 * 
 */
public class WebSecurityInterceptor extends AbstractSecurityInterceptor
		implements Filter {

	 private FilterInvocationSecurityMetadataSource securityMetadataSource;  
	    private static Log logger = LogFactory.getLog(WebSecurityInterceptor.class);  
	  
	    // ~ Methods  
	    // ========================================================================================================  
	  
	    /** 
	     * Method that is actually called by the filter chain. Simply delegates to 
	     * the {@link #invoke(FilterInvocation)} method. 
	     *  
	     * @param request  the servlet request 
	     * @param response   the servlet response 
	     * @param chain  the filter chain 
	     * @throws IOException   if the filter chain fails 
	     * @throws ServletException if the filter chain fails 
	     */  
	    public void doFilter(ServletRequest request, ServletResponse response,  
	            FilterChain chain) throws IOException, ServletException {  
	        //@1  
	    	System.out.println("WebSecurityInterceptor=================================");
	        HttpServletRequest httpRequest = (HttpServletRequest)request;  
	        HttpServletResponse httpResponse = (HttpServletResponse)response;  
	        String url = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");  
	          
//	        //  1.1）判断登陆状态：如果未登陆则要求登陆  
//	        if(!SessionUserDetailsUtil.isLogined()) {  
//	            httpResponse.sendRedirect(httpRequest.getContextPath() + SecurityConstants.LOGIN_URL);  
//	            logger.info("未登陆用户，From IP:" + SecutiryRequestUtil.getRequestIp(httpRequest) + "访问 ：URI" + url);  
//	            return;  
//	        }  
//	          
//	        //  1.2）过用户白名单：如果为超级管理员，则直接执行  
//	        if(SecurityUserTrustListHolder.isTrustUser(SessionUserDetailsUtil.getLoginUserName())) {  
//	            chain.doFilter(request, response);  
//	            return;  
//	        }  
//	          
//	        //  1.3）过资源(URL)白名单：如果为公共页面，直接执行  
//	        if(SecurityMetadataSourceTrustListHolder.isTrustSecurityMetadataSource(url)){  
//	            chain.doFilter(request, response);  
//	            return;  
//	        }  
//	          
	        FilterInvocation fi = new FilterInvocation(request, response, chain);  
	        invoke(fi);  
	    }  
	  
	    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {  
	        return this.securityMetadataSource;  
	    }  
	  
	    public Class<? extends Object> getSecureObjectClass() {  
	        return FilterInvocation.class;  
	    }  
	  
	    public void invoke(FilterInvocation fi) throws IOException,  
	            ServletException {  
	        //@2,进行安全验证  
	        InterceptorStatusToken token = null;  
	        try {  
	            token = super.beforeInvocation(fi);  
	        } catch (IllegalArgumentException e) {  
	            HttpServletRequest httpRequest = fi.getRequest();  
	            HttpServletResponse httpResponse = fi.getResponse();  
	            String url = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");  
	            //logger.info("用户 " + SessionUserDetailsUtil.getLoginUserName() + "，From IP:" + SecutiryRequestUtil.getRequestIp(httpRequest) + "。尝试访问未授权(或者) URI:" + url);  
	              
	            httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);  
	            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(httpRequest.getContextPath()+"/login");  
	            dispatcher.forward(httpRequest, httpResponse);  
	            return;  
	        }  
	          
	        try {  
	            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());  
	        } finally {  
	            super.afterInvocation(token, null);  
	        }  
	    }  
	  
	    public SecurityMetadataSource obtainSecurityMetadataSource() {  
	        return this.securityMetadataSource;  
	    }  
	  
	    public void setSecurityMetadataSource(  
	            FilterInvocationSecurityMetadataSource newSource) {  
	        this.securityMetadataSource = newSource;  
	    }  
	  
	    @Override  
	    public void destroy() {  
	    }  
	  
	    @Override  
	    public void init(FilterConfig arg0) throws ServletException {  
	    }  

}

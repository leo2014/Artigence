package com.artigence.smarthome.console.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.console.vo.ArtiSessionVo;
import com.artigence.smarthome.service.arti.ArtiService;
import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;
@Controller
@RequestMapping("user/arti/artiSession")
public class ArtiSessionStatus extends BaseSpringController {
	
	@Resource
	private ArtiService artiService;
	
	@RequestMapping
	public @ResponseBody SearchResult<ArtiSessionVo> search(@RequestBody SearchParam searchParam,HttpSession session){
		SecurityContext securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		//artiVo.setUsername(securityContext.getAuthentication().getName());
		SearchResult<ArtiSessionVo> searchResult=null;//artiService.searchSessions(searchParam,securityContext.getAuthentication().getName());
		//searchResult.setDraw(searchParam.getDraw());
		return searchResult;
	}
}

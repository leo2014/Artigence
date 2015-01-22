package com.artigence.smarthome.console.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.ClientType;
import com.artigence.smarthome.communication.session.SessionManager;
import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.console.vo.AjaxValidResult;
import com.artigence.smarthome.service.arti.ArtiService;
import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;
import com.artigence.smarthome.service.exception.BusinessException;
@Controller
@RequestMapping("user/arti")
public class Artis extends BaseSpringController {
	
	@Resource
	private ArtiService artiService;
	
	@RequestMapping
	public String arti(){
		return "user/artiinfo";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	@ResponseBody
	public SearchResult<ArtiVo> search(@RequestBody SearchParam searchParam,HttpSession session){
		SecurityContext securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		//artiVo.setUsername(securityContext.getAuthentication().getName());
		SearchResult<ArtiVo> searchResult=artiService.search(searchParam,securityContext.getAuthentication().getName());
		for(ArtiVo artiVo : searchResult.getData())
			artiVo.setOnLine(isOnline(artiVo.getId()));		
		searchResult.setDraw(searchParam.getDraw());
		return searchResult;
	}
	
	private boolean isOnline(Long id){
		SessionManager sessionManager = SessionManager.getInstance();
		if(sessionManager.getSessionClient(new CID(ClientType.ARTI,id))!=null)
			return true;
		return false;
	}
	
	@RequestMapping(value="/macAjaxValid")
	@ResponseBody
	public AjaxValidResult macValid(@Valid AjaxValidResult result){
		result.setValid(0);
		if(this.artiService.validMac(result.getValue()))
			result.setValid(1);
		return result;
	}
	
	@RequestMapping(value="/serialAjaxValid")
	@ResponseBody
	public AjaxValidResult serialValid(@Valid AjaxValidResult result){
		result.setValid(0);
		if(this.artiService.validSerialNum(result.getValue()))
			result.setValid(1);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public void delete(@RequestParam("ids") Long[] ids){
		this.artiService.deleteArti(ids);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public AjaxValidResult create(ArtiVo artiVo,HttpSession session){
		SecurityContext securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		artiVo.setUsername(securityContext.getAuthentication().getName());
		AjaxValidResult result = new AjaxValidResult("",1);
		try{
			this.artiService.register(artiVo);
		}catch(BusinessException be){
			result.setValid(0);
		}
		return result;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public void update(ArtiVo artiVo){
		this.artiService.updateArti(artiVo);
	}
	
}

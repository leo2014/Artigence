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
import com.artigence.smarthome.service.arti.NodeService;
import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.arti.dto.NodeVo;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;
import com.artigence.smarthome.service.exception.BusinessException;
@Controller
@RequestMapping("user/node")
public class Nodes extends BaseSpringController {
	
	@Resource
	private NodeService nodeService;
	
	@RequestMapping
	public String arti(){
		return "user/nodeinfo";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	@ResponseBody
	public SearchResult<NodeVo> search(@RequestBody SearchParam searchParam,HttpSession session){
		SecurityContext securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		//artiVo.setUsername(securityContext.getAuthentication().getName());
		SearchResult<NodeVo> searchResult=nodeService.search(searchParam,securityContext.getAuthentication().getName());
	
		searchResult.setDraw(searchParam.getDraw());
		return searchResult;
	}
	
	@RequestMapping(value="/macAjaxValid")
	@ResponseBody
	public AjaxValidResult macValid(@Valid AjaxValidResult result){
		result.setValid(0);
		if(this.nodeService.validMac(result.getValue()))
			result.setValid(1);
		return result;
	}
	
	@RequestMapping(value="/serialAjaxValid")
	@ResponseBody
	public AjaxValidResult serialValid(@Valid AjaxValidResult result){
		result.setValid(0);
		if(this.nodeService.validSerialNum(result.getValue()))
			result.setValid(1);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public void delete(@RequestParam("ids") Long[] ids){
		this.nodeService.markDelete(ids);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public AjaxValidResult create(NodeVo nodeVo,HttpSession session){
		SecurityContext securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		//artiVo.setUsername(securityContext.getAuthentication().getName());
		AjaxValidResult result = new AjaxValidResult("",1);
		try{
			this.nodeService.saveNode(nodeVo);
		}catch(BusinessException be){
			result.setValid(0);
		}
		return result;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public void update(NodeVo nodeVo){
		this.nodeService.updateNode(nodeVo);
	}
	
}

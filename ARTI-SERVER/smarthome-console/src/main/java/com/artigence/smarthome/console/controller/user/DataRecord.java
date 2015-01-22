package com.artigence.smarthome.console.controller.user;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.DecoderException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.service.arti.DataTransferRecordService;
import com.artigence.smarthome.service.arti.dto.DataTransferRecordVo;
import com.artigence.smarthome.service.arti.vo.RecordSis;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;
@Controller
@RequestMapping("user/dataRecord")
public class DataRecord extends BaseSpringController {
	
	@Resource
	private DataTransferRecordService dataTransferRecordService;
	
	@RequestMapping
	public String data(){
		return "user/nodedata";
	}
	
	@RequestMapping("/tongji")
	public String tongji(){
		return "user/tongji";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	@ResponseBody
	public SearchResult<DataTransferRecordVo> search(@RequestBody SearchParam searchParam,HttpSession session) throws DecoderException{
		SecurityContext securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		//artiVo.setUsername(securityContext.getAuthentication().getName());
		SearchResult<DataTransferRecordVo> searchResult=dataTransferRecordService.filter(searchParam,securityContext.getAuthentication().getName());
		
		searchResult.setDraw(searchParam.getDraw());
		return searchResult;
	}
	
	@RequestMapping(value="/recordSis")
	@ResponseBody
	public SearchResult<RecordSis> sis(HttpSession session) throws DecoderException{
		SecurityContext securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		//artiVo.setUsername(securityContext.getAuthentication().getName());
		List<RecordSis> rss=dataTransferRecordService.tongji(securityContext.getAuthentication().getName());
		SearchResult<RecordSis> sr = new SearchResult<RecordSis>();
		sr.setDraw(1);sr.setData(rss);sr.setRecordsTotal(rss==null?0:rss.size());sr.setRecordsFiltered(rss==null?0:rss.size());
		return sr;
	}
	
}

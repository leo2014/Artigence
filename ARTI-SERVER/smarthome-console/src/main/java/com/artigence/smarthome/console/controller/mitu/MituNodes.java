package com.artigence.smarthome.console.controller.mitu;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.service.mitu.MituNodeService;
import com.artigence.smarthome.service.mitu.dto.PageSearch;
import com.artigence.smarthome.service.mitu.dto.Report;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/mitu/nodes")
public class MituNodes extends BaseSpringController {

	@Resource
	private MituNodeService mituNodeService;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public String getNodes(String transceiverId,int currentPage,int rowsPerPage){
		if(transceiverId==null || currentPage == 0 || rowsPerPage==0)
			return  "{\"currentPage\":1"
					+",\"rowsPerPage\":"+rowsPerPage
					+",\"totalPages\":0"
					+",\"switchIds\":["
					+"]}";
		PageSearch search = new PageSearch(currentPage,0,rowsPerPage);
		return this.mituNodeService.getNodes(transceiverId,search);
	}
	
	@RequestMapping(value="/put",method=RequestMethod.GET)
	@ResponseBody
	public String putNodes(String transceiverId,String switches) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = mapper.readTree(switches);
			List<Report> reports = new ArrayList<Report>();
			for(JsonNode node:rootNode.path("switches")){
				Report data = new Report();
				data.setId(node.path("switchId").textValue());
				data.setStatus(node.path("status").textValue());
				reports.add(data);
			}
			if(switches!=null)this.mituNodeService.saveNodes(reports);
		} catch (Exception e) {
			return "{\"code\":\"300\",\"msg\":\"failed\"}";
		}
		
		return "{\"code\":\"200\",\"msg\":\"ok\"}";
/*		Report report1 = mapper.readValue(report, Report.class);
		this.mituNodeService.saveNodes(report1.getId(), report1.getStatus());
		return (report1.getId()+":"+report1.getStatus());*/
	}
	
	@RequestMapping(value="/getend",method=RequestMethod.GET)
	@ResponseBody
	public String endNodes(String transceiverId,String switches){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = mapper.readTree(switches);
			List<String> reports = new ArrayList<String>();
			for(JsonNode node:rootNode.path("switches")){
				reports.add(node.path("switchId").textValue());
			}
			this.mituNodeService.endReg(reports);
		} catch (Exception e) {
			return "{\"code\":\"300\",\"msg\":\"failed\"}";
		}

		return "{\"code\":\"200\",\"msg\":\"ok\"}";
/*		Report report1 = mapper.readValue(report, Report.class);
		this.mituNodeService.saveNodes(report1.getId(), report1.getStatus());
		return (report1.getId()+":"+report1.getStatus());*/
	}
}

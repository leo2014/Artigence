package com.artigence.smarthome.console.controller;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.service.demon.DemonService;
import com.artigence.smarthome.service.demon.dto.DemonVo;
@RestController
@RequestMapping(value="/index")
public class DemonController extends BaseSpringController{
	@Resource
	private DemonService demonService;

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(Model model){
		System.out.println("request is success");
		List<DemonVo> demonVos = this.demonService.getAllDemonVos();
		ModelAndView mav = new ModelAndView();
        mav.addObject("demonVos", demonVos);
        mav.setViewName("demons");
        return mav;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void deleteAll(HttpServletRequest request,@PathVariable Long id){
		System.out.println("request method:"+request.getMethod());
		if(id<0)
			this.demonService.deleteDemon(null);
		else
			this.demonService.deleteDemon(id);
		System.out.println("request method:"+request.getMethod());
	}
	
	public void setDemonService(DemonService demonService) {
		this.demonService = demonService;
	}
}

package com.artigence.smarthome.console.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.service.security.AuthorityService;
import com.artigence.smarthome.service.security.ResourceService;
import com.artigence.smarthome.service.security.RoleService;
import com.artigence.smarthome.service.security.dto.AuthorityVo;
import com.artigence.smarthome.service.security.dto.ResourceVo;
import com.artigence.smarthome.service.user.UserService;
import com.artigence.smarthome.service.user.dto.RoleVo;
@Controller
@RequestMapping("/admin/access")
public class AccessController extends BaseSpringController {

	@Resource
	private UserService userService;
	@Resource
	private AuthorityService authorityService;
	@Resource
	private RoleService roleService;
	@Resource
	private ResourceService resourceService;
	@RequestMapping
	public ModelAndView access(){
		ModelAndView mav = new ModelAndView("admin/roles");
		return mav;
	}
	
	@RequestMapping("/roles")
	public ModelAndView roles(){
		ModelAndView mav = new ModelAndView("admin/roles");
		List<RoleVo> roleVos = roleService.getRoles();
		mav.addObject("roleVos",roleVos);
		mav.addObject("authorityVos",roleVos.isEmpty()?null:roleVos.get(0).getAuthorities());
		return mav;
	}
	
	@RequestMapping("/authorities")
	public ModelAndView authorities(){
		ModelAndView mav = new ModelAndView("admin/authorities");
		List<AuthorityVo> authorityVos = authorityService.getAuthorities();
		mav.addObject("authorityVos",authorityVos);
		mav.addObject("resources",authorityVos.isEmpty()?null:authorityVos.get(0).getResources());
		return mav;
	}
	
	@RequestMapping("/resorces")
	public ModelAndView resorces(){
		ModelAndView mav = new ModelAndView("admin/resources");
		List<ResourceVo> resourceVos = resourceService.getResourceVos();
		mav.addObject("resourceVos",resourceVos);
		
		return mav;
	}
}

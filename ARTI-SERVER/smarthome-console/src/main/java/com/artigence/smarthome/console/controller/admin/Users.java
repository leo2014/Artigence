package com.artigence.smarthome.console.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.artigence.smarthome.console.base.BaseSpringController;
import com.artigence.smarthome.console.vo.AjaxValidResult;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;
import com.artigence.smarthome.service.exception.BusinessException;
import com.artigence.smarthome.service.user.LoginAndRegisterService;
import com.artigence.smarthome.service.user.UserService;
import com.artigence.smarthome.service.user.dto.UserVo;
@Controller
@RequestMapping("/admin/users")
public class Users extends BaseSpringController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView users(){
		ModelAndView mav = new ModelAndView("/admin/users");
		List<UserVo> userVos = userService.getUserVos(1, 100);
		mav.addObject("userVos", userVos);
		return mav;
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public @ResponseBody SearchResult<UserVo> search(@RequestBody SearchParam searchParam){
		SearchResult<UserVo> searchResult=userService.search(searchParam);
		searchResult.setDraw(searchParam.getDraw());
		return searchResult;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody AjaxValidResult create(UserVo userVo){
		AjaxValidResult result = new AjaxValidResult("",1);
		try{
			//System.out.println(userVo.getUsername());
			userService.saveUser(userVo);		
		}catch(BusinessException be){
			result.setValid(0);
			result.setMessage(be.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/id",method=RequestMethod.DELETE)
	public void delete(@PathVariable long id){
		userService.deleteUser(id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public void delete(@RequestParam("ids") Long[] ids){
		userService.deleteUser(ids);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public void update(UserVo userVo){
		userService.updateUser(userVo);
	}
	
}

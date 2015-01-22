package com.artigence.smarthome.console.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.artigence.smarthome.console.base.BaseSpringController;
@Controller
@RequestMapping("user/status")
public class ArtiNodeStatus extends BaseSpringController{

	@RequestMapping
	public String stat(){
		return "user/artistatus";
	}
}

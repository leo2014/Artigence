package com.artigence.smarthome.service.demon.dto;

import com.artigence.smarthome.persist.model.Demon;

public class DemonVo extends Demon {

	public String getStatusString(){
		return this.getStatus()==true?"开":"关";
	}
}

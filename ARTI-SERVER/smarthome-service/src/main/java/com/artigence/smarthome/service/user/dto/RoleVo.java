package com.artigence.smarthome.service.user.dto;

import java.util.List;

import com.artigence.smarthome.persist.model.security.Role;

public class RoleVo extends Role {

	private static final long serialVersionUID = 1L;
	private List<Integer> authorityIds;
	
	public String getIsEnableString(){
		return this.isEnable()==true?"启用":"禁用";
	}

	public List<Integer> getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(List<Integer> authorityIds) {
		this.authorityIds = authorityIds;
	}
	
}

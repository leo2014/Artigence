package com.artigence.smarthome.service.imp.security;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.artigence.smarthome.persist.dao.security.RoleDao;
import com.artigence.smarthome.persist.model.security.Authority;
import com.artigence.smarthome.persist.model.security.Role;
import com.artigence.smarthome.service.security.RoleService;
import com.artigence.smarthome.service.user.dto.RoleVo;
import com.artigence.smarthome.service.util.DtoBeanUtils;
@Service
public class RoleServiceImp implements RoleService {

	@Resource
	private RoleDao roleDao;
	
	@Transactional
	public List<RoleVo> getRoles() {
		List<Role> roles = this.roleDao.findAll();
		List<RoleVo> roleVos = DtoBeanUtils.copyProperties(roles, RoleVo.class,"users","authorities");
		if(!roles.isEmpty() && !roles.get(0).getAuthorities().isEmpty()){
			List<Authority> authorities = DtoBeanUtils.copyProperties(roles.get(0).getAuthorities(), Authority.class, "roles","resources");
			roleVos.get(0).setAuthorities(authorities);
		}
		return roleVos;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

}

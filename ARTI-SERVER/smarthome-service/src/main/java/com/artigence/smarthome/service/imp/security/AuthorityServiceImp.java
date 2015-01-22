package com.artigence.smarthome.service.imp.security;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.artigence.smarthome.persist.dao.security.AuthorityDao;
import com.artigence.smarthome.persist.model.security.Authority;
import com.artigence.smarthome.persist.model.security.Resource;
import com.artigence.smarthome.service.security.AuthorityService;
import com.artigence.smarthome.service.security.dto.AuthorityVo;
import com.artigence.smarthome.service.util.DtoBeanUtils;
@Service
public class AuthorityServiceImp implements AuthorityService{

	@javax.annotation.Resource
	private AuthorityDao authorityDao;
	
	@Transactional
	public List<AuthorityVo> getAuthorities() {
		List<Authority> authorities = authorityDao.findAll();
		List<AuthorityVo> authorityVos = DtoBeanUtils.copyProperties(authorities, AuthorityVo.class,"roles","resources");
		if((!authorityVos.isEmpty()) && (!authorities.get(0).getResources().isEmpty())){
			List<Resource> resources = DtoBeanUtils.copyProperties(authorities.get(0).getResources(),Resource.class,"authorities");
			authorityVos.get(0).setResources(resources);
		}
		return authorityVos;
	}

	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

}

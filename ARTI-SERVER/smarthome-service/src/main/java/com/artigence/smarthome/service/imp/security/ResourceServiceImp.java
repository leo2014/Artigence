package com.artigence.smarthome.service.imp.security;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.artigence.smarthome.persist.dao.security.ResourceDao;
import com.artigence.smarthome.service.security.ResourceService;
import com.artigence.smarthome.service.security.dto.ResourceVo;
import com.artigence.smarthome.service.util.DtoBeanUtils;
@Service
public class ResourceServiceImp implements ResourceService {

	@Resource
	private ResourceDao resourceDao;
	@Transactional
	public List<ResourceVo> getResourceVos() {
		List<com.artigence.smarthome.persist.model.security.Resource> resources = this.resourceDao.findAll();
		List<ResourceVo> resourceVos = DtoBeanUtils.copyProperties(resources, 
				com.artigence.smarthome.persist.model.security.Resource.class, 
				"authorities");
		return resourceVos;
	}
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

}

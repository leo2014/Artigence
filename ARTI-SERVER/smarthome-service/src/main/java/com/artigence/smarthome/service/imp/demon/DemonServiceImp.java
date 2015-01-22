package com.artigence.smarthome.service.imp.demon;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.dao.DemonDao;
import com.artigence.smarthome.persist.model.Demon;
import com.artigence.smarthome.service.demon.DemonService;
import com.artigence.smarthome.service.demon.dto.DemonVo;
import com.artigence.smarthome.service.util.DtoBeanUtils;
@Service
public class DemonServiceImp implements DemonService{

	@Resource
	private DemonDao demonDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public PageList<DemonVo> getDemonVos(int page, int pageSize) {
		PageList<Demon> demons = this.demonDao.pageQuery(page, pageSize, null, "");
		
		List demonVos=DtoBeanUtils.copyProperties(demons, DemonVo.class);
		
		return new PageList<DemonVo>(demonVos,page,pageSize,demons.getTotalItems());
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<DemonVo> getAllDemonVos(){
		List<Demon> demons =  this.demonDao.findAll("id",true);		
		List<DemonVo> demonVos = DtoBeanUtils.copyProperties(demons, DemonVo.class);
		return demonVos;
	}

	public void setDemonDao(DemonDao demonDao) {
		this.demonDao = demonDao;
	}
	@Override
	@Transactional
	public void deleteDemon(Long id) {
		if(id==null){
			this.demonDao.deleteAll();
		}else{
			this.demonDao.deleteById(id);
		}
	}

}

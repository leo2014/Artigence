package com.artigence.smarthome.service.demon;

import java.util.List;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.service.demon.dto.DemonVo;

public interface DemonService {
	public PageList<DemonVo> getDemonVos(int page,int pageSize);
	public List<DemonVo> getAllDemonVos();
	
	public void deleteDemon(Long id);
}

package com.artigence.smarthome.service.demon;

import java.util.List;

import javax.annotation.Resource;

import org.eclipse.core.runtime.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.artigence.smarthome.service.demon.dto.DemonVo;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-service.xml")
@Ignore
public class DemonServiceTest {

	@Resource
	private DemonService demonService;
	
	@Test
	public void testGetDemonVos() {
		List<DemonVo> list = this.demonService.getDemonVos(1, 5);
		Assert.isTrue(!list.isEmpty());
		for(DemonVo dv : list)
			System.out.println("DEMON:"+dv.getMac()+" Time:"+dv.getCmdTime());
	}

}

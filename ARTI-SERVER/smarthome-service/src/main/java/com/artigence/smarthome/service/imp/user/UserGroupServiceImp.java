package com.artigence.smarthome.service.imp.user;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.dao.UserGroupDao;
import com.artigence.smarthome.persist.model.Arti;
import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.UserGroup;
import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.user.UserGroupService;
import com.artigence.smarthome.service.user.dto.UserGroupVo;
import com.artigence.smarthome.service.user.dto.UserVo;
import com.artigence.smarthome.service.util.DtoBeanUtils;
import com.artigence.smarthome.service.util.PageUtils;
@Service
public class UserGroupServiceImp implements UserGroupService {

	@Resource
	private UserGroupDao userGroupDao;
	
	@Override
	@Transactional
	public void saveUserGroup(UserGroupVo userGroupVo) {
		Assert.notNull(userGroupVo, "arg userGroupVo is not null");
		UserGroup userGroup = new UserGroup();
		BeanUtils.copyProperties(userGroupVo, userGroup);
		userGroupDao.save(userGroup);
	}

	@Override
	@Transactional
	public void updateUserGroup(UserGroupVo userGroupVo) {
		Assert.notNull(userGroupVo, "userGroupVo is not null");
		UserGroup userGroup = null;
		if(userGroupVo.getId()!=null)
			userGroup = userGroupDao.getById(userGroupVo.getId());
		else if(userGroupVo.getHost()!=null){
			List<UserGroup> userGroups = userGroupDao.findByKey("host", userGroupVo.getHost());
			userGroup = userGroups!=null?userGroups.isEmpty()?null:userGroups.get(0):null;
		}
		Assert.notNull(userGroup, "update userGroup is not null");
		userGroupDao.update(userGroup);
		
	}

	@Override
	@Transactional
	public void deleteUserGroup(UserGroupVo userGroupVo) {
		Assert.notNull(userGroupVo);
		this.userGroupDao.deleteById(userGroupVo.getId());

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public PageList<UserVo> getUsers(Long userGroupId, int page, int pageSize) {
		UserGroup userGroup = this.userGroupDao.getById(userGroupId);
		List<User> users = userGroup.getUsers();
		List userVos = DtoBeanUtils.copyProperties(users, UserVo.class,"loginRecords","roles", "userGroup", "operableNodes");
		
		return PageUtils.getPage(userVos, page, pageSize);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public PageList<UserVo> getUsers(String userGroupName, int page, int pageSize) {
		UserGroup userGroup = this.userGroupDao.getByUniqueField("name", userGroupName);
		List<User> users = userGroup.getUsers();
		List userVos = DtoBeanUtils.copyProperties(users, UserVo.class,"loginRecords","roles", "userGroup", "operableNodes");
		
		return PageUtils.getPage(userVos, page, pageSize);
	}

	@Override
	@Transactional
	public PageList<UserVo> getUsers(UserGroupVo userGroup, int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public PageList<ArtiVo> getArtis(Long userGroupId, int page, int pageSize) {
		UserGroup userGroup = this.userGroupDao.getById(userGroupId);
		List<Arti> artis = userGroup.getArtis();
		List ArtiVos = DtoBeanUtils.copyProperties(artis, ArtiVo.class, "userGroup","nodes");
		
		return PageUtils.getPage(ArtiVos, page, pageSize);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public PageList<ArtiVo> getArtis(String userGroupName, int page, int pageSize) {
		UserGroup userGroup = this.userGroupDao.getByUniqueField("name",userGroupName);
		List<Arti> artis = userGroup.getArtis();
		List ArtiVos = DtoBeanUtils.copyProperties(artis, ArtiVo.class, "userGroup","nodes");
		
		return PageUtils.getPage(ArtiVos, page, pageSize);
	}

	@Override
	public PageList<ArtiVo> getArtis(UserGroupVo userGroup, int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUserGroupDao(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}

}

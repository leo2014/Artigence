package com.artigence.smarthome.service.user;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.user.dto.UserGroupVo;
import com.artigence.smarthome.service.user.dto.UserVo;

/**
 * @date 2014年4月23日 上午10:17:32
 * @author Leipeng
 *	user group manager interface
 */
public interface UserGroupService {
	
	/**
	 * save userGroup
	 * @param userGroupVo
	 */
	public void saveUserGroup(UserGroupVo userGroupVo);

	/**
	 * update UserGroup
	 * @param userGroupVo
	 */
	public void updateUserGroup(UserGroupVo userGroupVo);
	
	/**
	 * delete UserGroup
	 * @param userGroupVo
	 */
	public void deleteUserGroup(UserGroupVo userGroupVo);
	
	/**
	 * get Users by userGroup's Id
	 * @param userGroupId
	 * @return
	 */
	public PageList<UserVo> getUsers(Long userGroupId, int page, int pageSize);
	
	/**
	 * get Users by UserGroup's name
	 * @param userGroupName
	 * @return
	 */
	public PageList<UserVo> getUsers(String userGroupName, int page, int pageSize);
	
	/**
	 * get users by UserGroup
	 * @param userGroup
	 * @return
	 */
	public PageList<UserVo> getUsers(UserGroupVo userGroup, int page, int pageSize);
	
	/**
	 * get Artis by userGroup's id
	 * @param userGroupId
	 * @return
	 */
	public PageList<ArtiVo> getArtis(Long userGroupId, int page, int pageSize);
	
	/**
	 * get Artis by userGroup's name
	 * @param userGroupName
	 * @return
	 */
	public PageList<ArtiVo> getArtis(String userGroupName, int page, int pageSize);
	
	/**
	 * get Artis by userGroup
	 * @param userGroup
	 * @return
	 */
	public PageList<ArtiVo> getArtis(UserGroupVo userGroup, int page, int pageSize);
}

package com.artigence.smarthome.service.user;

import java.util.List;

import javax.mail.MessagingException;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;
import com.artigence.smarthome.service.user.dto.RoleVo;
import com.artigence.smarthome.service.user.dto.UserSearchCriteria;
import com.artigence.smarthome.service.user.dto.UserVo;

/**
 * @date 2014年4月22日 下午3:54:36
 * @author Leipeng
 *	this is user manager interface
 */
public interface UserService {
	/**
	 * save user
	 * @param userVo
	 */
	public void saveUser(UserVo userVo);
	
	/**
	 * update user
	 * @param useVo
	 */
	public void updateUser(UserVo useVo);
	
	/**
	 * delete the user
	 * @param userVo
	 */
	public void deleteUser(UserVo userVo);
	public void deleteUser(Long id);
	public void deleteUser(Long[] ids);
	/**
	 * get user both infomation
	 * @param username
	 * @return
	 */
	public UserVo getUserVo(String username);
	
	/**
	 * search users  
	 * @return
	 */
	public PageList<UserVo> getUserVos(UserSearchCriteria usc, int page, int pageSize);
	
	/**
	 * get all users
	 * @return
	 */
	public PageList<UserVo> getUserVos(int page, int pageSize);
	
	
	/**
	 * find UserVos with role's name
	 * @param rolename
	 * @return
	 */
	public PageList<UserVo> getUserVos(String rolename, int page, int pageSize);
	
	public PageList<User> getUsers(int page, int pageSize);
	
	/**
	 * Determine username whether the only
	 * @prama username
	 * @return boolean
	 */
	public boolean userIsUnique(String username);
	
	/**
	 * Determine email whether the only
	 * @param email
	 * @return
	 */
	public boolean emailIsUnique(String email);
	
	/**
	 * user register system
	 * @param userVo
	 * @return
	 */
	public Long auth(String username, String password);
	
	/**
	 * user email validation
	 * @param securityCode
	 */
	public void sendEmailValid(Integer securityCode,UserVo userVo) throws MessagingException;

	/**
	 * remark user email validated
	 */
	public void emailValid(UserVo userVo);

	public List<RoleVo> getRoles(String username);
	
	public SearchResult<UserVo> search(SearchParam searchParam);


}

package com.artigence.smarthome.service.user;

import java.security.KeyPair;
import java.util.concurrent.ConcurrentHashMap;


import com.artigence.smarthome.service.user.dto.UserVo;

/**
 * @date 2014年4月22日 上午10:48:41
 * @author Leipeng
 * user login and register system interface
 */
public interface LoginAndRegisterService {
	/**
	 * user login with username and password
	 * @param username 
	 * @param password
	 * @return userVo
	 */
	public UserVo login(UserVo userVo);
	
	/**
	 * user register with userVo
	 * @param userVo
	 */
	public void register(UserVo userVo);
	
	/**
	 * judge the username is exist or not
	 * @param username
	 */
	public boolean isExistUsername(String username);
	
	/**
	 * judge the email is registed or not
	 * @param email
	 * @return true or false
	 */
	public boolean isExistEmail(String email);
	
	/**
	 * judge the user validate email or do not
	 * @param username
	 * @return
	 */
	public boolean isValidateEmail(String username);
	
	/**
	 * create RSA keyPair and save it to currentHashMap
	 * @param session
	 * @return
	 */
	public KeyPair createRsaKey(String sessionId);
	
	/**
	 * get RsaKeyPairMap
	 * @return
	 */
	public ConcurrentHashMap<String, KeyPair> getRsaKeyPairMap();
	
}

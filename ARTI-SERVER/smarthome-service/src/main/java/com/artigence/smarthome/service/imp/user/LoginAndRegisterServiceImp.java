package com.artigence.smarthome.service.imp.user;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.artigence.smarthome.persist.dao.UserDao;
import com.artigence.smarthome.persist.dao.UserLoginRecordDao;
import com.artigence.smarthome.persist.dao.security.RoleDao;
import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.UserLoginRecord;
import com.artigence.smarthome.persist.model.security.Role;
import com.artigence.smarthome.service.exception.BusinessException;
import com.artigence.smarthome.service.user.LoginAndRegisterService;
import com.artigence.smarthome.service.user.dto.UserVo;
import com.artigence.smarthome.service.util.RSAUtil;
@Service
@Transactional
public class LoginAndRegisterServiceImp implements LoginAndRegisterService {
	
	@Resource
	private UserDao userDao;
	@Resource
	private UserLoginRecordDao userLoginRecordDao;
	@Resource
	private RoleDao roleDao;

	
	private ConcurrentHashMap<String,KeyPair> RsaKeyPairMap = new ConcurrentHashMap<String,KeyPair>();
	
	
	@Override
	@Transactional
	public UserVo login(UserVo userVo) {
		String username = userVo.getUsername();		
		decodeUserVo(userVo);
		String password = userVo.getPassword();
		if(username!=null  && password!=null){
			User user = userDao.getUserByUsername(username);
			if(user!=null){
				if(password.equals(user.getPassword())){
					BeanUtils.copyProperties(user, userVo, "roles", "userGroup", "operableNodes","loginRecords");
					UserLoginRecord ulr = new UserLoginRecord();
					ulr.setUser(user);
					ulr.setLoginTime(new Date());
					userLoginRecordDao.save(ulr);
				}else
					throw new BusinessException("pwdError","password is error");
			}else{
				throw new BusinessException("userExist","this user is not exist");
			}
		}else{
			throw new BusinessException("nullParam","username or email , password is not null");
		}
		return userVo;
	}

	@Override
	@Transactional
	public void register(UserVo userVo) {
		if(userVo.getUsername()!=null && userVo.getEnpassword()!=null && userVo.getSessionId()!=null){
			decodeUserVo(userVo);
			try{
				User user = new User();
				BeanUtils.copyProperties(userVo, user);
				user.setRegisterDate(new Date());
				Role role = roleDao.getById(1l);
				List<Role> roles = new ArrayList<Role>();
				roles.add(role);
				user.setRoles(roles);
				userDao.save(user);
				
				UserLoginRecord ulr = new UserLoginRecord();
				ulr.setUser(user);
				ulr.setLoginTime(new Date());
				ulr.setClientDescr("first login!");
				userLoginRecordDao.save(ulr);
			}catch(Exception dae){
				throw new BusinessException("emailExist","user's email is registered");
			}
		}else{
			throw new BusinessException("nullParam","user email or password is not null!!");
		}
	}

	@Override
	@Transactional
	public boolean isExistUsername(String username) {
		if(username!=null && userDao.getUserByUsername(username)!=null)
			return true;
		return false;
	}

	@Override
	@Transactional
	public boolean isExistEmail(String email) {
		if(email!=null && userDao.getUserByEmail(email)!=null)
			return true;
		return false;
	}

	@Override
	@Transactional
	public boolean isValidateEmail(String username) {
		if(username!=null){
			User user = userDao.getUserByUsername(username);
			if(user != null)
				return user.getEmailValidation();
		}
		return false;
	}

	

	@Override
	public KeyPair createRsaKey(String sessionId) {
		KeyPair keyPair = this.getRsaKeyPairMap().get(sessionId);
		if(keyPair==null){
			keyPair = RSAUtil.generateKeyPair();
			this.getRsaKeyPairMap().put(sessionId, keyPair);
		}
		//System.out.println("create KeyPairs:sessionId:"+sessionId + "--publickey:"+((RSAPublicKey)(keyPair.getPublic())).getModulus()+""+((RSAPublicKey)(keyPair.getPublic())).getPublicExponent()+"--privatekey:"+keyPair.getPrivate());
		return keyPair;
	}

	private void decodeUserVo(UserVo userVo){
		if(userVo.getSessionId()==null)
			throw new BusinessException("emailExist","session is not null");
		KeyPair keyPair = this.getRsaKeyPairMap().get(userVo.getSessionId());
		if(keyPair==null)
			throw new BusinessException("nullParam","user email or password is not null!!");
		//PrivateKey privateKey = keyPair.getPrivate();
		String password = RSAUtil.decryptStringByJs(userVo.getEnpassword(), keyPair);
		userVo.setPassword(password);
		//System.out.println(password);
	}
	
	public ConcurrentHashMap<String, KeyPair> getRsaKeyPairMap() {
		return RsaKeyPairMap;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserLoginRecordDao(UserLoginRecordDao userLoginRecordDao) {
		this.userLoginRecordDao = userLoginRecordDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}


}

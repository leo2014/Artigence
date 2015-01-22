package com.artigence.smarthome.service.imp.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.dao.UserDao;
import com.artigence.smarthome.persist.dao.UserLoginRecordDao;
import com.artigence.smarthome.persist.dao.security.RoleDao;
import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.UserLoginRecord;
import com.artigence.smarthome.persist.model.security.Authority;
import com.artigence.smarthome.persist.model.security.Role;
import com.artigence.smarthome.service.core.dto.OrderColumns;
import com.artigence.smarthome.service.core.dto.Search;
import com.artigence.smarthome.service.core.dto.SearchColumns;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;
import com.artigence.smarthome.service.email.EmailService;
import com.artigence.smarthome.service.exception.BusinessException;
import com.artigence.smarthome.service.imp.email.MailBean;
import com.artigence.smarthome.service.user.UserService;
import com.artigence.smarthome.service.user.dto.RoleVo;
import com.artigence.smarthome.service.user.dto.UserSearchCriteria;
import com.artigence.smarthome.service.user.dto.UserVo;
import com.artigence.smarthome.service.util.DtoBeanUtils;

@Service
@Transactional
public class UserServiceImp implements UserService {

	@Resource
	private UserDao userDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private EmailService emailService;
	@Resource
	private UserLoginRecordDao userLoginRecordDao;
	
	@Override
	@Transactional
	public void saveUser(UserVo userVo) {
		if(userVo.getUsername()!=null && userVo.getPassword()!=null){
			try{
				User user = new User();
				BeanUtils.copyProperties(userVo, user);
				user.setRegisterDate(new Date());
				Role role = roleDao.getById(1l);
				List<Role> roles = new ArrayList<Role>();
				roles.add(role);
				user.setRoles(roles);
				userDao.save(user);
			}catch(Exception dae){
				throw new BusinessException("emailExist","user's email is registered");
			}
		}else{
			throw new BusinessException("nullParam","user email or password is not null!!");
		}
	}

	@Override
	@Transactional
	public void updateUser(UserVo userVo) {		
		User user = userDao.getById(userVo.getId());
		if(user!=null){
			user.setName(userVo.getName());
			user.setAge(userVo.getAge());
			user.setCellphone(userVo.getCellphone());
			user.setAddress(userVo.getAddress());
			userDao.update(user);
		}
	}

	@Override
	@Transactional
	public void deleteUser(UserVo userVo) {
		if(userVo.getId()!=null)
			userDao.deleteById(userVo.getId());
		else if(userVo.getUsername()!=null && userDao.getUserByUsername(userVo.getUsername())!=null)
			userDao.delete(userDao.getUserByUsername(userVo.getUsername()));
		else if(userVo.getEmail()!=null && userDao.getUserByEmail(userVo.getEmail())!=null)
			userDao.delete(userDao.getUserByEmail(userVo.getEmail()));
	}
	@Transactional
	public void deleteUser(Long id){
		userDao.deleteById(id);
	}
	@Transactional
	public void deleteUser(Long[] ids){
		userDao.batchDelete(ids);
	}
	
	@Override
	@Transactional
	public UserVo getUserVo(String username) {
		if(username!=null){
			User user = userDao.getUserByUsername(username);
			if(user!=null){
				UserVo userVo = new UserVo();
				BeanUtils.copyProperties(user, userVo, "loginRecords", "roles", "userGroup", "operableNodes");
				return userVo;
			}
		}
		return null;
	}

	@Override
	public PageList<UserVo> getUserVos(UserSearchCriteria usc, int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public PageList<UserVo> getUserVos(int page, int pageSize) {
		PageList<User> usersPage = userDao.getUsers(page, pageSize);
		List<UserVo> userVos = new ArrayList<UserVo>();
		for(User user : usersPage){
			UserVo userVo = new UserVo();
			BeanUtils.copyProperties(user, userVo, "loginRecords","roles", "userGroup", "operableNodes");
			userVos.add(userVo);
		}
		return new PageList<UserVo>(userVos, page, pageSize, usersPage.getTotalItems());
	}



	@Override
	public PageList<UserVo> getUserVos(String rolename, int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public PageList<User> getUsers(int page, int pageSize) {
		return userDao.getUsers(page, pageSize);
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public boolean userIsUnique(String username) {
		if(this.userDao.getUserByUsername(username)!=null)
			return false;
		return true;
	}

	@Override
	public boolean emailIsUnique(String email) {
		if(this.userDao.getUserByEmail(email)!=null)
			return false;
		return true;
	}

	@Override
	@Transactional
	public Long auth(String username, String password) {
		User user = null;
		if(username!=null  && password!=null){
			user = userDao.getUserByUsername(username);
			if(user!=null){
				if(password.equals(user.getPassword())){
					return user.getId();
				}else
					return -1L;
			}else{
				return -1L;
			}
		}else{
			return -1L;
		}
	}

	@Override
	@Transactional
	public void sendEmailValid(Integer securityCode,UserVo userVo) throws MessagingException {
		MailBean mailBean = new MailBean();
        mailBean.setFrom("leipeng@artigence.com.cn");  
        mailBean.setSubject("邮箱验证!!");  
        mailBean.setToEmails(new String[]{userVo.getEmail()});  
        mailBean.setTemplate("<p>Hi, ${username}</p><p>欢迎使用 arti系统！以下是你的注册信息：</p>"
        		+ "<p>昵称：${username}</p><p >邮箱：${useremail}</p>"
        		+ "<p >确认使用该邮箱绑定 arti系统，请在arti系统相关页面输入验证码：${emailSecurityCode}。</p>"
        		+ "<p>如果你没有进行过相关操作，那很可能是别人在注册 时误填了你的邮箱，请忽略此邮件。有任何疑问请发邮件至arti@artigence.com.cn，不要直接回复此邮件哦！</p>"
        		+ "<p>谢谢!</p>"
        		+ "<p>昶瑞</p>");  
        Map<String,String> map = new HashMap<String,String>();  
        map.put("username", userVo.getUsername());
        map.put("useremail", userVo.getEmail());
        map.put("emailSecurityCode", securityCode.toString());
        
        mailBean.setData(map);  
		User user = userDao.getUserByUsername(userVo.getUsername());
		user.setEmail(userVo.getEmail());
		try{
			userDao.update(user);
		}catch(DataAccessException dae){
			throw new BusinessException("emailExist");
		}
		emailService.send(mailBean);
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	@Override
	@Transactional
	public void emailValid(UserVo userVo) {
		User user = this.userDao.getUserByUsername(userVo.getUsername());
		user.setEmailValidation(true);
		this.userDao.update(user);
	}

	@Override
	@Transactional
	public List<RoleVo> getRoles(String username) {
		User user = userDao.getUserByUsername(username);
		List<Role> roles = user.getRoles();
		List<RoleVo> roleVos = new ArrayList<RoleVo>();
		for(Role role : roles){
			RoleVo roleVo = new RoleVo();
			roleVo.setId(role.getId());
			roleVo.setName(role.getName());
			roleVo.setDescr(role.getDescr());
			roleVo.setEnable(role.isEnable());
			List<Authority> authorities = new ArrayList<Authority>();
			for(Authority auth : role.getAuthorities()){
				Authority author = new Authority();
				author.setId(auth.getId());
				author.setDescr(auth.getDescr());
				author.setEnable(auth.isEnable());
				authorities.add(author);
			}
			roleVos.add(roleVo);
		}
		return roleVos;
	}

	@Override
	@Transactional
	public SearchResult<UserVo> search(SearchParam searchParam) {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		Search search = searchParam.getSearch();
		if(search!=null && search.getValue()!=null && !search.getValue().trim().isEmpty()){
			dc.add(Restrictions.or(	
					Restrictions.like("username", "%"+search.getValue()+"%"),
					Restrictions.like("name", "%"+search.getValue()+"%"),
					Restrictions.like("email", "%"+search.getValue()+"%"),
					Restrictions.like("address", "%"+search.getValue()+"%")
					
			));
		}
		List<SearchColumns> scs = searchParam.getColumns();
		for(SearchColumns sc : scs){
			if(sc.isSearchable()){
				Search s = sc.getSearch();
				if(s!=null && s.getValue()!=null && !s.getValue().trim().isEmpty()){
					dc.add(Restrictions.like(sc.getName(), "%"+s.getValue()+"%"));
				}
			}
		}
		List<OrderColumns> ocs = searchParam.getOrder();
		Order[] orders = new Order[ocs.size()];
		for(int i=0; i<ocs.size(); i++){
			OrderColumns oc = ocs.get(i);
			String feildName = scs.get(oc.getColumn()).getName();
			if("asc".equals(oc.getDir()) || "ASC".equals(oc.getDir()))
				orders[i]=Order.asc(feildName);
			else
				orders[i]=Order.desc(feildName);
		}
		PageList<User> users = null;
		if(searchParam.getLength() >= 0)
			users = this.userDao.pageQuery(searchParam.getStart(), searchParam.getLength(), dc, orders);
		else
			users = this.userDao.pageQuery(searchParam.getStart(), Integer.MAX_VALUE, dc, orders);
		List<UserVo> userVos = DtoBeanUtils.copyProperties(users, UserVo.class,"loginRecords","roles", "userGroup", "operableNodes");
		Long totalCount = this.userDao.count(User.class);
		SearchResult<UserVo> sr = new SearchResult<UserVo>(totalCount.intValue(),users.getTotalItems(),userVos);
		return sr;
	}

	public void setUserLoginRecordDao(UserLoginRecordDao userLoginRecordDao) {
		this.userLoginRecordDao = userLoginRecordDao;
	}

}

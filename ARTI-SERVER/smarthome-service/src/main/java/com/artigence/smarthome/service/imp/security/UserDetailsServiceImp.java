package com.artigence.smarthome.service.imp.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.artigence.smarthome.persist.dao.UserDao;
import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.security.Authority;
import com.artigence.smarthome.persist.model.security.Role;

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		System.out.println("user grat authrity---------------");

		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		// 取得用户的权 限
		User user = userDao.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("user with the " + username
					+ " is not exist!!");
		}
		for (Role role : user.getRoles()) {
			for (Authority auth : role.getAuthorities()) {
				auths.add(new SimpleGrantedAuthority(auth.getName()));
			}

		}

		String password = null;
		// 取得用户的密码
		password = user.getPassword();

		// List<PubAuthoritiesResources>
		// aaa=pubAuthoritiesResourcesDao.getAll();
		/**
		 * username the username presented to the DaoAuthenticationProvider
		 * password the password that should be presented to the
		 * DaoAuthenticationProvider enabled set to true if the user is enabled
		 * accountNonExpired set to true if the account has not expired
		 * credentialsNonExpired set to true if the credentials have not expired
		 * accountNonLocked set to true if the account is not locked authorities
		 * the authorities that should be granted to the caller if they
		 * presented the correct username and password and the user is enabled.
		 * Not null.
		 */
		org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(
				username, password, true, true, true, true, auths);

		return userDetail;

	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}

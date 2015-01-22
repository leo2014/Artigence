package com.artigence.smarthome.service.security;

import java.util.List;

import com.artigence.smarthome.service.security.dto.AuthorityVo;

public interface AuthorityService {
	List<AuthorityVo> getAuthorities();
}

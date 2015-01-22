package com.artigence.smarthome.service.user.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.UserLoginRecord;

public class UserVo extends User {

	private static long serialVersionUID = 6403834920036003011L;
	private UserLoginRecord userLoginRecord;
	private String registerDateString;
	@NotNull
	private String enpassword;
	private String sessionId;

	public UserLoginRecord getUserLoginRecord() {
		return userLoginRecord;
	}

	public void setUserLoginRecord(UserLoginRecord userLoginRecord) {
		this.userLoginRecord = userLoginRecord;
	}
	
	
    public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getEnpassword() {
		return enpassword;
	}

	public void setEnpassword(String enpassword) {
		this.enpassword = enpassword;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	public String getRegisterDateString() {
		this.registerDateString = DateConvertUtils.format(getRegisterDate(), "yyyy-MM-dd");
		return registerDateString;
	}

	public void setRegisterDateString(String registerDateString) {
		setRegisterDate(DateConvertUtils.parse(registerDateString, "yyyy-MM-dd", Date.class));
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}

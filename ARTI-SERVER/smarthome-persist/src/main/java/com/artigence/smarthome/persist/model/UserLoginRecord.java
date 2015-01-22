package com.artigence.smarthome.persist.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.artigence.smarthome.persist.model.base.BaseEntity;
@Entity@DynamicInsert@DynamicUpdate
@Table(name="user_login_record")
public class UserLoginRecord extends BaseEntity {
	private static final long serialVersionUID = 3769795245101427586L;

	@Id
	@GenericGenerator(name="idGenerator", strategy="native")
	@GeneratedValue(generator="idGenerator")
	private Long id;
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="client_descr")
	private String clientDescr;
	
	@Column(name="login_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginTime;
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	public UserLoginRecord(){};
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClientDescr() {
		return clientDescr;
	}

	public void setClientDescr(String clientDescr) {
		this.clientDescr = clientDescr;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof UserLoginRecord))
			return false;
		
		final UserLoginRecord ulr = (UserLoginRecord)o;
		if(this.id!=null?!id.equals(ulr.getId()):ulr.getId()!=null)
			return false;
		return true;
	}
	
	@Override
	public int hashCode(){
		return this.id.hashCode();
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

package com.artigence.smarthome.persist.model.communication;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.artigence.smarthome.persist.model.communication.code.ClientType;

@Entity(name="session_exception_log")
@DynamicInsert@DynamicUpdate
public class SessionExceptionLog implements Serializable{
	
	private static final long serialVersionUID = 4345985835055540013L;

	@Id
	@GenericGenerator(name="idGenerator",strategy="native")
	@GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="client_type", nullable = false, unique = false)
	private ClientType clientType;
	
	@Column(name="client_id", nullable = true, unique = false)
	private Long clientId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="exception_datetime", nullable = false, unique = false)
	private Date exceptionDateTime;
	
	@Column(name="log_info", nullable = false, unique = false)
	private String logInfo;
	
	@Override
	public int hashCode(){
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof SessionExceptionLog))
			return false;
		SessionExceptionLog newlog = (SessionExceptionLog)o;
		if(this.id==null?true:!this.id.equals(newlog.getId()))
			return false;
		return true;
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Date getExceptionDateTime() {
		return exceptionDateTime;
	}

	public void setExceptionDateTime(Date exceptionDateTime) {
		this.exceptionDateTime = exceptionDateTime;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	
	
}

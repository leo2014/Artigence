package com.artigence.smarthome.persist.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import com.artigence.smarthome.persist.model.base.BaseEntity;
@Entity
@Table(name="demon")
public class Demon extends BaseEntity {
	
	private static final long serialVersionUID = -8716502486309886553L;
	@Id
	@GenericGenerator(name="idGenerator", strategy="native")
	@GeneratedValue(generator="idGenerator")
	private Long id;
	
	@Column(name="mac",length=12)
	private String mac;
	
	@Column(name="status",length=1)
	private boolean status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="cmd_time")
	private Date cmdTime;
	
	public Demon(){}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Demon))
			return false;
		final Demon demon = (Demon)o;
		if(this.id!=null?!id.equals(demon.getId()):demon.getId()==null)
			return false;
		return true;
	}

	public int hashCode(){
		return this.id.hashCode();
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getCmdTime() {
		return cmdTime;
	}

	public void setCmdTime(Date cmdTime) {
		this.cmdTime = cmdTime;
	}
	
	
}

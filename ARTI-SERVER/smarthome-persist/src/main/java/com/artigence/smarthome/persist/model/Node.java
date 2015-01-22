package com.artigence.smarthome.persist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.artigence.smarthome.persist.model.base.BaseEntity;
@Entity@DynamicInsert@DynamicUpdate
@Table(name="node")
public class Node extends BaseEntity {
	private static final long serialVersionUID = -1900622520251855313L;

	@Id
    @GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="mac",length=6)
	private String mac;
	
	@Column(name="serial_no")
	private String serialNum;
	
	@Column(name="status")
	private boolean status;
	
	@Column(name="is_arti_auth")
	private boolean isArtiAuth;
	
	@Column(name="is_delete")
	private boolean isDelete;
	
	@Column(name="descr")
	private String descr;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="arti_id")
	private Arti arti;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Arti getArti() {
		return arti;
	}

	public void setArti(Arti arti) {
		this.arti = arti;
	}
	
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isArtiAuth() {
		return isArtiAuth;
	}

	public void setArtiAuth(boolean isArtiAuth) {
		this.isArtiAuth = isArtiAuth;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof Node))
			return false;
		final Node n = (Node)o;
		if(id!=null?!id.equals(n.getId()):n.getId()!=null)
			return false;
		return true;
	}
	
	@Override
	public int hashCode(){
		return this.id.hashCode();
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}

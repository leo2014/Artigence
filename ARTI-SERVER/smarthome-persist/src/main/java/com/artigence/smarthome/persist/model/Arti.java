package com.artigence.smarthome.persist.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity@DynamicInsert@DynamicUpdate
@Table(name = "arti")
public class Arti implements Serializable {

	private static final long serialVersionUID = 4049012940414742642L;

	@Id
    @GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Column(name="name", nullable = false, length = 64, unique = false)
	private String name;

	@Column(name = "app_id", nullable = false, length = 32,unique = true)
	private String appId;

	@Column(name="mac")
	private String mac;
	
	@Column(name="serial_no")
	private String serialNum;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_group_id")
	private UserGroup userGroup;
	
	@OneToMany(mappedBy="arti",fetch=FetchType.LAZY)
	private List<Node> nodes;
	
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

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof Arti))
			return false;
		
		final Arti obj = (Arti)o;
		if(id!=null?obj.getId().longValue()!=this.id.longValue():obj.getId()!=null)
			return false;
		return true;
	}
	@Override
	public int hashCode(){
		return  this.id.hashCode();
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

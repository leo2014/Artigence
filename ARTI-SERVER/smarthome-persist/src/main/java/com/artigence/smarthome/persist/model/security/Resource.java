package com.artigence.smarthome.persist.model.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.artigence.smarthome.persist.model.security.code.ResourceType;
@Entity@DynamicInsert@DynamicUpdate
@Table(name="resources")
public class Resource implements Serializable {
	private static final long serialVersionUID = 2108637809175386678L;

	@Id
    @GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="type")
	@Enumerated(EnumType.ORDINAL)
	private ResourceType type;
	
	@Column(name="url")
	private String url;
	
	@Column(name="descr")
	private String descr;
	
	@Column(name="enable")
	private boolean enable;
	
	@ManyToMany(mappedBy="resources")
	private List<Authority> authorities;
	
	public Resource(){
	}

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



	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof Resource))
			return false;
		final Resource obj = (Resource)o;
		if(id!=null?id.equals(obj.id):true)
			return false;
		return true;
	}
	
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

package com.artigence.smarthome.persist.model.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
@Entity@DynamicInsert@DynamicUpdate
@Table(name="authorities")
public class Authority implements Serializable {
	private static final long serialVersionUID = 2575967897716782989L;

	@Id
    @GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="descr")
	private String descr;
	
	@Column(name="enable")
	private boolean enable;
	
	@ManyToMany(mappedBy="authorities")
	private List<Role> roles;
	
	@ManyToMany(targetEntity=Resource.class, cascade=CascadeType.ALL)
	@JoinTable(
			name="authority_resource",
			joinColumns={@JoinColumn(name="authority_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="resource_id", referencedColumnName="id")}
			)
	private List<Resource> resources;
	
	public Authority(){}

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
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof Authority))
			return false;
		final Authority authority = (Authority)o;
		if(id!=null?id.equals(authority.getId()):
			true)
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

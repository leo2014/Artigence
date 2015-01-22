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

import com.artigence.smarthome.persist.model.User;
@Entity@DynamicInsert@DynamicUpdate
@Table(name="role")
public class Role implements Serializable {
	private static final long serialVersionUID = -3372693918471544582L;
	
	@Id
    @GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="descr")
	private String descr;
	
	@Column(name="is_enable")
	private boolean isEnable;
	
	@ManyToMany(mappedBy="roles")
	private List<User> users;
	
	@ManyToMany(targetEntity=Authority.class, cascade=CascadeType.ALL)
	@JoinTable(
			name="role_authority",
			joinColumns={@JoinColumn(name="role_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="authority_id", referencedColumnName="id")}
			)
	private List<Authority> authorities;
	
	public Role(){}
		
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
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof Role))
			return false;
		final Role role = (Role)o;
		if(this.name!=null?!this.name.equals(role.getName())
				:this.name!=null)
			return false;
		return true;
	}
	
	@Override
	public int hashCode(){
		return this.id.hashCode()+this.name.hashCode();
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}

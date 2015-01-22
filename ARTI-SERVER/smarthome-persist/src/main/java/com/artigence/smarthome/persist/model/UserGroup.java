package com.artigence.smarthome.persist.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.artigence.smarthome.persist.model.base.BaseEntity;
@Entity@DynamicInsert@DynamicUpdate
@Table(name="user_group")
public class UserGroup extends BaseEntity {
	private static final long serialVersionUID = -3854887210768760720L;
	
	@Id
    @GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Column(name="name",unique=true,nullable=false)
	private String name;

	@OneToOne
	@JoinColumn(name="host", nullable=false)
	private User host;
	
	@OneToMany(mappedBy="userGroup",fetch=FetchType.LAZY)
	private List<User> users;
	
	@OneToMany(mappedBy="userGroup",fetch=FetchType.LAZY)
	private List<Arti> artis;

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

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Arti> getArtis() {
		return artis;
	}

	public void setArtis(List<Arti> artis) {
		this.artis = artis;
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof UserGroup))
			return false;
		
		final UserGroup ug = (UserGroup)o;
		if(id!=null?!id.equals(ug.getId()):ug.getId()!=null)
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

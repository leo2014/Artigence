package com.artigence.smarthome.persist.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.artigence.smarthome.persist.model.base.BaseEntity;
@Entity@DynamicUpdate@DynamicInsert
@Table(name="security_answer")
public class SecurityAnswer extends BaseEntity {

	private static final long serialVersionUID = 3768526672771620656L;

	public SecurityAnswer(){}
	
	@Id
	@GenericGenerator(name="idGenerator",strategy="native")
	@GeneratedValue(generator="idGenerator")
	private Long id;
	
	@ManyToOne(targetEntity=SecurityQuestion.class,optional=false,fetch=FetchType.EAGER)
	@JoinColumn(name="security_question_id",nullable=false)
	private SecurityQuestion securityQuestion;

	@ManyToOne(cascade=CascadeType.PERSIST,optional=false,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Column(name="answer",nullable=false,length=200)
	private String answer;
	
	@Override
	public int hashCode(){
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof SecurityAnswer))
			return false;
		SecurityAnswer sa = (SecurityAnswer)o;
		if(answer!=null?!answer.equals(sa.answer):sa.answer!=null)
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

	public SecurityQuestion getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(SecurityQuestion securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}

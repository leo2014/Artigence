package com.artigence.smarthome.persist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.artigence.smarthome.persist.model.base.BaseEntity;
@Entity@DynamicInsert@DynamicUpdate
@Table(name="security_question")
public class SecurityQuestion extends BaseEntity {
	private static final long serialVersionUID = 754126383981383809L;
	
	public SecurityQuestion(){}
	
	@Id
	@GenericGenerator(name="idGenerator",strategy="native")
	@GeneratedValue(generator="idGenerator")
	private Long id;
	
	@Column(name="question",length=200,nullable=false)
	private String question;
	
	@Override
	public int hashCode(){
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof SecurityQuestion))
			return false;
		SecurityQuestion sq = (SecurityQuestion)o;
		if(question!=null?!question.equals(sq.question):sq.question!=null)
			return false;
		return true;
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}

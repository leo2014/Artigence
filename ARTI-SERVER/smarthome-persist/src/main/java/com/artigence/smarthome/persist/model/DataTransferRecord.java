package com.artigence.smarthome.persist.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.artigence.smarthome.persist.model.code.DataTransferDirection;
import com.artigence.smarthome.persist.model.code.DataType;

@Entity@DynamicInsert@DynamicUpdate
@Table(name = "data_transfer_record")
public class DataTransferRecord implements Serializable {

	private static final long serialVersionUID = -7654370562036769611L;

	@Id
    @GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Column(name="cmd", nullable = false, length = 10, unique = false)
	private byte[] cmd;
	
	@Column(name="transfer_date",nullable=false, updatable=false)
	private Date transferDate;
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_id",nullable=true)
	private User user;
	
	@ManyToOne(targetEntity=Arti.class)
	@JoinColumn(name="arti_id",nullable=true)
	private Arti gateway;

	@Enumerated(EnumType.ORDINAL)
	@Column(name="transfer_direction")
	private DataTransferDirection dataTransferDirection;
	
	@Enumerated(EnumType.STRING)
	@Column(name="data_type")
	private DataType dataType;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getCmd() {
		return cmd;
	}

	public void setCmd(byte[] cmd) {
		this.cmd = cmd;
	}


	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Arti getGateway() {
		return gateway;
	}

	public void setGateway(Arti gateway) {
		this.gateway = gateway;
	}

	public DataTransferDirection getDataTransferDirection() {
		return dataTransferDirection;
	}

	public void setDataTransferDirection(DataTransferDirection dataTransferDirection) {
		this.dataTransferDirection = dataTransferDirection;
	}


	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof DataTransferRecord))
			return false;
		final DataTransferRecord obj = (DataTransferRecord)o;
		if(id != null?id != obj.getId():obj.id!=null)
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

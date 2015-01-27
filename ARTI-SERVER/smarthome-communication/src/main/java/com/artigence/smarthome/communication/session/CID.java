package com.artigence.smarthome.communication.session;

public class CID {
	private ClientType clientType;
	private Long clientId;
	
	public CID(){}

	public CID(Long clientId){
		this.clientId = clientId;
	}
	public CID(ClientType clientType, Long clientId){
		this.clientId = clientId;
		this.clientType = clientType;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
	@Override
	public String toString(){
		return this.clientType + ":" + this.clientId;
	}
	@Override
	public int hashCode(){
		return this.clientType.hashCode()+this.clientId.hashCode();
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof CID))
			return false;
		CID n = (CID)o;
		if(this.clientType == null || this.clientId == null)
			return false;
		if(this.clientType != n.getClientType() || this.clientId != n.getClientId())
			return false;
		return true;
	}
}

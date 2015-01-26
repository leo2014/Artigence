package com.artigence.smarthome.communication.session;

public class CID {
	private ClientType clientType;
	private String clientId;
	
	public CID(){}

	public CID(String clientId){
		this.clientId = clientId;
	}
	public CID(ClientType clientType, String clientId){
		this.clientId = clientId;//APP_ID
		this.clientType = clientType;
	}

	public static CID getServerId(){
		return new CID(ClientType.SERVER,"00000000000000000000000000000000");
	}
	public static CID getDefalutId(){
		return new CID(ClientType.UNKNOWN,"00000000000000000000000000000001");
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@Override
	public String toString(){
		return this.clientId;
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

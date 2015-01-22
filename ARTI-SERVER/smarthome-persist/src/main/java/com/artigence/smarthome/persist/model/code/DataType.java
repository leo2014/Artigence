package com.artigence.smarthome.persist.model.code;

public enum DataType {
	ARTI_AUTH(0x0000),
	USER_AUTH(0x0001),
	KEY(0x0002),
	PLAIN_REPLY(0x0010),
	AUTH_REPLY(0x0011),
	CMD(0x1000),
	HEARTBEAT(0xFFFF),
	GET_NEW_NODE(0x1001),
	UPDATE_NODE(0x1002),
	DELETE_NODE(0x1003);
	
	private int nCode;
	
	private DataType(int nCode){
		this.nCode = nCode;
	}

	public static DataType getDataType(int nCode){
		switch(nCode){
		case 0x0000:
			return DataType.ARTI_AUTH;
		case 0x0001:
			return DataType.USER_AUTH;
		case 0x0002:
			return DataType.KEY;
		case 0x0010:
			return DataType.PLAIN_REPLY;
		case 0x0011:
			return DataType.AUTH_REPLY;
		case 0x1000:
			return DataType.CMD;
		case 0xFFFF:
			return DataType.HEARTBEAT;
		case 0x1001:
			return DataType.GET_NEW_NODE;
		case 0x1002:
			return DataType.UPDATE_NODE;
		case 0x1003:
			return DataType.DELETE_NODE;
		default:
			return DataType.HEARTBEAT;
		}
		
	}
	
	public int nCode(){
		return this.nCode;
	}
}

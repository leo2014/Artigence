package com.artigence.smarthome.communication.filter.secret;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

import com.artigence.smarthome.communication.codec.arithmetic.TEA;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.persist.model.code.DataType;

public class SecretFilter extends IoFilterAdapter {
	private static final Log log = LogFactory.getLog(SecretFilter.class);
	@Override
    public void messageReceived(NextFilter nextFilter, IoSession session,
            Object message) throws Exception {
		ArtiProtocol artiProtocol = (ArtiProtocol)message;
		byte[] data = artiProtocol.getData();
		
		//data = decryptData(data,session);	
		int nCode = (int)(((data[0] & 0xff )<< 8) | (data[1]& 0xff));
		
		byte[] dataValue = Arrays.copyOfRange(data, 2, data.length);
		DataType dataType = DataType.getDataType(nCode);
		artiProtocol.setDataType(dataType);
		artiProtocol.setData(dataValue);
		artiProtocol.setLength(dataValue.length);
		//log.info("session-"+session.getId()+" 接收解密后的数据类型:"+artiProtocol.getDataType());
		nextFilter.messageReceived(session, message);
	}
	
	@Override
	 public void filterWrite(NextFilter nextFilter, IoSession session,
	            WriteRequest writeRequest) throws Exception {
		//System.out.println("secret filter -----------------------------3");
		ArtiProtocol artiProtocol = (ArtiProtocol)writeRequest.getMessage();
		short dataType = (short)artiProtocol.getDataType().nCode();
		byte[] dt = new byte[]{(byte)(dataType >> 8),(byte)(dataType)};
		byte[] data = null;
		if(artiProtocol.getData()!=null)
			data = ArrayUtils.addAll(dt, artiProtocol.getData());
		else 
			data = dt;
		//System.out.println("发送加密前data:"+Hex.encodeHexString(data));
		//data = encryptData(data,session);
		//System.out.println("发送加密后data:"+Hex.encodeHexString(data));
		artiProtocol.setData(data);
		artiProtocol.setLength(data.length);
		//log.info("session-"+session.getId()+" 发送加密后的数据包对象:"+artiProtocol);
		nextFilter.filterWrite(session, writeRequest);
	}
	
	/**
	 * TEA解密数据
	 * @param data
	 * @param session
	 * @return
	 */
	private byte[] decryptData(byte[] data,IoSession session){
		if(data != null && data.length > 0){
			boolean isSafeAuth = (Boolean)session.getAttribute("isSafeAuth");
			int[] key = null;int rounds = 32;
			if(isSafeAuth)
				rounds = 16;
			key = (int[])session.getAttribute("key");
			return TEA.processDecrypt(data, 0, key, rounds);
		}else{
			return data;
		}
		
	}
	
	private byte[] encryptData(byte[] data,IoSession session){
		if(data != null && data.length > 0){
			boolean isSafeAuth = (Boolean)session.getAttribute("isSafeAuth");
			int[] key = null;int rounds = 32;
			if(isSafeAuth)
				rounds = 16;
			key = (int[])session.getAttribute("key");

			return TEA.processEncrypt(data, 0, key, rounds);
		}else{
			return data;
		}
		
	}
	
}

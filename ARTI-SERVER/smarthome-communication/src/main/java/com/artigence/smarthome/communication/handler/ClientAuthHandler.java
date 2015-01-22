package com.artigence.smarthome.communication.handler;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import com.artigence.smarthome.communication.codec.arithmetic.TEA;
import com.artigence.smarthome.communication.core.DataHandler;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.Destination;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.ClientType;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.communication.session.SessionManager;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.service.arti.ArtiService;
import com.artigence.smarthome.service.user.UserService;
 
@Component("clientAuthHandler")
public class ClientAuthHandler extends DataValidationHandler implements DataHandler {
	private static final Log log = LogFactory.getLog(ClientAuthHandler.class);

			
	@Resource
	private ArtiService artiService;
	@Resource
	private UserService userService;
	@Override
	@Transactional
	public void doProcess(ArtiProtocol artiProtocol) {
		log.info("arti is authoritying");
		ArtiProtocol reply = null;
		byte[] data = artiProtocol.getData();
		//data = Arrays.copyOfRange(data, 0, artiProtocol.getLength());
		System.out.println("auth data:"+Hex.encodeHexString(data));
		Long id = -1L;
		CID client = new CID();
		switch(artiProtocol.getDataType()){
		case ARTI_AUTH:			
			String mac = Hex.encodeHexString(data);
			id = artiService.login(mac);
			client.setClientType(ClientType.ARTI);
			break;
		case USER_AUTH:
			String[] userdata = new String(data).split("&");
			if(userdata.length == 2){
				String username = userdata[0];String password = userdata[1];
				id = userService.auth(username, password);
				client.setClientType(ClientType.USER);
			}
			break;
		default:
			log.warn("unknow this dataPackage type in the ClientAuthHandler!");
		}
		if(id < 0){
			reply = getAuthResult(false,artiProtocol);
			ioSession.write(reply);

			ioSession.close(false);
		}else{
			client.setClientId(id);
			SessionManager sessionManager = SessionManager.getInstance();
			SessionClient session = sessionManager.getSessionClient(client);
			if(session == null){
				reply = getAuthResult(true,artiProtocol);
				ioSession.write(reply);
				
				
				ioSession.setAttribute("isAuth", true);
				session = sessionManager.createSessionClient(ioSession, client);
				//createSafeSession(ioSession,artiProtocol);
			} else{
				reply = getAuthResult(false,artiProtocol);
				ioSession.write(reply);
				
				ioSession.close(false);
			}		
		}
	}
	
	private ArtiProtocol getAuthResult(boolean auth,ArtiProtocol ap){
		ArtiProtocol artiProtocol = null;
		byte[] data = null;
		if(auth)
			data=new byte[]{0x00};
		else
			data=new byte[]{0x01};
		if(ap.getDataType() == DataType.ARTI_AUTH)
			artiProtocol = new ArtiProtocol(Destination.ARTI,DataType.AUTH_REPLY,data,1);	
		else
			artiProtocol = new ArtiProtocol(Destination.USER,DataType.AUTH_REPLY,data,1);	
		
		return artiProtocol;
	}

	private void createSafeSession(IoSession session,ArtiProtocol ap){
		try {
			//生成随机TEA 128位密钥
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			keygen.init(128);
			SecretKey key = keygen.generateKey();
			byte[] k = key.getEncoded();
			int[] key16 = TEA.byteToInt(k, 0);
			session.setAttribute("KRY", key16);
			//发送动态密钥和校验数据
			String validData=Long.toHexString(new Random().nextLong()).substring(0, 14);	
			session.setAttribute("validData", validData);
			byte[] data=ArrayUtils.addAll(k,validData.getBytes());
			ArtiProtocol artiProtocol = null;
			if(ap.getDataType() == DataType.ARTI_AUTH)
				artiProtocol = new ArtiProtocol(Destination.ARTI,DataType.KEY,data,32);	
			else
				artiProtocol = new ArtiProtocol(Destination.USER,DataType.KEY,data,32);	
			session.write(artiProtocol);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setArtiService(ArtiService artiService) {
		this.artiService = artiService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	





}

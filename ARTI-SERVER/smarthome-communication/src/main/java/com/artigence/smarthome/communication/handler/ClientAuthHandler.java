package com.artigence.smarthome.communication.handler;

import com.artigence.smarthome.communication.codec.arithmetic.TEA;
import com.artigence.smarthome.communication.core.DataHandler;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.ClientType;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.communication.session.SessionManager;
import com.artigence.smarthome.persist.model.Arti;
import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.service.arti.ArtiService;
import com.artigence.smarthome.service.user.UserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
 
@Component("clientAuthHandler")
public class ClientAuthHandler extends DataValidationHandler implements DataHandler {
	private static final Log log = LogFactory.getLog(ClientAuthHandler.class);

			
	@Resource
	private ArtiService artiService;
	@Resource
	private UserService userService;
	@Override
	@Transactional
	public void doProcess(ArtiProtocol artiProtocol){
		log.info("arti is authoritying");
		boolean isAuth = false;
		byte[] data = artiProtocol.getData();
		String[] dataStr = null;
		try{
			dataStr = new String(data,"UTF-8").split("&");
		}catch(Exception e){
			e.printStackTrace();
		}
		CID client = CID.getDefalutId();
		User user = null;
		Arti arti = null;
		if(dataStr != null && dataStr.length == 2){
			System.out.println("auth data:"+dataStr[0]);

			String appId = null;
			String cid = dataStr[0];
			String appKey = dataStr[1];

			switch(artiProtocol.getDataType()){
			case ARTI_AUTH:
				arti = artiService.login(cid,appKey);
				if(arti!=null)appId = arti.getAppId();
				client.setClientType(ClientType.ARTI);
				break;
			case USER_AUTH:
				user = userService.auth(cid, appKey);
				if(user!=null)appId = user.getAppId();
				client.setClientType(ClientType.USER);
				break;
			default:
				log.warn("unknow this dataPackage type in the ClientAuthHandler!");
			}

			if(appId!=null) {
				client.setClientId(appId);
				SessionManager sessionManager = SessionManager.getInstance();
				SessionClient session = sessionManager.getSessionClient(client);
				if (session == null) {
					ioSession.setAttribute("isAuth", true);
					sessionManager.createSessionClient(ioSession, client);
					isAuth = true;
				}
			}
		}

		ArtiProtocol reply = getAuthResult(isAuth,client);
		ioSession.write(reply);
		if(!isAuth)ioSession.close(false);
	}
	
	private ArtiProtocol getAuthResult(boolean auth,CID destination){
		ArtiProtocol artiProtocol = null;
		byte[] data = null;
		if(auth)
			data=new byte[]{0x00};
		else
			data=new byte[]{0x01};

		artiProtocol = new ArtiProtocol(CID.getServerId(),destination,DataType.AUTH_REPLY,data,3);
		
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
				artiProtocol = new ArtiProtocol(null,null,DataType.KEY,data,32);
			else
				artiProtocol = new ArtiProtocol(null,null,DataType.KEY,data,32);
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

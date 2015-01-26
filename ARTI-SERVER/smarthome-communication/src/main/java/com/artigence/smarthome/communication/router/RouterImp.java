package com.artigence.smarthome.communication.router;

import com.artigence.smarthome.communication.core.DataHandler;
import com.artigence.smarthome.communication.handler.ClientAuthHandler;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.persist.model.code.DataType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component
public class RouterImp implements Router {
	

	private static final Log log = LogFactory.getLog(ClientAuthHandler.class);
	
	@Resource(name="clientAuthHandler")
	private DataHandler clientAuthHandler;
	@Resource(name="plainDataHandler")
	private DataHandler plainDataHandler; 
	@Resource(name="getNewNodes")
	private DataHandler getNewNodes;
	@Resource(name="updateNodeStatus")
	private DataHandler updateNodeStatus;
	@Resource(name="deleteNode")
	private DataHandler deleteNode;
//	@Resource(name="createSafeAuthHandler")
//	private DataHandler createSafeAuthHandler;

	@Override
	public void router(IoSession session, Object message) {
		boolean isAuth = (Boolean)session.getAttribute("isAuth");
//		boolean isSafeAuth = (Boolean)session.getAttribute("isSafeAuth");
		ArtiProtocol artiProtocol = (ArtiProtocol)message;
		if(!isAuth){
			if(artiProtocol.getDataType() == DataType.ARTI_AUTH){
				clientAuthHandler.setIoSession(session);
				clientAuthHandler.process(message);
			}
//		}else if(!isSafeAuth){
//			createSafeAuthHandler.setIoSession(session);
//			createSafeAuthHandler.process(message);
//		}else if(isSafeAuth){
		}else{
			switch(artiProtocol.getDataType()){
			case ARTI_AUTH:
				session.write(getAuthResult(true,CID.getDefalutId()));
				break;
			case CMD:
				plainDataHandler.setIoSession(session);
				plainDataHandler.process(message);
				break;
			case GET_NEW_NODE:
				getNewNodes.setIoSession(session);
				getNewNodes.process(message);
				break;
			case UPDATE_NODE:
				updateNodeStatus.setIoSession(session);
				updateNodeStatus.process(message);
				break;
			case DELETE_NODE:
				deleteNode.setIoSession(session);
				deleteNode.process(message);
				break;
			default:
				log.info("无此数据类型处理器");
				break;
			}
		}	
//		}else{
//			log.info("还没建立安全数据传输通道，无法处理和接收数据");
//		}

	}



	public void setClientAuthHandler(DataHandler clientAuthHandler) {
		this.clientAuthHandler = clientAuthHandler;
	}



	public void setPlainDataHandler(DataHandler plainDataHandler) {
		this.plainDataHandler = plainDataHandler;
	}



	public void setGetNewNodes(DataHandler getNewNodes) {
		this.getNewNodes = getNewNodes;
	}



	public void setUpdateNodeStatus(DataHandler updateNodeStatus) {
		this.updateNodeStatus = updateNodeStatus;
	}



	public void setDeleteNode(DataHandler deleteNode) {
		this.deleteNode = deleteNode;
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



}

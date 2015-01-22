package com.artigence.smarthome.communication.handler.arti;


import javax.annotation.Resource;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.artigence.smarthome.communication.core.DataHandler;
import com.artigence.smarthome.communication.handler.DataValidationHandler;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.Destination;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.service.arti.ArtiService;
import com.artigence.smarthome.service.arti.NodeService;
import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.arti.dto.NodeVo;
@Component("getNewNodes")
public class GetNewNodes extends DataValidationHandler implements DataHandler {
	private static final Log log = LogFactory.getLog(GetNewNodes.class);
	@Resource
	private ArtiService artiService;
	@Resource
	private NodeService nodeService;
	
	@Override
	public void doProcess(ArtiProtocol artiProtocol) {
		switch((((artiProtocol.getData()[0] & 0xff )<< 8) | (artiProtocol.getData()[1]& 0xff))){
		case 0x0000:
			ioSession.write(getNewNode());
			break;
		default:
			updateNode(artiProtocol);
		}

	}
	private ArtiProtocol getNewNode(){
		ArtiProtocol artiProtocol = null;
		byte[] data=new byte[]{0x00,0x00};
		SessionClient session = (SessionClient)ioSession.getAttribute("sessionClient");
		ArtiVo artiVo = artiService.getArti(session.getClient().getClientId());
		for(NodeVo nodeVo:artiVo.getNodeVos()){
			
			if(!nodeVo.isArtiAuth()){
				try {
					data = Hex.decodeHex(nodeVo.getSerialNum().toCharArray());
				} catch (DecoderException e) {
					e.printStackTrace();
					data = new byte[]{0x00,0x00};
				} 
				break;

			}	
		}
		artiProtocol = new ArtiProtocol(Destination.ARTI,DataType.GET_NEW_NODE,data,2);
		return artiProtocol;
	}
	private void updateNode(ArtiProtocol ap){
		SessionClient session = (SessionClient)ioSession.getAttribute("sessionClient");
		ArtiVo artiVo = artiService.getArti(session.getClient().getClientId());
		for(NodeVo nodeVo:artiVo.getNodeVos()){
			if(nodeVo.getSerialNum().equals(Hex.encodeHexString(ap.getData()))){
				nodeVo.setArtiAuth(true);
				log.info(Hex.encodeHexString(ap.getData()));
				nodeService.updateNode(nodeVo);
			}
		}
		
	}
	public void setArtiService(ArtiService artiService) {
		this.artiService = artiService;
	}
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}
	
}

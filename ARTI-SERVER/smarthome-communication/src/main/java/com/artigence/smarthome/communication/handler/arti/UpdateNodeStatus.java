package com.artigence.smarthome.communication.handler.arti;

import com.artigence.smarthome.communication.handler.DataValidationHandler;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.ArtiProtocolFactory;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.service.arti.ArtiService;
import com.artigence.smarthome.service.arti.NodeService;
import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.arti.dto.NodeVo;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component("updateNodeStatus")
public class UpdateNodeStatus extends DataValidationHandler {
	private static final Log log = LogFactory.getLog(UpdateNodeStatus.class);
	@Resource
	private ArtiService artiService;
	@Resource
	private NodeService nodeService;
	
	@Override
	public void doProcess(ArtiProtocol artiProtocol) {
		log.info("====================updateNode start==================================");
		updateNode(artiProtocol);
		ioSession.write(ArtiProtocolFactory.artiProtocolInstance(DataType.PLAIN_REPLY,new byte[]{0x00},null));
		log.info("====================updateNode start==================================");
	}
	private void updateNode(ArtiProtocol ap){
		SessionClient session = (SessionClient)ioSession.getAttribute("sessionClient");
		ArtiVo artiVo = artiService.getArti(1l);
		String nodeSerial = Hex.encodeHexString(ArrayUtils.subarray(ap.getData(), 0, 2));
		byte[] statusb = ArrayUtils.subarray(ap.getData(), 2, 4);
		int status = (statusb[0]<<8 | statusb[1]);
		
		for(NodeVo nodeVo:artiVo.getNodeVos()){
			if(nodeVo.getSerialNum().equals(nodeSerial)){
				if(status == 0)
					nodeVo.setStatus(true);
				else
					nodeVo.setStatus(false);
				log.info(nodeSerial);
				nodeService.updateNode(nodeVo);
			}
		}
		
	}
	
	public ArtiService getArtiService() {
		return artiService;
	}
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

}

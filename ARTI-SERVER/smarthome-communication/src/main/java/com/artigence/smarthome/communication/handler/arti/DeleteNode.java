package com.artigence.smarthome.communication.handler.arti;

import javax.annotation.Resource;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import com.artigence.smarthome.communication.core.DataHandler;
import com.artigence.smarthome.communication.handler.DataValidationHandler;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.Destination;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.service.arti.NodeService;
@Component("deleteNode")
public class DeleteNode extends DataValidationHandler implements DataHandler {

	@Resource
	private NodeService nodeService;
	
	@Override
	public void doProcess(ArtiProtocol artiProtocol) {
		byte[] nodeSearilNum = artiProtocol.getData();
		if(artiProtocol.getLength()<2)return;
		switch(nodeSearilNum[0]<<8 | nodeSearilNum[1]){
		case 0x0000:
			ioSession.write(getDeleteNode());
			break;
		default:
			deleteNode(Hex.encodeHexString(nodeSearilNum));
		}
		

	}

	private ArtiProtocol getDeleteNode() {
		ArtiProtocol deleteNode = new ArtiProtocol();
		String serialNum = this.nodeService.getDeleteNode();
		if(serialNum==null)
			deleteNode.setData(new byte[]{0x00,0x00});
		else
			try {
				deleteNode.setData(Hex.decodeHex(serialNum.toCharArray()));
			} catch (DecoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				deleteNode.setData(new byte[]{0x00,0x00});
			}
		deleteNode.setDestination(Destination.ARTI);
		deleteNode.setLength(2);
		deleteNode.setDataType(DataType.DELETE_NODE);
		return deleteNode;
		
	}
	
	private void deleteNode(String nodeSerialNum){
		this.nodeService.deleteNode(nodeSerialNum);
	}
	
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

}

package com.artigence.smarthome.communication.codec.arti;

import com.artigence.smarthome.communication.codec.Encoder;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.springframework.stereotype.Component;
@Component
public class ArtiEncoder implements Encoder {
	private static final Log log = LogFactory.getLog(ArtiEncoder.class);
	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		
		ArtiProtocol artiProtocol = (ArtiProtocol)message;
		log.info("session-"+session.getId()+" 发送未编码码数据类型:"+artiProtocol.getDataType());
		out.write(artiProtocol.toIoBuffer());

	}
	
	
	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

}

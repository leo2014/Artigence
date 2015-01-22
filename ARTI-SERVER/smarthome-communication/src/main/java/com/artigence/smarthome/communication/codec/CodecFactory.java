package com.artigence.smarthome.communication.codec;

import javax.annotation.Resource;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.springframework.stereotype.Component;
@Component("codecFactory")
public class CodecFactory implements ProtocolCodecFactory {

	@Resource
	private Decoder decoder;
	@Resource
	private Encoder encoder;
	
	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	public void setDecoder(Decoder decoder) {
		this.decoder = decoder;
	}

	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
	}

}

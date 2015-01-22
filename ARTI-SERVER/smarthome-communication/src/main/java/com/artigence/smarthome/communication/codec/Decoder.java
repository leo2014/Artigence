package com.artigence.smarthome.communication.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import com.artigence.smarthome.communication.core.BufferContext;

public interface Decoder extends ProtocolDecoder{
	public BufferContext getContext(IoSession session);
}

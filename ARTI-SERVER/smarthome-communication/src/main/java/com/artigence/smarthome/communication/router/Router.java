package com.artigence.smarthome.communication.router;

import org.apache.mina.core.session.IoSession;

public interface Router {

	public void router(IoSession session, Object message);
}

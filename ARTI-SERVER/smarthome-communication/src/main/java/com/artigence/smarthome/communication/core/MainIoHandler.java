package com.artigence.smarthome.communication.core;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.router.Router;
import com.artigence.smarthome.communication.server.CommunicationServer;
import com.artigence.smarthome.communication.session.SessionClient;

/**
 * This class is to create new sessions, destroy sessions and deliver
 * received data 
 * @author Leipeng
 *
 */
@Component
public class MainIoHandler implements IoHandler {

    private static final Log log = LogFactory.getLog(MainIoHandler.class);
    private static final String IS_AUTH = "isAuth";
//    private static final String IS_SAFE_AUTH = "isSafeAuth";
//    private static final String KRY = "key";
//    private static final int[] key32 = new int[] {
//        0x759f5845, 
//        0xf48ba5a3, 
//        0x81243faa, 
//        0x458facd5
//        };
	private static final String WRITE_QUEUE = "writeQueue";
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		log.debug("session remote address that's " +session.getRemoteAddress()+" is created");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		session.setAttribute(IS_AUTH, false);
//		session.setAttribute(IS_SAFE_AUTH, false);
//		session.setAttribute(KRY, key32);
		session.setAttribute(WRITE_QUEUE, new ConcurrentLinkedQueue<ArtiProtocol>());
		log.info("session-"+session.getId()+" remote address that's " +session.getRemoteAddress()+" is openned");
		log.warn("session-"+session.getId()+" remote address that's " +session.getRemoteAddress()+" is openned");
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("session-"+session.getId()+" remote address that's " +session.getRemoteAddress()+" is closed");
		log.warn("session-"+session.getId()+" remote address that's " +session.getRemoteAddress()+" is closed");
		SessionClient sessionClient = (SessionClient)session.getAttribute("sessionClient");
		if(sessionClient != null)sessionClient.colse(false);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		log.info("session-"+session.getId()+" remote address that's " +session.getRemoteAddress()+" is Idle");
		log.warn("session-"+session.getId()+" remote address that's " +session.getRemoteAddress()+" is Idle");
		SessionClient sessionClient = (SessionClient)session.getAttribute("sessionClient");
		if(sessionClient != null)sessionClient.colse(false);
		else session.close(false);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		log.info("session-"+session.getId()+" remote address that's " +session.getRemoteAddress()+" throws a exception");
		log.warn("session-"+session.getId()+" remote address that's " +session.getRemoteAddress()+" throws a exception");
		log.debug("Excwption: "+cause.getMessage());
		if(cause!=null){
			for(StackTraceElement st:cause.getStackTrace())
				log.info(st.toString());
			cause.printStackTrace();
			
		}
		SessionClient sessionClient = (SessionClient)session.getAttribute("sessionClient");
		if(sessionClient != null)sessionClient.colse(false);
		else session.close(false);
	}
	

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug("session remote address that's " +session.getRemoteAddress()+" received messages");
		log.debug("receives message :"+message);
		
		Router router = (Router)CommunicationServer.getBean("routerImp");
		router.router(session, message);

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		log.debug("session remote address that's " +session.getRemoteAddress()+" send messages");
		log.debug("send message :"+message);
	}

}

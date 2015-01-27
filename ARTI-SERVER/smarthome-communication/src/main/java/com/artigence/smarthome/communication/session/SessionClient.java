package com.artigence.smarthome.communication.session;

import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * 
 * @author ripon
 *
 */
public class SessionClient {
	
	private static final String CLIENT_SESSION = "sessionClient";
	
	private IoSession session;
	private Long id;
	private CID client;
	private ConcurrentLinkedQueue<ArtiProtocol> writeQueue = null;
	
	
	public SessionClient(IoSession session){
		this.session = session;
		session.setAttribute(CLIENT_SESSION, this);
		id = this.session.getId();
		 writeQueue = (ConcurrentLinkedQueue<ArtiProtocol>)session.getAttribute("writeQueue");
	}

	public WriteFuture write(ArtiProtocol artiProtocol){
		writeQueue.offer(artiProtocol);
		return session.write(artiProtocol);
	}
	
	public ReadFuture read(){
		return session.read();
	}
	
	public void colse(boolean isFlush){
		SessionManager.getInstance().removeSessionClient(this);
		if(session!=null&&session.isConnected())
			session.close(isFlush);
	}
	
	public void destory(){
		
	}
	
	
	public String toString(){
		return "sessionClientId:"+id+" Client:"+this.client;
	}
	
	public int hashCode(){
		return HashCodeBuilder.reflectionHashCode(client);
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof SessionClient))
			return false;
		SessionClient session = (SessionClient)o;
		if(!this.client.toString().equals(session.getClient().toString()))
				return false;
		return true;
	}
	
	
	
	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CID getClient() {
		return client;
	}

	public void setClient(CID client) {
		this.client = client;
	}

	public ConcurrentLinkedQueue<ArtiProtocol> getWriteQueue() {
		return writeQueue;
	}


	
}






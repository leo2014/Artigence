package com.artigence.smarthome.communication.session;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

public class SessionManager {

	private static final Log log = LogFactory.getLog(SessionManager.class);
	
	private static SessionManager instance;
	
	private Map<String, SessionClient> clientSessions = new ConcurrentHashMap<String, SessionClient>();
	
	public static SessionManager getInstance(){
		if(instance == null){
			synchronized (SessionManager.class){
				instance = new SessionManager();
			}
		}
		return instance;
	}
	
	public SessionClient createSessionClient(IoSession ioSession, CID client){
		SessionClient sessionClient = null;
		sessionClient = clientSessions.get(client);
		if(sessionClient == null)
			sessionClient=new SessionClient(ioSession);
		sessionClient.setClient(client);
		clientSessions.put(client.toString(), sessionClient);
		log.debug("SessionClient is created");
		return sessionClient;
	}
	
	public void addSessionClient(SessionClient sessionClient){
		clientSessions.put(sessionClient.getClient().toString(), sessionClient);
	}
	
	
	public SessionClient getSessionClient(CID client){
		return clientSessions.get(client.toString());
	}
	
	public Collection<SessionClient> getSessionClients(){
		return clientSessions.values();
	}
	
	public boolean removeSessionClient(SessionClient sessionClient){
		if(sessionClient == null)
			return false;
		CID client = sessionClient.getClient();
		clientSessions.remove(client.toString());
		return true;
	}
	
	public void closeAllSessionClient(){
		Set<SessionClient> sessions = new HashSet<SessionClient>();
		sessions.addAll(clientSessions.values());
		for(SessionClient session : sessions){
			session.getSession().close(false);
		}
	}
}

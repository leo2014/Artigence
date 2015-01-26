package com.artigence.smarthome.communication.filter.valid;

import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.persist.model.code.DataType;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CrcValidFilter extends IoFilterAdapter {
	private ConcurrentLinkedQueue<ArtiProtocol> writeQueue = null;
	private static int reSendCount;
	
	@Override
    public void messageReceived(NextFilter nextFilter, IoSession session,
            Object message) throws Exception {
		
		writeQueue = (ConcurrentLinkedQueue<ArtiProtocol>)session.getAttribute("writeQueue");
		ArtiProtocol artiProtocol = (ArtiProtocol)message;
//		CRC16 crc16 = new CRC16();
//		crc16.update(artiProtocol.getDestination().ordinal());
//		crc16.update(artiProtocol.getLength()+8);
//		CheckSum check = new CheckSum();
//		long newheadCrc = check.check(Arrays.copyOfRange(artiProtocol.getProtocolData(), 0, 4));
//		//System.out.println("newheadCrc:"+newheadCrc);
//		//if(artiProtocol.getHeadCrc() == newheadCrc){
//			byte[] bodydata = Arrays.copyOfRange(artiProtocol.getProtocolData(), 6, 6+artiProtocol.getData().length);
//			long newbodyCrc = check.check(bodydata);
//			//System.out.println("newbodyCrc:"+newbodyCrc);
//			if(artiProtocol.getBodyCrc() == newbodyCrc){
//				if(processValid(artiProtocol,session))
//					return;
				nextFilter.messageReceived(session, message);
//				//ioSession.write(getValidResult(true));
//			}else{
//				//response bad result
//				session.write(getValidResult(false));
//			}
//		}else{
//			//response bad result
//			session.write(getValidResult(false));
//		}
		
		
    }
	
	
	@Override
	 public void filterWrite(NextFilter nextFilter, IoSession session,
	            WriteRequest writeRequest) throws Exception {
		//System.out.println("crcvalid filter -----------------------------2");
//		writeQueue = (ConcurrentLinkedQueue<ArtiProtocol>)session.getAttribute("writeQueue");
//		ArtiProtocol artiProtocol = (ArtiProtocol)writeRequest.getMessage();
//		CheckSum check = new CheckSum();
//
//		byte[] headcrc = new byte[]{(byte)(des >>> 8),(byte)des,(byte)(length >>> 8),(byte)length};
//		artiProtocol.setHeadCrc(check.check(headcrc));
//
//		byte[] bodycrc = artiProtocol.getData();
//
//		artiProtocol.setBodyCrc(check.check(bodycrc));
		//if(writeQueue!=null)writeQueue.offer(artiProtocol);
		
	    nextFilter.filterWrite(session, writeRequest);
	 }

	
	
	private boolean processValid(ArtiProtocol artiProtocol,IoSession session){
		ArtiProtocol preArtiProtocol = writeQueue.poll();
		if(preArtiProtocol!=null && artiProtocol.getDataType() == DataType.PLAIN_REPLY
				&& artiProtocol.getData()[0] > 0x00){
			if(reSendCount > 1){
				reSendCount = 0;
				//ioSession.close(false);
				return true;
			}
			session.write(preArtiProtocol);
			writeQueue.offer(preArtiProtocol);
			reSendCount++;
			return true;
		}else{
			if(writeQueue.size()>1)
				writeQueue.clear();
			reSendCount=0;
		}
		return false;
	}
}

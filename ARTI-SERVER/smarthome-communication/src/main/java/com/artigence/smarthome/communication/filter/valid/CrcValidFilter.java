package com.artigence.smarthome.communication.filter.valid;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

import com.artigence.smarthome.communication.codec.arithmetic.CRC16;
import com.artigence.smarthome.communication.codec.arithmetic.CheckSum;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.Destination;
import com.artigence.smarthome.persist.model.code.DataType;

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
		writeQueue = (ConcurrentLinkedQueue<ArtiProtocol>)session.getAttribute("writeQueue");
		ArtiProtocol artiProtocol = (ArtiProtocol)writeRequest.getMessage();
		CheckSum check = new CheckSum();
		int des = artiProtocol.getDestination().ordinal();
		int length = artiProtocol.getLength()+8;
		byte[] headcrc = new byte[]{(byte)(des >>> 8),(byte)des,(byte)(length >>> 8),(byte)length};
		artiProtocol.setHeadCrc(check.check(headcrc));
		
		byte[] bodycrc = artiProtocol.getData();
		
		artiProtocol.setBodyCrc(check.check(bodycrc));
		//if(writeQueue!=null)writeQueue.offer(artiProtocol);
		
	    nextFilter.filterWrite(session, writeRequest);
	 }
	/**
	 * 获取校验结果
	 * @param valid 校验成功true或失败false
	 * @return
	 */
	private ArtiProtocol getValidResult(boolean valid){
		ArtiProtocol validResult = new ArtiProtocol();
		CheckSum check = new CheckSum();
		validResult.setDestination(Destination.ARTI);
		validResult.setLength(1);
		validResult.setDataType(DataType.PLAIN_REPLY);
		if(valid)
			validResult.setData(new byte[]{0x00});
		else
			validResult.setData(new byte[]{0x01});
		int des = validResult.getDestination().ordinal();
		int length = validResult.getLength()+8;
		byte[] headcrc = new byte[]{(byte)des,(byte)(des >>> 8),(byte)length,(byte)(length >>> 8)};
		validResult.setHeadCrc(check.check(headcrc));
		int ncode = validResult.getDataType().nCode();
		byte[] bodycrc = new byte[]{(byte)ncode,(byte)(ncode >>> 8)};
		
		if(validResult.getData()!=null)
			bodycrc = ArrayUtils.addAll(bodycrc, validResult.getData());
		validResult.setBodyCrc(check.check(bodycrc));
		return validResult;
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

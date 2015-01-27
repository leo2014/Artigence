package com.artigence.smarthome.communication.codec.arti;

import com.artigence.smarthome.communication.codec.Decoder;
import com.artigence.smarthome.communication.codec.arithmetic.CheckSum;
import com.artigence.smarthome.communication.core.BufferContext;
import com.artigence.smarthome.communication.protocol.ArtiProtocol;
import com.artigence.smarthome.communication.protocol.ArtiProtocolFactory;
import com.artigence.smarthome.communication.protocol.DestinationType;
import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.communication.session.SessionClient;
import com.artigence.smarthome.persist.model.code.DataType;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.springframework.stereotype.Component;


/**
 * 
 * @author ripon 协议： 字段 大小 destination 2 length 2 crc 2 data >2 crc 2
 */
@Component
public class ArtiDecoder extends CumulativeProtocolDecoder implements Decoder {
	private static final Log log = LogFactory.getLog(ArtiDecoder.class);
	private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	private final CheckSum check = new CheckSum();
	private int maxPackLength = 2000;



	public BufferContext getContext(IoSession session) {
		BufferContext ctx;
		ctx = (BufferContext) session.getAttribute(CONTEXT);
		if (ctx == null) {
			ctx = new BufferContext();
			session.setAttribute(CONTEXT, ctx);
		}
		return ctx;
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		BufferContext ctx = (BufferContext) session.getAttribute(CONTEXT);
		if (ctx != null) {
			session.removeAttribute(CONTEXT);
		}
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		return decoderProtocol(session, in, out);
	}

	private boolean decoderProtocol(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {

		//获取客户端
		CID client = null;
		SessionClient sessionClient = (SessionClient)session.getAttribute("sessionClient");
		if(sessionClient!=null)
			client = sessionClient.getClient();

		final int packHeadLength = ArtiProtocol.PROTOCOL_HEAD_LENGTH;
		// 先获取上次的处理上下文，其中可能有未处理完的数据
		BufferContext ctx = getContext(session);
		// 先把当前buffer中的数据追加到Context的buffer当中
		log.info("session-"+session.getId()+
				"limit[" + in.limit() + "]:"+Hex.encodeHexString(in.array()).substring(in.position()*2, in.limit()*2));
		ctx.append(in);
		// 把position指向0位置，把limit指向原来的position位置
		IoBuffer buf = ctx.getBuffer();
		buf.flip();
		// 然后按数据包的协议进行读取
		while (buf.remaining() >= packHeadLength) {
			buf.mark();
			// 读取消息头部分
			int length = buf.remaining();
			log.info("session-"+session.getId()+" 接收未解码数据长度[" + length + "] "+
					"limit[" + buf.limit() + "]:" + Hex.encodeHexString(buf.array()).substring(buf.position()*2, buf.limit()*2));
			// 检查读取是否正常，不正常的话清空buffer
			if (length < 0 || length > maxPackLength) {
				System.out.println("长度[" + length
						+ "] > maxPackLength or <0....");
				buf.clear();
				break;
			}
			// 读取正常的消息，并写入输出流中，以便IoHandler进行处理
			else if (length >= packHeadLength) {

				//header bytes
				byte[] headerbytes = new byte[packHeadLength-2];
				buf.get(headerbytes);
				buf.reset();

				//destinationType | destination
				DestinationType destT = buf.getEnumShort(DestinationType.class);
				Long destination = Long.valueOf(buf.getShort());

				//length | header crc
				int bodyLength = buf.getChar();
				int headCrc = buf.getChar();

				//checksum header
				if(!this.checkHeader(headerbytes, headCrc)){
					log.info("protocol header checkSum error");
					session.write(ArtiProtocolFactory.artiProtocolInstance(DataType.PLAIN_REPLY,new byte[]{0x01},client));
					buf.clear();
					buf.limit(buf.position());
					break;
				}

				//body
				if (bodyLength >= 0 && (bodyLength+4) <= buf.remaining()) {
					byte[] bodybytes = null;
					//dataType
					DataType dataType = DataType.getDataType(buf.getShort());

					bodybytes = new byte[]{(byte)(dataType.nCode()>>>8),(byte)dataType.nCode()};
					//data
					byte[] data = new byte[bodyLength];
					buf.get(data);
					int bodyCrc = buf.getChar();

					bodybytes = ArrayUtils.addAll(bodybytes,data);
					if(!this.checkBody(bodybytes, bodyCrc)){
						log.info("protocol body checkSum error");
						session.write(ArtiProtocolFactory.artiProtocolInstance(DataType.PLAIN_REPLY, new byte[]{0x01}, client));
						buf.clear();
						buf.limit(buf.position());
						break;						
					}

					ArtiProtocol artiProtocol = new ArtiProtocol(destT, destination,
							dataType,data, data.length, headCrc, bodyCrc);
					System.out.println("artiProtocol"+artiProtocol);
					out.write(artiProtocol);
				}else if(bodyLength < 4){
					log.info("protocol body length < 4 is error");
					buf.clear();buf.limit(buf.position());
					break;	
				}else {
					// 如果消息包不完整
					// 将指针重新移动消息头的起始位置
					buf.reset();
					break;
				}
			} else {
				// 如果消息包不完整
				// 将指针重新移动消息头的起始位置
				buf.reset();
				break;
			}
		}
		if (buf.hasRemaining()) {
			// 将数据移到buffer的最前面
			IoBuffer temp = IoBuffer.allocate(maxPackLength)
					.setAutoExpand(true);
			temp.put(buf);
			temp.flip();
			buf.clear();
			buf.put(temp);

		} else {// 如果数据已经处理完毕，进行清空
			buf.clear();
		}
		return true;
	}
	
	private boolean checkHeader(byte[] header,long headCrc){
		System.out.println("Hex bytes:" +Hex.encodeHexString(header)+" crc:"+check.check(header) );
		if(check.check(header) != headCrc){
			System.out.println("protocol header checkSum error");
			return false;
		}

		return true;
	}
	
	private boolean checkBody(byte[] body,long bodyCrc){
		if(check.check(body) != bodyCrc) {
			System.out.println("protocol body checkSum error");
			return false;
		}
		return true;
	}


	public int getMaxLineLength() {
		return maxPackLength;
	}

	public void setMaxLineLength(int maxLineLength) {
		if (maxLineLength <= 0) {
			throw new IllegalArgumentException("maxLineLength: "
					+ maxLineLength);
		}
		this.maxPackLength = maxLineLength;
	}
}

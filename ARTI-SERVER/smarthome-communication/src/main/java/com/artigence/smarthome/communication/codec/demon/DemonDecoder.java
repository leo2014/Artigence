package com.artigence.smarthome.communication.codec.demon;

import org.apache.commons.codec.binary.Hex;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.mortbay.log.Log;
import org.springframework.stereotype.Component;

import com.artigence.smarthome.communication.codec.Decoder;
import com.artigence.smarthome.communication.core.BufferContext;

public class DemonDecoder extends CumulativeProtocolDecoder implements Decoder {

	private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");

	private int maxPackLength = 4000;

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
		Log.debug("message is decoding");
		final int packLength = 7;
		// 先获取上次的处理上下文，其中可能有未处理完的数据
		BufferContext ctx = getContext(session);
		// 先把当前buffer中的数据追加到Context的buffer当中
		ctx.append(in);
		// 把position指向0位置，把limit指向原来的position位置
		IoBuffer buf = ctx.getBuffer();
		buf.flip();
		// 然后按数据包的协议进行读取
		if (buf.remaining() >= packLength) {
			buf.mark();
			// 读取消息头部分
			int length = buf.remaining();
			System.out.println("长度[" + length+"]");
			System.out.println("limit[" + buf.limit()+"]");
			// 检查读取是否正常，不正常的话清空buffer
			if (length < 0 || length > maxPackLength) {
				System.out.println("长度[" + length
						+ "] > maxPackLength or <0....");
				buf.clear();
				//break;
			}
			// 读取正常的消息，并写入输出流中，以便IoHandler进行处理
			else if (length >= packLength) {
//				Integer.toHexString(buf.get());
//				String content = buf.getString(ctx.getDecoder());
				byte[] bytes = buf.array();
				byte[] bs = new byte[7];
				for (int i=0;i<7;i++){
					bs[i] = bytes[i];
				}
				System.out.println("bytes[" + bs.toString() +"]");
				String content = Hex.encodeHexString(bs);
				System.out.println("data[" + content+"]");
				out.write(content);
				buf.position(packLength);
			} else {
				// 如果消息包不完整
				// 将指针重新移动消息头的起始位置
				buf.reset();
				//break;
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
}

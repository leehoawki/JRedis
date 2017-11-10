package org.seeking.jredis;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class JRedisCodecEncoder implements ProtocolEncoder {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        Reply reply = (Reply) message;
        String value = reply.getMessage();
        CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
        IoBuffer buf = IoBuffer.allocate(value.length()).setAutoExpand(true);
        buf.putString(value, encoder);
        buf.flip();
        out.write(buf);
    }

    @Override
    public void dispose(IoSession session) throws Exception {

    }
}
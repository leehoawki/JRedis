package org.seeking.jredis;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class JRedisCodecFactory implements ProtocolCodecFactory {
    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return new JRedisCodecDecoder();
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return new JRedisCodecEncoder();
    }
}
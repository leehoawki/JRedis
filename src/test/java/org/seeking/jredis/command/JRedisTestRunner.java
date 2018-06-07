package org.seeking.jredis.command;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.seeking.jredis.JRedisCodecFactory;
import org.seeking.jredis.JRedisHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JRedisTestRunner extends BlockJUnit4ClassRunner {

    static IoAcceptor acceptor = null;

    public JRedisTestRunner(Class<?> klass) throws InitializationError, IOException {
        super(klass);
        if (acceptor == null) {
            acceptor = new NioSocketAcceptor();
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new JRedisCodecFactory()));
            acceptor.setHandler(new JRedisHandler(null));
            acceptor.getSessionConfig().setReadBufferSize(2048);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            acceptor.bind(new InetSocketAddress(9000));
        }
    }
}

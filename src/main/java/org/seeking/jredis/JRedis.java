package org.seeking.jredis;

import org.apache.commons.cli.*;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;

public class JRedis {
    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("h", "help", false, "Print usage information");
        options.addOption("p", "port", true, "Server port (default: 9000)");
        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException ex) {
            printHelp(options);
            return;
        }

        if (commandLine.hasOption("h")) {
            printHelp(options);
            return;
        }

        int port = 9000;
        if (commandLine.hasOption("p")) {
            port = Integer.valueOf(commandLine.getOptionValue("p"));
        }

        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new JRedisCodecFactory()));
        acceptor.setHandler(new JRedisHandler());
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        acceptor.bind(new InetSocketAddress(port));
    }

    static void printHelp(Options options) {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp("Options", options);
    }
}
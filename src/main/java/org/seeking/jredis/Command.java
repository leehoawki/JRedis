package org.seeking.jredis;

import org.apache.mina.core.session.IoSession;

import java.util.List;

public interface Command {
    Reply eval(List<String> params, IoSession ioSession);

    CommandSpec getCommandSpec();
}

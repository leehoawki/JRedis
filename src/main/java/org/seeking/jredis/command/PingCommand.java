package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.StatusReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PingCommand implements Command {

    private CommandSpec commandSpec;

    public PingCommand() {
        this.commandSpec = new CommandSpec(-1, new ArrayList<>(Arrays.asList("stale", "fast")), 0, 0, 0);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        return new StatusReply("PONG");
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

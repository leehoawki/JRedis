package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.StatusReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuitCommand implements Command {

    private CommandSpec commandSpec;

    public QuitCommand() {
        this.commandSpec = new CommandSpec(2, new ArrayList<>(Arrays.asList("write", "fast")), 1, 1, 1);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        ioSession.closeNow();
        return new StatusReply("OK");
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

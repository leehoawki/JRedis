package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.io.SnapShot;
import org.seeking.jredis.reply.StatusReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SaveCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public SaveCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(1, new ArrayList<>(Arrays.asList("admin", "noscript")), 0, 0, 0);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        SnapShot.dump(memory);
        return new StatusReply("OK");
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

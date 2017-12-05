package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.Reply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DelCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public DelCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(-2, new ArrayList<>(Arrays.asList("write")), 1, -1, 1);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        int count = 0;
        for (String key : params) {
            Object value = memory.remove(key);
            if (value != null) count += 1;
        }
        return new IntegerReply(count);
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

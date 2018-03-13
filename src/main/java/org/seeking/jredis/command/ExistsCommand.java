package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.type.Expirable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExistsCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public ExistsCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(-2, new ArrayList<>(Arrays.asList("readonly", "fast")), 1, -1, 1);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        String key = params.get(0);
        Object value = memory.get(key);
        if (value == null) return new IntegerReply(0);

        if (value instanceof Expirable && ((Expirable) value).isExpired()) {
            this.memory.remove(key);
            return new IntegerReply(0);
        }
        return new IntegerReply(1);
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

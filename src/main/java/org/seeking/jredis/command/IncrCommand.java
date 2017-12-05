package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.ErrorReply;
import org.seeking.jredis.reply.IntegerReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class IncrCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public IncrCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(2, new ArrayList<>(Arrays.asList("write", "denyoom", "fast")), 1, 1, 1);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        String key = params.get(0);
        Object value = memory.get(key);
        if (value == null) {
            memory.put(key, 1);
            return new IntegerReply(1);
        }
        try {
            int ret = Integer.parseInt(value.toString()) + 1;
            memory.put(key, ret);
            return new IntegerReply(ret);
        } catch (NumberFormatException ex) {
            return new ErrorReply("WRONGTYPE Operation against a key holding the wrong kind of value");
        }
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

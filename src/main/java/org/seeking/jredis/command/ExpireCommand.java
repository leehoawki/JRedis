package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.ErrorReply;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.type.Expirable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExpireCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public ExpireCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(3, new ArrayList<>(Arrays.asList("write", "fast")), 1, 1, 1);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        String key = params.get(0);
        try {
            int second = Integer.valueOf(params.get(1));
            Expirable value = (Expirable) memory.get(key);
            if (value == null) return new IntegerReply(0);
            value.expire(second);
            return new IntegerReply(1);
        } catch (NumberFormatException ex) {
            return new ErrorReply(ErrorReply.INT_OUT_OF_RANGE);
        }
    }


    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.ErrorReply;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.type.Strings;

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
            memory.put(key, Strings.create("1"));
            return new IntegerReply(1);
        }
        if (value instanceof Strings) {
            Strings sds = (Strings) value;
            if (sds.isExpired()) {
                memory.put(key, Strings.create("1"));
                return new IntegerReply(1);
            }
            try {
                int ret = Integer.parseInt(sds.val()) + 1;
                memory.put(key, Strings.create(String.valueOf(ret)));
                return new IntegerReply(ret);
            } catch (NumberFormatException ex) {
                return new ErrorReply(ErrorReply.INT_OUT_OF_RANGE);
            }
        }
        return new ErrorReply(ErrorReply.WRONG_TYPE);
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

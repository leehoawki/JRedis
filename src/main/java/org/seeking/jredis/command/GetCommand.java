package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.reply.ErrorReply;
import org.seeking.jredis.type.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GetCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public GetCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(2, new ArrayList<>(Arrays.asList("readonly", "fast")), 1, 1, 1);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        String key = params.get(0);
        Object value = memory.get(key);
        if (value == null) return new BulkReply(null);
        if (value instanceof Strings) {
            Strings sds = (Strings) value;
            if (sds.isExpired()) {
                this.memory.remove(key);
                return new BulkReply(null);
            }
            return new BulkReply(((Strings) value).val());
        }
        return new ErrorReply(ErrorReply.WRONG_TYPE);
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

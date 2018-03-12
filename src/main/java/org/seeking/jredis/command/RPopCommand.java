package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.reply.ErrorReply;
import org.seeking.jredis.type.Lists;

import java.util.*;

public class RPopCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public RPopCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(2, new ArrayList<>(Arrays.asList("write", "fast")), 1, 1, 1);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        String key = params.get(0);
        Object list = memory.get(key);
        if (list == null) {
            return new BulkReply(null);
        } else if (list instanceof Lists) {
            Lists l = (Lists) list;
            if (l.isExpired()) {
                this.memory.remove(key);
                return new BulkReply(null);
            }
            return new BulkReply(l.pollLast());
        } else {
            return new ErrorReply(ErrorReply.WRONG_TYPE);
        }
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.ErrorReply;
import org.seeking.jredis.reply.IntegerReply;

import java.util.*;

public class LPushCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public LPushCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(-3, new ArrayList<>(Arrays.asList("write", "denyoom", "fast")), 1, 1, 1);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        String key = params.get(0);
        List<String> value = params.subList(1, params.size());
        Object list = memory.get(key);
        LinkedList<String> l;
        if (list == null) {
            l = new LinkedList<>(value);
            memory.put(key, l);
        } else if (list instanceof LinkedList) {
            l = (LinkedList<String>) list;
            for (String string : value) {
                l.addFirst(string);
            }
        } else {
            return new ErrorReply(ErrorReply.WRONG_TYPE);
        }
        return new IntegerReply(l.size());
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

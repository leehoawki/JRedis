package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.Reply;

import java.util.List;
import java.util.Map;

public class ExistsCommand implements Command {
    private Map<String, Object> memory;

    public ExistsCommand(Map<String, Object> memory) {
        this.memory = memory;
    }

    @Override
    public Reply eval(List<String> params) {
        String key = params.get(0);
        if (memory.containsKey(key)) return new IntegerReply(1);
        return new IntegerReply(0);
    }
}
package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.Reply;

import java.util.List;
import java.util.Map;

public class GetCommand implements Command {
    private Map<String, Object> memory;

    public GetCommand(Map<String, Object> memory) {
        this.memory = memory;
    }

    @Override
    public Reply eval(List<String> params) {
        String key = params.get(0);
        Object value = memory.get(key);
        if (value == null) return new BulkReply(null);
        return new BulkReply(value.toString());
    }
}

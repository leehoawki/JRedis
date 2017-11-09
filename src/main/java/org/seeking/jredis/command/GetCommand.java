package org.seeking.jredis.command;

import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.reply.Reply;

import java.util.List;
import java.util.Map;

public class GetCommand implements Command {
    private Map<String, String> memory;

    public GetCommand(Map<String, String> memory) {
        this.memory = memory;
    }

    @Override
    public Reply eval(List<String> params) {
        String key = params.get(0);
        String value = memory.get(key);
        return new BulkReply(value);
    }
}

package org.seeking.jredis.command;

import org.seeking.jredis.reply.Reply;
import org.seeking.jredis.reply.StatusReply;

import java.util.List;
import java.util.Map;

public class SetCommand implements Command {
    private Map<String, Object> memory;

    public SetCommand(Map<String, Object> memory) {
        this.memory = memory;
    }

    @Override
    public Reply eval(List<String> params) {
        String key = params.get(0);
        String value = params.get(1);
        memory.put(key, value);
        return new StatusReply("OK");
    }
}

package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.Reply;

import java.util.List;
import java.util.Map;

public class DelCommand implements Command {
    private Map<String, Object> memory;

    public DelCommand(Map<String, Object> memory) {
        this.memory = memory;
    }

    @Override
    public Reply eval(List<String> params) {
        int count = 0;
        for (String key : params) {
            Object value = memory.remove(key);
            if (value != null) count += 1;
        }
        return new IntegerReply(count);
    }
}

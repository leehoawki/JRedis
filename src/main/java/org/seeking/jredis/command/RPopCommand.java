package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.Reply;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RPopCommand implements Command {
    private Map<String, Object> memory;

    public RPopCommand(Map<String, Object> memory) {
        this.memory = memory;
    }

    @Override
    public Reply eval(List<String> params) {
        String key = params.get(0);
        LinkedList<String> list = (LinkedList<String>) memory.get(key);
        if (list == null) {
            return new BulkReply(null);
        } else {
            return new BulkReply(list.pollLast());
        }
    }
}
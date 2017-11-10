package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.Reply;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LPushCommand implements Command {
    private Map<String, Object> memory;

    public LPushCommand(Map<String, Object> memory) {
        this.memory = memory;
    }

    @Override
    public Reply eval(List<String> params) {
        String key = params.get(0);
        List<String> value = params.subList(1, params.size());
        LinkedList<String> list = (LinkedList<String>) memory.get(key);
        if (list == null) {
            list = new LinkedList<String>(value);
            memory.put(key, list);
        } else {
            for (String string : value) {
                list.addFirst(string);
            }
        }
        return new IntegerReply(list.size());
    }
}

package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.Reply;

import java.util.*;

public class LPushCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public LPushCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(-3, new ArrayList<>(Arrays.asList("write", "denyoom", "fast")), 1, 1, 1);
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

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

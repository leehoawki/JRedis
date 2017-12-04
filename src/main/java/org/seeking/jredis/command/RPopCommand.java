package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.Reply;

import java.util.*;

public class RPopCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public RPopCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(2, new ArrayList<>(Arrays.asList("write", "fast")), 1, 1, 1);
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

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
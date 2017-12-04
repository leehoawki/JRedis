package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.Reply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GetCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public GetCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(2, new ArrayList<>(Arrays.asList("readonly", "fast")), 1, 1, 1);
    }

    @Override
    public Reply eval(List<String> params) {
        String key = params.get(0);
        Object value = memory.get(key);
        if (value == null) return new BulkReply(null);
        return new BulkReply(value.toString());
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

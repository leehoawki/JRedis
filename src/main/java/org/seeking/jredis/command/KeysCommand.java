package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.reply.MultiBulkReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class KeysCommand implements Command {
    private Map<String, Object> memory;

    private CommandSpec commandSpec;

    public KeysCommand(Map<String, Object> memory) {
        this.memory = memory;
        this.commandSpec = new CommandSpec(2, new ArrayList<>(Arrays.asList("readonly", "sort_for_script")), 0, 0, 0);
    }

    @Override
    public Reply eval(List<String> params) {
        String pattern = params.get(0).replace("*", ".*").replace("?", ".?");
        List<Reply> list = new ArrayList<>();
        for (String key : memory.keySet()) {
            if (Pattern.matches(pattern, key)) {
                list.add(new BulkReply(key));
            }
        }
        return new MultiBulkReply(list);
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

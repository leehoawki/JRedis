package org.seeking.jredis.command;

import org.seeking.jredis.reply.Reply;

import java.util.List;
import java.util.Map;

public class ExistsCommand implements Command {
    private Map<String, Object> memory;

    public ExistsCommand(Map<String, Object> memory) {
        this.memory = memory;
    }

    @Override
    public Reply eval(List<String> params) {
        return null;
    }
}

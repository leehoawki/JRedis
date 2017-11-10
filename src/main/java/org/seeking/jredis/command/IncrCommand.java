package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.Reply;

import java.util.List;
import java.util.Map;

public class IncrCommand implements Command {
    private Map<String, Object> memory;

    public IncrCommand(Map<String, Object> memory) {
        this.memory = memory;
    }

    @Override
    public Reply eval(List<String> params) {
        return null;
    }
}

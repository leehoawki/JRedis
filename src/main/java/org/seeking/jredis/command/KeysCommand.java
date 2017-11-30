package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.Reply;

import java.util.List;
import java.util.Map;

public class KeysCommand implements Command {
    private Map<String, Object> memory;

    public KeysCommand(Map<String, Object> memory) {
        this.memory = memory;
    }

    @Override
    public Reply eval(List<String> params) {
        // TODO
        return null;
    }
}

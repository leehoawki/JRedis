package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.Reply;

import java.util.List;
import java.util.Map;

public class CmdCommand implements Command {

    private Map<String, Command> commands;

    public CmdCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public Reply eval(List<String> params) {
        return null;
    }
}

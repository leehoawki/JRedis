package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;

import java.util.List;

public class AuthCommand implements Command {
    private CommandSpec commandSpec;

    @Override
    public Reply eval(List<String> params) {
        return null;
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

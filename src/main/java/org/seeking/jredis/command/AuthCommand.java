package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.ErrorReply;
import org.seeking.jredis.reply.StatusReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthCommand implements Command {
    private CommandSpec commandSpec;

    private String password;

    public AuthCommand(String password) {
        this.password = password;
        this.commandSpec = new CommandSpec(0, new ArrayList<>(Arrays.asList("loading", "stale")), 0, 0, 0);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        if (this.password == null) return new ErrorReply("ERR Client sent AUTH, but no password is set");
        String password = params.get(0);
        if (password.equals(this.password)) {
            ioSession.setAttribute("authenticated", 1);
            return new StatusReply("OK");
        } else {
            ioSession.removeAttribute("authenticated");
            return new ErrorReply("ERR invalid password");
        }
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

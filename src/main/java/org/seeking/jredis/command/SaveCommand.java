package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.io.RDB;
import org.seeking.jredis.reply.ErrorReply;
import org.seeking.jredis.reply.StatusReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SaveCommand implements Command {
    private Map<String, Object> memory;

    private String filename;

    private CommandSpec commandSpec;

    public SaveCommand(Map<String, Object> memory, String filename) {
        this.memory = memory;
        this.filename = filename;
        this.commandSpec = new CommandSpec(1, new ArrayList<>(Arrays.asList("admin", "noscript")), 0, 0, 0);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        if (RDB.INSTANCE.dump(memory, filename))
            return new StatusReply("OK");
        else return new ErrorReply("Background save already in progress");
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.io.RDB;
import org.seeking.jredis.reply.StatusReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public class BgSaveCommand implements Command {
    private Map<String, Object> memory;

    private String filename;

    private ScheduledExecutorService ses;

    private CommandSpec commandSpec;

    public BgSaveCommand(Map<String, Object> memory, String filename, ScheduledExecutorService scheduledExecutorService) {
        this.memory = memory;
        this.filename = filename;
        this.ses = scheduledExecutorService;
        this.commandSpec = new CommandSpec(-1, new ArrayList<>(Arrays.asList("admin")), 0, 0, 0);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        ses.submit(() -> RDB.INSTANCE.dump(memory, filename));
        return new StatusReply("OK");
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

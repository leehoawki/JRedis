package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.reply.MultiBulkReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CmdCommand implements Command {
    private Map<String, Command> commands;

    private CommandSpec commandSpec;

    public CmdCommand(Map<String, Command> commands) {
        this.commands = commands;
        this.commandSpec = new CommandSpec(0, new ArrayList<>(Arrays.asList("loading", "stale")), 0, 0, 0);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        List<Reply> replyList = new ArrayList<>();
        for (Entry<String, Command> commandEntry : commands.entrySet()) {
            CommandSpec spec = commandEntry.getValue().getCommandSpec();
            if (spec == null) continue;
            List<Reply> command = new ArrayList<>();
            command.add(new BulkReply(commandEntry.getKey()));
            command.add(new IntegerReply(spec.getArity()));
            List<Reply> flags = new ArrayList<>();
            for (String flag : spec.getFlags()) {
                flags.add(new BulkReply(flag));
            }
            command.add(new MultiBulkReply(flags));
            command.add(new IntegerReply(spec.getFkial()));
            command.add(new IntegerReply(spec.getLkial()));
            command.add(new IntegerReply(spec.getStepCount()));
            replyList.add(new MultiBulkReply(command));
        }
        MultiBulkReply reply = new MultiBulkReply(replyList);
        return reply;
    }

    @Override
    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

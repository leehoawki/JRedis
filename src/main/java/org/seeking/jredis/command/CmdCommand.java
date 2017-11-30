package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.reply.MultiBulkReply;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CmdCommand implements Command {

    private Map<String, Command> commands;

    public CmdCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public Reply eval(List<String> params) {
        // TODO
        List<Reply> replyList = new ArrayList<>();
        for (Entry<String, Command> commandEntry : commands.entrySet()) {
            List<Reply> command = new ArrayList<>();
            command.add(new BulkReply(commandEntry.getKey()));
            command.add(new IntegerReply(2));
            List<Reply> flags = new ArrayList<>();
            flags.add(new BulkReply("readonly"));
            command.add(new MultiBulkReply(flags));
            command.add(new IntegerReply(1));
            command.add(new IntegerReply(1));
            command.add(new IntegerReply(1));
            replyList.add(new MultiBulkReply(command));
        }
        MultiBulkReply reply = new MultiBulkReply(replyList);
        return reply;
    }
}

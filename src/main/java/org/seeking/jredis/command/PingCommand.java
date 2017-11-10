package org.seeking.jredis.command;

import org.seeking.jredis.Command;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.StatusReply;

import java.util.List;

public class PingCommand implements Command {

    @Override
    public Reply eval(List<String> params) {
        return new StatusReply("PONG");
    }
}

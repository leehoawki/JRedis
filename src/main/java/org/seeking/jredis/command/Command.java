package org.seeking.jredis.command;

import org.seeking.jredis.reply.Reply;

import java.util.List;

public interface Command {
    Reply eval(List<String> params);
}

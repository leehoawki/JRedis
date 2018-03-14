package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.pubsub.ChannelManager;
import org.seeking.jredis.reply.IntegerReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PublishCommand implements Command {

    private CommandSpec commandSpec;

    public PublishCommand() {
        this.commandSpec = new CommandSpec(3, new ArrayList<>(Arrays.asList("pubsub", "loading", "stale", "fast")), 0, 0, 0);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        String channel = params.get(0);
        String message = params.get(1);
        int count = ChannelManager.INSTANCE.publish(channel, message);
        return new IntegerReply(count);
    }

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}


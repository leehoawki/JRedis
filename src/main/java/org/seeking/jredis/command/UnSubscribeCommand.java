package org.seeking.jredis.command;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Command;
import org.seeking.jredis.CommandSpec;
import org.seeking.jredis.Reply;
import org.seeking.jredis.pubsub.ChannelManager;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.reply.IntegerReply;
import org.seeking.jredis.reply.MultiBulkReply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UnSubscribeCommand implements Command {

    private CommandSpec commandSpec;

    public UnSubscribeCommand() {
        this.commandSpec = new CommandSpec(-2, new ArrayList<>(Arrays.asList("pubsub", "noscript", "loading", "stale")), 0, 0, 0);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        Collection<String> channels = params;
        if (params.isEmpty()) {
            channels = ChannelManager.INSTANCE.getChannels();
        }
        for (String channel : channels) {
            int count = ChannelManager.INSTANCE.unsubscribe(channel, ioSession);
            List<Reply> replyList = new ArrayList<>();
            replyList.add(new BulkReply("unsubscribe"));
            replyList.add(new BulkReply(channel));
            replyList.add(new IntegerReply(count));
            ioSession.write(new MultiBulkReply(replyList));
        }
        return null;
    }

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

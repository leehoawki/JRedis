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
import java.util.List;

public class SubscribeCommand implements Command {

    private CommandSpec commandSpec;

    public SubscribeCommand() {
        this.commandSpec = new CommandSpec(-1, new ArrayList<>(Arrays.asList("pubsub", "noscript", "loading", "stale")), 0, 0, 0);
    }

    @Override
    public Reply eval(List<String> params, IoSession ioSession) {
        for (String channel : params) {
            int count = ChannelManager.INSTANCE.subscribe(channel, ioSession);
            List<Reply> replyList = new ArrayList<>();
            replyList.add(new BulkReply("subscribe"));
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

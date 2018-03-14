package org.seeking.jredis.pubsub;

import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.Reply;
import org.seeking.jredis.reply.BulkReply;
import org.seeking.jredis.reply.MultiBulkReply;

import java.util.*;

public enum ChannelManager {
    INSTANCE;

    Map<String, List<IoSession>> channels = new HashMap<>();

    public int publish(String channel, String message) {
        List<IoSession> sessions = channels.get(channel);
        if (sessions == null || sessions.isEmpty()) return 0;
        for (IoSession session : sessions) {
            List<Reply> replyList = new ArrayList<>();
            replyList.add(new BulkReply("message"));
            replyList.add(new BulkReply(channel));
            replyList.add(new BulkReply(message));
            session.write(new MultiBulkReply(replyList));
        }
        return sessions.size();
    }

    public int subscribe(String channel, IoSession session) {
        if (channels.get(channel) == null) {
            channels.put(channel, new ArrayList<>());
        }
        channels.get(channel).add(session);
        return channels.get(channel).size();
    }

    public int unsubscribe(String channel, IoSession session) {
        if (channels.get(channel) == null) return 0;
        channels.get(channel).remove(session);
        return channels.get(channel).size();
    }

    public void remove(IoSession session) {
        for (List<IoSession> ioSessions : channels.values()) {
            ioSessions.remove(session);
        }
    }

    public Collection<String> getChannels() {
        return channels.keySet();
    }
}

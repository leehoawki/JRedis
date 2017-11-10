package org.seeking.jredis.reply;

import org.seeking.jredis.Reply;

public class StatusReply implements Reply {
    private String status;

    public StatusReply(String status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return "+" + status + "\r\n";
    }
}

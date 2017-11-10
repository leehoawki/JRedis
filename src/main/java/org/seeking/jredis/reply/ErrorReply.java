package org.seeking.jredis.reply;

import org.seeking.jredis.Reply;

public class ErrorReply implements Reply {
    private String status;

    public ErrorReply(String status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return "-" + status + "\r\n";
    }
}
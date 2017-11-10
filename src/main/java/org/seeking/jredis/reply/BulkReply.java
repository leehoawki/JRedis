package org.seeking.jredis.reply;

import org.seeking.jredis.Reply;

public class BulkReply implements Reply {
    private String value;

    public BulkReply(String value) {
        this.value = value;
    }

    @Override
    public String getMessage() {
        if (value == null || value.isEmpty()) {
            return "$-1" + "\r\n";
        }
        return "$" + value.length() + "\r\n" + value + "\r\n";
    }
}
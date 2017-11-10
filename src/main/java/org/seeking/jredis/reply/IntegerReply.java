package org.seeking.jredis.reply;

import org.seeking.jredis.Reply;

public class IntegerReply implements Reply {
    private int value;

    public IntegerReply(int value) {
        this.value = value;
    }

    @Override
    public String getMessage() {
        return ":" + value + "\r\n";
    }
}

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

    public static final String WRONG_TYPE = "WRONGTYPE Operation against a key holding the wrong kind of value";

    public static final String INT_OUT_OF_RANGE = "ERR value is not an integer or out of range";
}
package org.seeking.jredis.reply;

public class StatusReply implements Reply{
    private String status;

    public StatusReply(String status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return "+" + status + "\r\n";
    }
}

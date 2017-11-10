package org.seeking.jredis.reply;

import org.seeking.jredis.Reply;

import java.util.List;

public class MultiBulkReply implements Reply {
    private List<Reply> replys;

    public MultiBulkReply(List<Reply> replys) {
        this.replys = replys;
    }

    @Override
    public String getMessage() {
        return "*" + replys.size() + "\r\n" + replys.stream().map(x -> x.getMessage()).reduce("", (x, y) -> x + y);
    }
}

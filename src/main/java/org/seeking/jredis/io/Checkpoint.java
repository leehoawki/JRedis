package org.seeking.jredis.io;

import java.io.Serializable;
import java.util.Date;

public class Checkpoint implements Serializable {
    private final long timestamp;

    public Checkpoint() {
        timestamp = new Date().getTime();
    }

    public Checkpoint next() {
        return new Checkpoint();
    }

    public long get() {
        return timestamp;
    }
}

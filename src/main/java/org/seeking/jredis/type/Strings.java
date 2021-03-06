package org.seeking.jredis.type;

import java.io.Serializable;
import java.util.Date;

public class Strings implements Expirable, Serializable {
    final String val;

    long time;

    public Strings(String val) {
        this.val = val;
    }

    @Override
    public int ttl() {
        if (time > 0) return (int) ((time - new Date().getTime()) / 1000);
        return -1;
    }

    @Override
    public boolean isExpired() {
        return time > 0 && time < new Date().getTime();
    }

    @Override
    public boolean isExpirable() {
        return time > 0;
    }

    @Override
    public void persist() {
        time = 0;
    }

    @Override
    public void expire(int second) {
        time = new Date().getTime() + second * 1000;
    }

    public static Strings create(String val) {
        return new Strings(val);
    }

    public String val() {
        return val;
    }
}

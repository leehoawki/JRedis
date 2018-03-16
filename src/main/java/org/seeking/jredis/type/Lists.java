package org.seeking.jredis.type;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Lists implements Expirable, Serializable {
    final ConcurrentLinkedDeque<String> val;

    long time;

    public Lists(Collection<String> val) {
        this.val = new ConcurrentLinkedDeque(val);
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

    public int size() {
        return val.size();
    }

    public void add(String s) {
        val.add(s);
    }

    public void addFirst(String s) {
        val.addFirst(s);
    }

    public String poll() {
        return val.poll();
    }

    public String pollLast() {
        return val.pollLast();
    }
}

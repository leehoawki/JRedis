package org.seeking.jredis.type;

public interface Expirable {
    void expire(int second);

    int ttl();

    boolean isExpired();

    boolean isExpirable();

    void persist();
}

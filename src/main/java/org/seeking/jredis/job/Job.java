package org.seeking.jredis.job;

public interface Job {
    int rate();

    int delay();

    Runnable getRunnable();
}

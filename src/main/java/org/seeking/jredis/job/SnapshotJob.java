package org.seeking.jredis.job;

import org.seeking.jredis.io.RDB;

import java.util.Map;

public class SnapshotJob implements Job {

    final Map<String, Object> memory;

    final String filename;

    public SnapshotJob(Map<String, Object> memory, String filename) {
        this.memory = memory;
        this.filename = filename;
    }

    @Override
    public int rate() {
        return 60000;
    }

    @Override
    public int delay() {
        return 0;
    }

    @Override
    public Runnable getRunnable() {
        return () -> {
            RDB.INSTANCE.dump(memory, filename);
        };
    }
}
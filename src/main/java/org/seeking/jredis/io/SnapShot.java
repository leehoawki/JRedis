package org.seeking.jredis.io;

import java.io.Serializable;
import java.util.Map;

public class SnapShot implements Serializable {
    private Checkpoint checkpoint;

    private Map<String, Object> memory;

    public SnapShot(Map<String, Object> memory) {
        this.checkpoint = new Checkpoint();
        this.memory = memory;
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    public Map<String, Object> getMemory() {
        return memory;
    }

    public void setMemory(Map<String, Object> memory) {
        this.memory = memory;
    }
}

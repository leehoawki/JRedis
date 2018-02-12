package org.seeking.jredis.io;

import java.io.*;
import java.util.Map;

public enum SnapShot {
    INSTANCE;

    volatile int status = 0;

    static final String FILENAME = "dump.snapshot";

    public boolean exists() {
        return new File(FILENAME).exists();
    }

    public Map<String, Object> loads(Map<String, Object> memory) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))) {
            return (Map<String, Object>) in.readObject();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean dump(Map<String, Object> memory) {
        if (status > 0) return false;
        status = 1;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            out.writeObject(memory);
            status = 0;
            return true;
        } catch (IOException e) {
            status = 0;
            throw new IllegalStateException(e);
        }
    }
}

package org.seeking.jredis.io;

import java.io.*;
import java.util.Map;

public enum RDB {
    INSTANCE;

    volatile int status = 0;

    public boolean exists(String filename) {
        return new File(filename).exists();
    }

    public Map<String, Object> restore(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            SnapShot snapShot = (SnapShot) in.readObject();
            return snapShot.getMemory();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean dump(Map<String, Object> memory, String filename) {
        if (status > 0) return false;
        status = 1;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(new SnapShot(memory));
            status = 0;
            return true;
        } catch (IOException e) {
            status = 0;
            throw new IllegalStateException(e);
        }
    }
}
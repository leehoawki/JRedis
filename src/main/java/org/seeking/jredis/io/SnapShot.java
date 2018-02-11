package org.seeking.jredis.io;

import java.io.*;
import java.util.Map;

public class SnapShot {

    static final String FILENAME = "dump.snapshot";

    public static boolean exists() {
        return new File(FILENAME).exists();
    }

    public static Map<String, Object> loads(Map<String, Object> memory) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))) {
            return (Map<String, Object>) in.readObject();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void dump(Map<String, Object> memory) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            out.writeObject(memory);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

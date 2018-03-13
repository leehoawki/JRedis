package org.seeking.jredis.job;

import org.seeking.jredis.type.Expirable;

import java.util.*;
import java.util.stream.Collectors;

public class ExpirationJob implements Job {

    final Map<String, Object> memory;

    public ExpirationJob(Map<String, Object> memory) {
        this.memory = memory;
    }

    @Override
    public int rate() {
        return 1000;
    }

    @Override
    public int delay() {
        return 0;
    }

    @Override
    public Runnable getRunnable() {
        return () -> {
            List<Map.Entry<String, Object>> entries = memory.entrySet().stream().filter((x) -> ((Expirable) x.getValue()).isExpirable()).collect(Collectors.toList());
            while (expire(entries, memory)) ;
        };
    }

    static boolean expire(List<Map.Entry<String, Object>> entries, Map<String, Object> memory) {
        List<Integer> del = new ArrayList<>();
        int rate = 4;
        int times = Integer.min(20, entries.size());
        for (int i = 0; i < times; i++) {
            int r = RANDOM.nextInt(entries.size());
            if (((Expirable) entries.get(r).getValue()).isExpired()) {
                del.add(r);
            }
        }
        for (int i : del) {
            memory.remove(entries.get(i).getKey());
            entries.remove(i);
        }
        return del.size() * rate > times;
    }

    static Random RANDOM = new Random();
}
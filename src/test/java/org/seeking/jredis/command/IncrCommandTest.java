package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class IncrCommandTest extends TestCase {
    Jedis jedis;

    @Override
    public void setUp() {
        jedis = new Jedis("localhost", 9000);
    }

    @Override
    public void tearDown() {
        jedis.close();
    }

    @Test
    public void test() {
        jedis.del("incrtest");
        jedis.incr("incrtest");
        Assert.assertEquals("1", jedis.get("incrtest"));
        Assert.assertEquals(Long.valueOf(2), jedis.incr("incrtest"));
        Assert.assertEquals(Long.valueOf(3), jedis.incr("incrtest"));
        Assert.assertEquals(Long.valueOf(4), jedis.incr("incrtest"));
        Assert.assertEquals("4", jedis.get("incrtest"));
    }
}

package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seeking.jredis.JRedisTestRunner;
import redis.clients.jedis.Jedis;


@RunWith(JRedisTestRunner.class)
public class IncrCommandTest {
    Jedis jedis;

    @Before
    public void setUp() {
        jedis = new Jedis("localhost", 9000);
    }

    @After
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
        jedis.del("incrtest");
    }
}

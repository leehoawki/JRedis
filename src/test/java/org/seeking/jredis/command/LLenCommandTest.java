package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seeking.jredis.JRedisTestRunner;
import redis.clients.jedis.Jedis;


@RunWith(JRedisTestRunner.class)
public class LLenCommandTest {
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
        Assert.assertEquals(0, jedis.llen("llentest").intValue());
        jedis.rpush("llentest", "wa");
        Assert.assertEquals(1, jedis.llen("llentest").intValue());
        jedis.rpush("llentest", "ha");
        Assert.assertEquals(2, jedis.llen("llentest").intValue());
        jedis.rpush("llentest", "ha");
        Assert.assertEquals(3, jedis.llen("llentest").intValue());
        jedis.rpush("llentest", "su", "ba", "ra");
        Assert.assertEquals(6, jedis.llen("llentest").intValue());
        jedis.del("llentest");
        Assert.assertEquals(0, jedis.llen("llentest").intValue());
    }
}

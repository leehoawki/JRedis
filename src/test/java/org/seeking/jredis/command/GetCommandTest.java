package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import redis.clients.jedis.Jedis;

@RunWith(JRedisTestRunner.class)
public class GetCommandTest {
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
        jedis.set("getTest", "b");
        Assert.assertEquals("b", jedis.get("getTest"));
        jedis.set("getTest", "c");
        Assert.assertEquals("c", jedis.get("getTest"));
        jedis.del("getTest");
    }
}

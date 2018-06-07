package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import redis.clients.jedis.Jedis;

@RunWith(JRedisTestRunner.class)
public class ExpireCommandTest {
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
        Assert.assertEquals(0, jedis.expire("expireTest", 10).intValue());
        jedis.set("expireTest", "a");
        Assert.assertEquals(1, jedis.expire("expireTest", 10).intValue());
        Assert.assertEquals(1, jedis.expire("expireTest", 10).intValue());
        jedis.del("expireTest");
    }
}

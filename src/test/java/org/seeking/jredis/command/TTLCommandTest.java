package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seeking.jredis.JRedisTestRunner;
import redis.clients.jedis.Jedis;

@RunWith(JRedisTestRunner.class)
public class TTLCommandTest {
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
    public void test() throws InterruptedException {
        jedis.set("ttlTest", "a");
        Assert.assertEquals(-1, jedis.ttl("ttlTest").intValue());
        jedis.expire("ttlTest", 2);
        Assert.assertTrue(jedis.ttl("ttlTest") > 0);
        Thread.sleep(5000);
        Assert.assertEquals(-2, jedis.ttl("ttlTest").intValue());
        Assert.assertEquals(null, jedis.get("ttlTest"));
        Assert.assertEquals(false, jedis.exists("ttlTest"));
        jedis.del("ttlTest");
    }
}

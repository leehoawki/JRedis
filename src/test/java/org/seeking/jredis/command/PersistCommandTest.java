package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seeking.jredis.JRedisTestRunner;
import redis.clients.jedis.Jedis;


@RunWith(JRedisTestRunner.class)
public class PersistCommandTest {
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
        Assert.assertEquals(0, jedis.persist("persistTest").intValue());
        jedis.set("persistTest", "a");
        Assert.assertEquals(0, jedis.persist("persistTest").intValue());
        jedis.expire("persistTest", 2);
        Assert.assertEquals(1, jedis.persist("persistTest").intValue());
        jedis.del("ttlTest");
    }
}

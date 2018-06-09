package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seeking.jredis.JRedisTestRunner;
import redis.clients.jedis.Jedis;

@RunWith(JRedisTestRunner.class)
public class DelCommandTest {
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
        Assert.assertTrue(0 == jedis.del("delTest"));
        Assert.assertEquals(false, jedis.exists("delTest"));
        jedis.set("delTest", "a");
        Assert.assertEquals(true, jedis.exists("delTest"));
        Assert.assertTrue(1 == jedis.del("delTest"));
        Assert.assertEquals(false, jedis.exists("delTest"));
        Assert.assertTrue(0 == jedis.del("delTest"));

        jedis.set("delTest1", "a");
        jedis.set("delTest2", "b");
        jedis.set("delTest3", "c");
        Assert.assertTrue(3 == jedis.del("delTest", "delTest1", "delTest2", "delTest3"));
    }
}

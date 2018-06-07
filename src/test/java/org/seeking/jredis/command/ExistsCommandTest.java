package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import redis.clients.jedis.Jedis;

@RunWith(JRedisTestRunner.class)
public class ExistsCommandTest {
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
        Assert.assertEquals(false, jedis.exists("existTest"));
        jedis.set("existTest", "a");
        Assert.assertEquals(true, jedis.exists("existTest"));
        jedis.del("existTest");
    }
}

package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import redis.clients.jedis.Jedis;


@RunWith(JRedisTestRunner.class)
public class RPopCommandTest {
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
        jedis.lpush("rpoptest", "wa");
        jedis.lpush("rpoptest", "ha");
        jedis.lpush("rpoptest", "ha");
        jedis.lpush("rpoptest", "su", "ba", "ra");

        Assert.assertEquals("wa", jedis.rpop("rpoptest"));
        Assert.assertEquals("ha", jedis.rpop("rpoptest"));
        Assert.assertEquals("ha", jedis.rpop("rpoptest"));
        Assert.assertEquals("su", jedis.rpop("rpoptest"));
        Assert.assertEquals("ba", jedis.rpop("rpoptest"));
        Assert.assertEquals("ra", jedis.rpop("rpoptest"));
        Assert.assertEquals(null, jedis.rpop("rpoptest"));

        jedis.del("rpoptest");
    }
}

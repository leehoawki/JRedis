package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class LPopCommandTest extends TestCase {
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
        jedis.rpush("lpoptest", "wa");
        jedis.rpush("lpoptest", "ha");
        jedis.rpush("lpoptest", "ha");
        jedis.rpush("lpoptest", "su", "ba", "ra");

        Assert.assertEquals("wa", jedis.lpop("lpoptest"));
        Assert.assertEquals("ha", jedis.lpop("lpoptest"));
        Assert.assertEquals("ha", jedis.lpop("lpoptest"));
        Assert.assertEquals("su", jedis.lpop("lpoptest"));
        Assert.assertEquals("ba", jedis.lpop("lpoptest"));
        Assert.assertEquals("ra", jedis.lpop("lpoptest"));
        Assert.assertEquals(null, jedis.lpop("lpoptest"));

        jedis.del("lpoptest");
    }
}

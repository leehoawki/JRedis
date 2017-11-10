package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RPopCommandTest extends TestCase {
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
    }
}

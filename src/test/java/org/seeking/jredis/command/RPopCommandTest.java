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
    public void testRPop() {
        jedis.lpush("wa", "wa");
        jedis.lpush("wa", "ha");
        jedis.lpush("wa", "ha");
        jedis.lpush("wa", "su", "ba", "ra");

        Assert.assertEquals("wa", jedis.rpop("wa"));
        Assert.assertEquals("ha", jedis.rpop("wa"));
        Assert.assertEquals("ha", jedis.rpop("wa"));
        Assert.assertEquals("su", jedis.rpop("wa"));
        Assert.assertEquals("ba", jedis.rpop("wa"));
        Assert.assertEquals("ra", jedis.rpop("wa"));
        Assert.assertEquals(null, jedis.rpop("wa"));
    }
}

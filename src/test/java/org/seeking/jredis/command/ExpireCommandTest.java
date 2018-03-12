package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class ExpireCommandTest extends TestCase {
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
        Assert.assertEquals(0, jedis.expire("expireTest", 10).intValue());
        jedis.set("expireTest", "a");
        Assert.assertEquals(1, jedis.expire("expireTest", 10).intValue());
        Assert.assertEquals(1, jedis.expire("expireTest", 10).intValue());
        jedis.del("expireTest");
    }
}

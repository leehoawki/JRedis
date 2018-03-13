package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TTLCommandTest extends TestCase {
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

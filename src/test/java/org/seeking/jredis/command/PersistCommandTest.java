package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class PersistCommandTest extends TestCase {
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
        Assert.assertEquals(0, jedis.persist("persistTest").intValue());
        jedis.set("persistTest", "a");
        Assert.assertEquals(0, jedis.persist("persistTest").intValue());
        jedis.expire("persistTest", 2);
        Assert.assertEquals(1, jedis.persist("persistTest").intValue());
        jedis.del("ttlTest");
    }
}

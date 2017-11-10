package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class DelCommandTest extends TestCase {
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

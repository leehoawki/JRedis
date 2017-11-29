package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class ExistsCommandTest extends TestCase {
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
        jedis.del("existTest");
        Assert.assertEquals(false, jedis.exists("existTest"));
        jedis.set("existTest", "a");
        Assert.assertEquals(true, jedis.exists("existTest"));
    }
}

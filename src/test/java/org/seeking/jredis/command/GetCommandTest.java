package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class GetCommandTest extends TestCase {
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
    public void testGet() {
        jedis.set("a", "b");
        Assert.assertEquals("b", jedis.get("a"));
        jedis.set("a", "c");
        Assert.assertEquals("c", jedis.get("a"));
    }
}

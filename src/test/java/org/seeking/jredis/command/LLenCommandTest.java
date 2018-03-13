package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class LLenCommandTest extends TestCase {
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
        Assert.assertEquals(0, jedis.llen("llentest").intValue());
        jedis.rpush("llentest", "wa");
        Assert.assertEquals(1, jedis.llen("llentest").intValue());
        jedis.rpush("llentest", "ha");
        Assert.assertEquals(2, jedis.llen("llentest").intValue());
        jedis.rpush("llentest", "ha");
        Assert.assertEquals(3, jedis.llen("llentest").intValue());
        jedis.rpush("llentest", "su", "ba", "ra");
        Assert.assertEquals(6, jedis.llen("llentest").intValue());
        jedis.del("llentest");
        Assert.assertEquals(0, jedis.llen("llentest").intValue());
    }
}

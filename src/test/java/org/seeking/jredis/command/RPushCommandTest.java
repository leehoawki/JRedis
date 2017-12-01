package org.seeking.jredis.command;

import junit.framework.TestCase;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RPushCommandTest extends TestCase {
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
        jedis.lpush("rpushtest","wa");
        jedis.lpush("rpushtest","ha");
        jedis.lpush("rpushtest","ha");
        jedis.lpush("rpushtest","su","ba","ra");
        jedis.del("rpushtest");
    }
}

package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seeking.jredis.JRedisTestRunner;
import redis.clients.jedis.Jedis;


@RunWith(JRedisTestRunner.class)
public class LPushCommandTest {
    Jedis jedis;

    @Before
    public void setUp() {
        jedis = new Jedis("localhost", 9000);
    }

    @After
    public void tearDown() {
        jedis.close();
    }

    @Test
    public void test() {
        jedis.lpush("lpushtest","wa");
        jedis.lpush("lpushtest","ha");
        jedis.lpush("lpushtest","ha");
        jedis.lpush("lpushtest","su","ba","ra");
        jedis.del("lpushtest");
    }
}

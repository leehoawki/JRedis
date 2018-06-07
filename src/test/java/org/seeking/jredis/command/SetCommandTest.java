package org.seeking.jredis.command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import redis.clients.jedis.Jedis;


@RunWith(JRedisTestRunner.class)
public class SetCommandTest {
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
        jedis.set("setTest", "b");
        jedis.set("setTest", "b");
        jedis.set("setTest", "c");

        jedis.del("setTest");
    }
}

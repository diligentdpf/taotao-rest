package diao.taotao.rest.jedisTest;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis客户端jedis包的测试 2016年5月31日14:24
 * 
 */
public class TestJedis {
    private static String url = "192.168.1.193";
    private static int port = 6379;

    //@Test
    public void testJedisSingle() {
        // 创建一个jedis的对象。
        Jedis jedis = new Jedis(url, port);
        // 调用jedis对象的方法，方法名称和redis的命令一致。
        jedis.set("key1", "jedis test");
        String string = jedis.get("key1");
        System.out.println(string);
        System.out.println(jedis.get("a"));
        // 关闭jedis。
        jedis.close();
    }

    /**
     * 使用连接池
     */
   //@Test
    public void testJedisPool() {
        // 创建jedis连接池
        JedisPool pool = new JedisPool(url, port);
        // 从连接池中获得Jedis对象
        Jedis jedis = pool.getResource();
        String string = jedis.get("key1");
        System.out.println(string);
        // 关闭jedis对象
        jedis.close();
        pool.close();
    }

    /**
     * 单机版测试 2016年5月31日19:16
     */
   // @Test
    public void testSpringJedisSingle() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = pool.getResource();
        String string = jedis.get("key1");
        System.out.println(string + " , " + jedis.get("b"));
        // 关闭jedis客户端对象
        jedis.close();
        // 关闭连接池对象
        pool.close();
    }

}

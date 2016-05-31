package diao.taotao.rest.jedisTest;

import java.util.HashSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestJedisCluster {

    private static String url = "192.168.1.193";
    private static int port = 6379;

   // @Test
    public void testJedisCluster() {
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort(url, 7001));
        nodes.add(new HostAndPort(url, 7002));
        nodes.add(new HostAndPort(url, 7003));
        nodes.add(new HostAndPort(url, 7004));
        nodes.add(new HostAndPort(url, 7005));
        nodes.add(new HostAndPort(url, 7006));

        JedisCluster cluster = new JedisCluster(nodes);
        cluster.set("key1", "1000");
        // cluster.set("b.", "11200");
        String string = cluster.get("b.");
        System.out.println(string);
        // 关闭集群对象
        cluster.close();
    }

    /**
     * 集群版测试 2016年5月31日19:30
     */
    //@Test
    public void testSpringJedisCluster() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisClient");
        String string = jedisCluster.get("a");
        System.err.println(string);
        jedisCluster.close();
    }
}

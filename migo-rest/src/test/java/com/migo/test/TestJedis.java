package com.migo.test;

import com.migo.rest.jediscomp.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Author  知秋
 * Created by kauw on 2016/10/18.
 */
public class TestJedis {
    @Test
     public void teJedisSingle(){
        //创建一个jedis对象
        Jedis jedis=new Jedis("192.168.42.131",6379);
        jedis.set("migo","hello migo");
        String s=jedis.get("migo");
        System.out.println(s);
        jedis.close();
     }
    //连接池
    @Test
    public void testJedisPool(){
        JedisPool jedisPool=new JedisPool("192.168.42.131",6379);
        Jedis jedis=jedisPool.getResource();
        String string=jedis.get("migo");
        System.out.println(string);
        //jedis必须关闭,否则无法释放连接
        jedis.close();
        //系统关闭时会关闭连接池
        jedisPool.close();
    }

    //jedis集群
    @Test
    public void teJedisCluster(){
        //创建一个jediscluster对象
        Set<HostAndPort> nodes=new HashSet<>();
        nodes.add(new HostAndPort("192.168.42.131",7001));
        nodes.add(new HostAndPort("192.168.42.131",7002));
        nodes.add(new HostAndPort("192.168.42.131",7003));
        nodes.add(new HostAndPort("192.168.42.131",7004));
        nodes.add(new HostAndPort("192.168.42.131",7005));
        nodes.add(new HostAndPort("192.168.42.131",7006));
        JedisCluster jedisCluster=new JedisCluster(nodes);
        jedisCluster.set("name","zhangsan");
        jedisCluster.set("age","26");
        String name=jedisCluster.get("name");
        String value=jedisCluster.get("age");
        System.out.println(name);
        System.out.println(value);
        //记得关闭
        jedisCluster.close();
    }

    @Test
    public void teJedisSpring(){
        ApplicationContext context=
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisClient jedisClient=context.getBean(JedisClient.class);
        jedisClient.set("spring","55555");
        String s=jedisClient.get("spring");
        System.out.println(s);
    }

}

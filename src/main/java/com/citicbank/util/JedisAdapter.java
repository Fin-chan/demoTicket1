package com.citicbank.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by FIN on 2016/12/12.
 */
@Service
public class JedisAdapter implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    public static void print(int index,Object obj){
        System.out.println(String.format("%d,%s",index,obj.toString()));
    }
    //排行榜，关注，点赞，PV
    //验证码可以放到redis

    /**
     * REDIS测试使用
     * @param argv
     */
    public static void mainx(String[] argv) {

        Jedis jedis=new Jedis();
        jedis.flushAll();

        jedis.set("hello","word");
        print(1,jedis.get("hello"));
        jedis.rename("hello","new hello");
        jedis.setex("hello2",15,"word");

        //访问并发，先存到redis
        jedis.set("pv","100");
        long ii=99;
        print(2,jedis.exists("pv"));




        String listname="listA";
        for(int i=0;i<10;++i){
            jedis.lpush(listname,"a"+String.valueOf(i));
        }

        jedis.lpush(listname,"a0");
        print(3,jedis.lrange(listname,0,12));

        //hash  临时添加一些临时字段，可以hash结构
        String userKey="userxx";
        jedis.hset(userKey,"name","jim");
        jedis.hset(userKey,"age","12");
        jedis.hset(userKey,"phone","181341654189");
        jedis.hset(userKey,"name","jim2");
        print(4,jedis.hgetAll(userKey));
        print(4,jedis.hvals(userKey));


    }
    private Jedis jedis = null;
    private JedisPool pool = null;
    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("localhost",6379);
    }

    private Jedis getJedis(){
        return pool.getResource();
    }

    /**
     * 获取redis key
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return getJedis().get(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long decr(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.decr(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 把对象变成一个json字符串 存储到key中
     *
     * @param key
     * @param obj
     */
    public void setObject(String key, Object obj) {
        set(key, JSON.toJSONString(obj));
    }

    /**
     * 把文本解析出来变成对象
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String value = get(key);
        if (value != null) {
            return JSON.parseObject(value, clazz);
        }
        return null;
    }

    /**
     * 进队
     *
     * @param key
     * @param value
     * @return
     */
    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 出队
     * @param timeout
     * @param key
     * @return
     */
    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}

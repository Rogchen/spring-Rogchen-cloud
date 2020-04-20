package com.rogchen.redislock.redislockdemo.Locks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Rogchen  rogchen128@gmail.com
 * @description:
 * @product: IntelliJ IDEA
 * @create by 20-4-20 15:11
 **/
@Component
public class RedisDistriButeLock implements Lock {
    @Autowired
    private StringRedisTemplate redisTemplate;
    //    记录持有锁的线程
    private static ThreadLocal<String> owerUuid = new ThreadLocal<>();
    private Thread owerThread = null;
    //    锁等待时间 100 毫秒
    private long waitTime = 100;
    //    锁有效时间
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    private String redisKey = "user-redisTemplate-redisLocal";

    @Override
    public boolean tryLock() {
        try {
            return tryLock(waitTime, timeUnit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void unlock() {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        try {
            Long result = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    Object nativeConnect = connection.getNativeConnection();
                    if (nativeConnect instanceof JedisCluster) {  //集群模式
                        return (Long) ((JedisCluster) nativeConnect).eval(script, Arrays.asList(redisKey), Arrays.asList(owerUuid.get()));
                    } else if (nativeConnect instanceof Jedis) {
                        return (Long) ((Jedis) nativeConnect).eval(script, Arrays.asList(redisKey), Arrays.asList(owerUuid.get()));
                    }
                    return 0L;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        while (!tryLock()) {
            try {
                TimeUnit.MILLISECONDS.sleep(waitTime);   //10毫秒轮寻
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        String oldUUid = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isEmpty(oldUUid)) {
            oldUUid = UUID.randomUUID().toString().replace("-", "");
            Thread thread = Thread.currentThread();
            owerThread = thread;    //记录存锁的线程
            owerUuid.set(oldUUid);
            String finalOldUUid = oldUUid;
//            redisTemplate.set 无法保证原子性
            String result = redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                    return commands.set(redisKey, finalOldUUid, "NX", "PX", 10*1000);//有效期10秒
                }
            });
            if (!StringUtils.isEmpty(result) && result.equalsIgnoreCase("OK")) {
                return true;
            }
        } else if (owerThread == Thread.currentThread()) {   //设置可重入
            return true;
        }
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}

package com.rogchen.redislock.redislockdemo;

import com.rogchen.redislock.redislockdemo.Locks.RedisDistriButeLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockDemoApplicationTests {
    @Autowired
    private RedisDistriButeLock lock;

    @Test
    public void contextLoads() throws InterruptedException {
        lock.lock();
        System.out.println("第一次上锁");
        lock.lock();
        System.out.println("同步：" + new Date().toLocaleString());
        System.out.println("同步-第二次上锁");
        Thread ohter = new Thread(() -> {
            System.out.println("异步,需要等5秒前：" + new Date().toLocaleString());
            System.out.println("异步：第3线程尝试获取锁");
            lock.lock();
            System.out.println("异步,需要等5秒后：" + new Date().toLocaleString());
            System.out.println("异步：第3线获取到了锁进行输出");
            lock.unlock();
            System.out.println("异步解锁");
        });
        ohter.start();
        TimeUnit.SECONDS.sleep(5);
        lock.unlock();
        lock.unlock();
        System.out.println("获取锁结束");
        ohter.join();
    }

}

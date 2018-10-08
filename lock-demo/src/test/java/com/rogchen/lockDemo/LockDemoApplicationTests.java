package com.rogchen.lockDemo;

import com.rogchen.lockDemo.config.LockConfig;
import com.rogchen.lockDemo.service.LockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.locks.Lock;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LockDemoApplicationTests {
    @Autowired
    private LockService lockService;
    @Autowired
    LockConfig lock;


    @Test
    public void contextLoads() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                public void run() {
                    log.info("初始化线程" + Thread.currentThread().getName());
                    lockService.handleData();
                }
            }).start();
        }
        //把主线程阻塞住
        Thread.currentThread().join();
    }

    /**
     *  出现线程安全问题
     * @throws InterruptedException
     */
    @Test
    public void unContextLoads() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                public void run() {
                    log.info("初始化线程" + Thread.currentThread().getName());
                    lockService.unLockHandleData();
                }
            }).start();
        }
        //把主线程阻塞住
        Thread.currentThread().join();
    }
}

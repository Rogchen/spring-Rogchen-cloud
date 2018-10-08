package com.rogchen.lockDemo.service;

import com.rogchen.lockDemo.config.AbstractLockConfig;
import com.rogchen.lockDemo.entity.lockEntity;
import com.rogchen.lockDemo.entity.lockName;
import com.rogchen.lockDemo.mapper.LockNameMapper;
import com.rogchen.lockDemo.mapper.LockSetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen rogchen128@gmail.com
 * @Created Date: 2018/9/30 09:50
 **/
@Service
@Slf4j
public class LockService extends AbstractLockConfig {
    @Autowired
    private LockSetMapper lockSetMapper;
    @Autowired
    private LockNameMapper lockNameMapper;
    public static int count = 0;    //全局订单Id

//    private Lock lock = new ReentrantLock();

    public void insertName() {
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                public void run() {
                    log.debug("初始化线程" + Thread.currentThread().getName());
//                    handleData();
                    unLockHandleData();
                }
            }).start();
        }
    }

    public void unLockHandleData() {
        lockName lockName = new lockName();
        lockName.setName(new Date().toLocaleString());
        lockNameMapper.insert(lockName);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行完成！" + ++count);
    }

    public void handleData() {
        try {
            getlock();
//            Thread.sleep(2000);
            lockName lockName = new lockName();
            lockName.setName(new Date().toLocaleString());
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = ++count;
            lockNameMapper.insert(lockName);
            System.out.println(Thread.currentThread().getName() + "执行完成！" + i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            unlock();
        }
    }

    /**
     * 非阻塞式锁
     *
     * @return
     */
    public boolean trylock() {
        try {
            lockEntity lockEntity = new lockEntity();
            lockEntity.setId(1);
            lockSetMapper.insert(lockEntity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void waitlock() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unlock() {
        lockSetMapper.deleteByPrimaryKey(1);
    }
}

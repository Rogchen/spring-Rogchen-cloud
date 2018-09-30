package com.rogchen.lockDemo.service;

import com.rogchen.lockDemo.config.LockConfig;
import com.rogchen.lockDemo.entity.lockEntity;
import com.rogchen.lockDemo.entity.lockName;
import com.rogchen.lockDemo.mapper.LockNameMapper;
import com.rogchen.lockDemo.mapper.LockSetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen rogchen128@gmail.com
 * @Created Date: 2018/9/30 09:50
 **/
@Service
@Slf4j
public class LockService extends LockConfig {
    @Autowired
    private LockSetMapper lockSetMapper;
    @Autowired
    private LockNameMapper lockNameMapper;

    private CountDownLatch latch = null;

    public void insertName() {
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                public void run() {
                    log.debug("初始化线程" + Thread.currentThread().getName());
                    handleData();
                }
            }).start();
        }
    }


    private void handleData() {
        if (trylock()) {
            lockName lockName = new lockName();
            lockName.setName(new Date().toLocaleString());
            lockNameMapper.insert(lockName);
            //模拟效果
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName() + "执行完成！");
            if (latch != null)
                latch.countDown();
        } else {
            waitlock();
        }
    }

    @Override
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

    @Override
    public void waitlock() {
        latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        unlock();
    }
}

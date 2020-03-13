package com.rogchen.lockDemo.config;

import com.rogchen.lockDemo.mapper.LockSetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 锁
 * @Product: IntelliJ IDEA
 * @Author Rogchen rogchen128@gmail.com
 * @Created Date: 2018/9/30 14:00
 **/
@Service
@Slf4j
public abstract class AbstractLockConfig implements LockConfig{
    @Autowired
    private LockSetMapper lockSetMapper;


    /**
     * 获取锁
     */
    public void getlock() {
        if(trylock()) {
            System.out.println(Thread.currentThread().getName()+"获取锁===========");
//            lockEntity lockEntity = new lockEntity();
//            lockEntity.setId(1);
//            lockSetMapper.insert(lockEntity);
        }else {
            waitlock(); //休息10ms再次获取锁
            getlock();
        }
    }

    /**
     * 解锁
     */
//    public void unlock() {
//        lockSetMapper.deleteByPrimaryKey(1);
//    }

    /**
     * 尝试获取锁
     */
    public abstract boolean trylock();

    //锁等待
    public abstract void waitlock();
}

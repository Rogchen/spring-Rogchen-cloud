package com.rogchen.lockDemo.config;

import com.rogchen.lockDemo.entity.lockEntity;
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
public abstract class LockConfig implements LockConfigService{
    @Autowired
    private LockSetMapper lockSetMapper;


    /**
     * 上锁
     */
    public void lock() {
        if(trylock()) {
            lockEntity lockEntity = new lockEntity();
            lockEntity.setId(1);
            lockSetMapper.insert(lockEntity);
        }else {
            waitlock();
            lock();
        }
    }

    /**
     * 解锁
     */
    public void unlock() {
        lockSetMapper.deleteByPrimaryKey(1);
    }

    /**
     * 尝试获取锁
     */
    public abstract boolean trylock();

    //锁等待
    public abstract void waitlock();
}

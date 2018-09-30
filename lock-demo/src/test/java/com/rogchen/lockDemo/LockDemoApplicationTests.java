package com.rogchen.lockDemo;

import com.rogchen.lockDemo.service.LockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LockDemoApplicationTests {
    @Autowired
    private LockService lockService;


    @Test
    public void contextLoads() {
        lockService.insertName();
    }

}

package com.rogchen.www.mybatis.service.impl;

import com.rogchen.www.mybatis.entity.CloudDemo;
import com.rogchen.www.mybatis.mapper.CloudDemoMapper;
import com.rogchen.www.mybatis.service.MybatisDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen chenhk128@163.com
 * @Created Date: 2018/3/20 15:29
 **/
@Service
public class MybatisDemoServiceImpl implements MybatisDemoService{
    @Autowired
    private CloudDemoMapper cloudDemoMapper;
    @Override
    public CloudDemo get(int id) {
        return cloudDemoMapper.selectByPrimaryKey(id);
    }
}

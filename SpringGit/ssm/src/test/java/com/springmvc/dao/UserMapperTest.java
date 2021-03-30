package com.springmvc.dao;

import com.springmvc.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectByPrimaryKey() throws Exception{

        int id = 2;
        User user = userMapper.selectByPrimaryKey(String.valueOf(id));
        System.out.println("user.getName():" + user.getName());

        System.out.println("喵喵，Test终于出来啦！");
    }
}
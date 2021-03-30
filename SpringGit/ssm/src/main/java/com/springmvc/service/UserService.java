package com.springmvc.service;
import com.springmvc.dao.UserMapper;
import com.springmvc.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(String id){
        return userMapper.selectByPrimaryKey(id);
    }

    public int insert(User record){
        return userMapper.insert(record);
    }

}
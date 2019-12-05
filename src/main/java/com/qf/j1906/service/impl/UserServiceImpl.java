package com.qf.j1906.service.impl;

import com.qf.j1906.mapper.UserMapper;
import com.qf.j1906.po.User;
import com.qf.j1906.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 刘旭
 * Date: 2019/12/5 15:58
 * @Version 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> loadAll() {
        List<User> userList = userMapper.loadAll();
        return userList;
    }

    @Override
    public boolean saveOneUser(User user) {
        int i = userMapper.saveOneUser(user);
        return i>0?true:false;
    }
}

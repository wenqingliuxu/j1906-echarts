package com.qf.j1906.service;

import com.qf.j1906.po.User;

import java.util.List;

/**
 * @Author: 刘旭
 * Date: 2019/12/5 15:57
 * @Version 1.0
 */
public interface UserService {
    List<User> loadAll();
    //增加一条用户信息
    boolean saveOneUser(User user);
}

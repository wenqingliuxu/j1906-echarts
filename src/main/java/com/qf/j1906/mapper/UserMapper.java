package com.qf.j1906.mapper;

import com.qf.j1906.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 刘旭
 * Date: 2019/12/5 15:54
 * @Version 1.0
 */
@Mapper
@Repository
public interface UserMapper {
    List<User> loadAll();
    int saveOneUser(User user);
}

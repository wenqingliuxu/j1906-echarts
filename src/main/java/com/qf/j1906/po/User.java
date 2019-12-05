package com.qf.j1906.po;

import lombok.Data;

/**
 * @Author: 刘旭
 * Date: 2019/12/4 20:55
 * @Version 1.0
 */
@Data
public class User {
    private int userId;
    private String name;
    private String password;
    private String gender;
    private int age;
}

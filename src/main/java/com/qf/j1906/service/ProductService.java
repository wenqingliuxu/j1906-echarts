package com.qf.j1906.service;

import com.qf.j1906.po.Product;

import java.util.List;

/**
 * @Author: 刘旭
 * Date: 2019/12/4 21:18
 * @Version 1.0
 */
public interface ProductService {
    public List<Product> findProductById(int id);
}

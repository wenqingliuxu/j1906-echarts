package com.qf.j1906.service.impl;

import com.qf.j1906.mapper.ProductMapper;
import com.qf.j1906.po.Product;
import com.qf.j1906.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 刘旭
 * Date: 2019/12/4 21:18
 * @Version 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findProductById(int id) {
        List<Product> productList = productMapper.findProductById(id);
        return productList;
    }
}

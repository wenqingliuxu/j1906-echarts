package com.qf.j1906.mapper;

import com.qf.j1906.po.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 刘旭
 * Date: 2019/12/4 21:04
 * @Version 1.0
 */
@Mapper
@Repository
public interface ProductMapper {
    public List<Product> findProductById(int id);
}

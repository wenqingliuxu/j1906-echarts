package com.qf.j1906.controller;

import com.qf.j1906.po.Echarts;
import com.qf.j1906.po.Product;
import com.qf.j1906.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: 刘旭
 * Date: 2019/12/4 21:20
 * @Version 1.0
 */
@Slf4j
@Controller
public class ProductorController {
    @Autowired
    private ProductService productService;
    @RequestMapping("/echarts")
    public String findProductById(int id, Model model){
        List<Product> products = productService.findProductById(id);
        ArrayList<Object> dateList = new ArrayList<>();
        ArrayList rateList=new ArrayList<>();
        for (Product product:products) {
            dateList.add(product.getPdcDate());
//            String s = product.getPdcRate().toString("yyyy-MM-dd");
            rateList.add(product.getPdcRate());
        }
        System.out.println(dateList);
        System.out.println(rateList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("rateList",rateList);
        return "echarts";
    }
}

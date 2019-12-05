package com.qf.j1906.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: 刘旭
 * Date: 2019/12/5 0:50
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Echarts {
    private Date date;
    private Double point;
}

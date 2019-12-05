package com.qf.j1906.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: 刘旭
 * Date: 2019/12/4 21:01
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private int pdcId;
    private String pdcName;
    private String pdcRisk;
    private long boughtNum;
    private String pdcDeadline;
    private String brightSpot;
    private String tradingRules;
    private String bearingRules;
    private Double pdcRate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pdcDate;

}

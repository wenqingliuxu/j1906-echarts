package com.qf.j1906.po;

import lombok.Data;

/**
 * @Author: 刘旭
 * Date: 2019/12/5 20:37
 * @Version 1.0
 */
@Data
public class AlipayVo {
    /** 订单名称 */
    private String subject;

    /** 商品网站唯一订单号 */
    private String out_trade_no;

    /** 该笔订单最晚付款时间 */
    private String timeout_express;

    /** 付款金额 */
    private String total_amount;

    /** 销售产品码，与支付宝签约的产品码名称 -注：目前仅支持FAST_INSTANT_TRADE_PAY */
    private String product_code = "FAST_INSTANT_TRADE_PAY";

    /** 商品描述 */
    private String body;
}

package com.qf.j1906.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.j1906.config.AlipayConfig;
import com.qf.j1906.po.AlipayVo;
import com.qf.j1906.util.AlipayUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 刘旭
 * Date: 2019/12/5 20:35
 * @Version 1.0
 */

    @Controller
    @RequestMapping("/alipay")
    public class AlipayController {
        private static Logger logger = LogManager.getLogger(AlipayController.class);

        @RequestMapping("/pay")
        @ResponseBody
        public String pay(HttpServletRequest request) {
            AlipayVo alipayVo = new AlipayVo();
            // 设置订单单号，需要保证唯一性
            alipayVo.setOut_trade_no(AlipayUtil.getInstance().nextId() + "");
            // 设置支付金额t
                alipayVo.setTotal_amount("88888");
//            alipayVo.setTotal_amount(request.getAttribute("total_amount").toString());
            // 设置支付标题
                alipayVo.setSubject("88888");
//            alipayVo.setSubject(request.getAttribute("subject").toString());
            // 设置订单有效时间
            alipayVo.setTimeout_express("5m");
            // 商品码（必须为"QUICK_WAP_WAY"、"FAST_INSTANT_TRADE_PAY")
            alipayVo.setProduct_code("FAST_INSTANT_TRADE_PAY");
            // 商品描述
//            alipayVo.setBody(request.getAttribute("body").toString());
            alipayVo.setBody("商品描述");
            // 对象转json字符串
            String json = JSONObject.toJSONString(alipayVo);

            // 获得初始化alipayClient
            AlipayClient alipayClient =
                    new DefaultAlipayClient(
                            AlipayConfig.gatewayUrl,
                            AlipayConfig.app_id,
                            AlipayConfig.merchant_private_key,
                            AlipayConfig.format,
                            AlipayConfig.charset,
                            AlipayConfig.alipay_public_key,
                            AlipayConfig.sign_type);

            // 设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            // 封装请求支付信息
            alipayRequest.setBizContent(json);

            // 请求
            String result;
            try {
                result = alipayClient.pageExecute(alipayRequest).getBody();
            } catch (AlipayApiException ex) {
                result = "request alipay has error";
                ex.printStackTrace();
            }

            return result;
        }

        /**
         * 支付宝完成【同步】回调页面（不可信回调）
         *
         * @return java.lang.String
         */
        @RequestMapping("/return")
        public String alipayreturn(HttpServletRequest request, Model model)
                throws UnsupportedEncodingException {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> handleParams = AlipayUtil.handleParams(parameterMap);

            // 这里的校验没有多大的意思，不可信，直接获取out_trade_no跳转到对应的payedController也可以
            boolean signVerified = AlipayUtil.rsaCheck(handleParams);

            // 存放信息
            //    Map<String, String> maps = new HashMap<>(6);

            // 验证成功
            if (signVerified) {

                // 商户订单号
                String out_trade_no =
                        new String(request.getParameter("out_trade_no").getBytes("ISO_8859_1"), "UTF-8");

                // 支付宝交易号
                String trade_no =
                        new String(request.getParameter("trade_no").getBytes("ISO_8859_1"), "UTF-8");
                String total_amount =
                        new String(request.getParameter("total_amount").getBytes("ISO_8859_1"), "UTF-8");

                model.addAttribute("out_trade_no", out_trade_no);
                model.addAttribute("trade_no", trade_no);
                model.addAttribute("total_amount", total_amount);
                model.addAttribute("return_msg", "success");
            } else {
                model.addAttribute("return_msg", "fail");
            }
            return "success";
        }

        /**
         * 支付宝完成【异步】回调页面（可信回调）
         *
         * @return java.lang.String
         */
        @RequestMapping("/notify")
        @ResponseBody
        public String notifyNotice(HttpServletRequest request) throws UnsupportedEncodingException {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> handleParams = AlipayUtil.handleParams(parameterMap);

            // 这里的校验没有多大的意思，不可信，直接获取out_trade_no跳转到对应的payedController也可以
            boolean signVerified = AlipayUtil.rsaCheck(handleParams);

            // 存放信息
            Map<String, String> maps = new HashMap<>(6);

            // 验证成功
            if (signVerified) {
                // 商户订单号
                String out_trade_no =
                        new String(request.getParameter("out_trade_no").getBytes("ISO_8859_1"), "UTF-8");

                // 支付宝交易号
                String trade_no =
                        new String(request.getParameter("trade_no").getBytes("ISO_8859_1"), "UTF-8");

                // 付款金额
                String total_amount =
                        new String(request.getParameter("total_amount").getBytes("ISO_8859_1"), "UTF-8");

                maps.put("out_trade_no", out_trade_no);
                maps.put("trade_no", trade_no);
                maps.put("total_amount", total_amount);
                maps.put("notify_msg", "success");
            } else {
                maps.put("notify_msg", "fail");
            }
            return JSON.toJSONString(maps);
        }

        @RequestMapping("/su")
        public String su() {
            return "success";
        }
    }


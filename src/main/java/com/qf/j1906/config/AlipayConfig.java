package com.qf.j1906.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101600696748 ";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key =
            "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";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqf9MBxUY6iLVTFRAjXLmYE+ZgySyWlVq+hzlkK/gunBJv1rQCTsNA+8raRPmvV+pPCA4Twcn+VxTW+2Zf6dGU7kZyVvcWB3A1MPXjtWQeXgIwVgqADVn1BKrv5+XomzYILK8iX/l6JVBgd239+O8Els03oS6IxaWd7bvAI3VV0j8Wndj06U6QPd5sAS0h38uQabpnf0o0cghnal+lGHwbM47HXvFFzgU/keQqW0spLdrMoyFGHxGsJPoOGKS6vehADD6RgaPKvHm7YmNbV1+bxeIidufUI3LUi98VHqmts3Ayy9joTsskFHEUdEuFhvRMX0dA/u8GUt2KIFosz5sywIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/alipay/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8080/alipay/return";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "UTF-8";

    // 返回格式
    public static String format = "json";

    /** 支付宝网关 - 注：沙箱使用 alipaydev , 非 alipay */
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = System.getProperty("user.dir") + "/logs/alipay.log";

    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

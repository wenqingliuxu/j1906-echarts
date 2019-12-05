package com.qf.j1906.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.qf.j1906.config.AlipayConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 刘旭
 * Date: 2019/12/5 20:39
 * @Version 1.0
 */
public class AlipayUtil {
    private static Logger logger = LogManager.getLogger(AlipayUtil.class);

    /**
     * 请求处理参数
     *
     * @auther
     * @param requestParams
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @date 2019/11/9 11:28
     */
    public static Map<String, String> handleParams(Map<String, String[]> requestParams) {
        Map<String, String> handleMap = new HashMap<>(requestParams.size());
        requestParams.forEach((key, value) -> handleMap.put(key, join(value, ",")));
        return handleMap;
    }

    /**
     * 数组转字符串
     *
     * @param os
     * @param splitString
     * @return
     */
    public static String join(Object[] os, String splitString) {
        String s = "";
        if (os != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < os.length; i++) {
                stringBuffer.append(os[i]).append(splitString);
            }
            s = stringBuffer.deleteCharAt(stringBuffer.length() - 1).toString();
        }
        return s;
    }

    public static boolean rsaCheck(Map<String, String> handleParams) {
        boolean checkV1 = false;
        try {
            checkV1 =
                    AlipaySignature.rsaCheckV1(
                            handleParams,
                            AlipayConfig.alipay_public_key,
                            AlipayConfig.charset,
                            AlipayConfig.sign_type);

        } catch (AlipayApiException ex) {
            ex.printStackTrace();
        }
        return checkV1;
    }

    /** 单例模式 */
    private static class SingletonHolder {
        private static final AlipayUtil INSTANCE = new AlipayUtil();
    }

    public static AlipayUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /** 雪花算法生成ID，自带时间排序，一秒可以生成25个左右 */
    /** 时间起始标记点，作为基础，一般取系统的最近时间（一旦确定不能变动） */
    private static final long twepoch = 1288834974657L;
    /** 机器标识位数 */
    private static final long workerIdBits = 5L;
    /** 数据中心Id最大值 */
    private static final long datacenterIdBits = 5L;
    /** 机器Id最大值 */
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /** 数据中心Id最大值 */
    private static final long maxDatacenterId = -1L & (-1L << datacenterIdBits);
    /** 毫秒内自增位 */
    private static final long sequenceBits = 12L;
    /** 机器Id偏左移12位 */
    private static final long workerIdShift = sequenceBits;
    /** 数据中心Id左移17位 */
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    /** 时间毫秒左移22位 */
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /** 序列掩码 */
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);
    /** 上次生产Id的时间戳 */
    private static long lastTimestamp = -1L;
    /** 并发控制 */
    private long sequence = 0L;
    /** 工作Id */
    private final long workerId;
    /** 数据标识Id部分 */
    private final long datacenterId;

    public AlipayUtil() {
        this.datacenterId = getDatacenterId(maxDatacenterId);
        this.workerId = getMaxWorkerId(datacenterId, maxWorkerId);
    }

    public AlipayUtil(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    /**
     * 获取下一个Id
     *
     * @return long
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则加一
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒计数满了，等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // Id偏移组合生成的最终Id，返回Id
        long nextId =
                ((timestamp - twepoch) << timestampLeftShift)
                        | (datacenterId << datacenterIdShift)
                        | (workerId << workerIdShift)
                        | sequence;

        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取maxWorkerId
     *
     * @return long
     * @date 2019/11/12 17:48
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            mpid.append(name.split("@")[0]);
        }
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 数据标识Id部分
     *
     * @return
     * @date 2019/11/12 17:51
     */
    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id =
                        (0x000000FF & (long) mac[mac.length - 1])
                                | (0x0000FF00 & (((long) mac[mac.length - 2] << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }
}

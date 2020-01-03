package com.github.rxyor.carp.auth.common.util;

import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * TokenUtil
 * </p>
 *
 * @author liuyang
 * @date 2018/12/8 Sat 08:50:00
 * @since 1.0.0
 */
public class BuildSignatureUtil {

    private static final Logger LOG = LoggerFactory.getLogger(BuildSignatureUtil.class);

    /**
     * 时间戳保留位数
     */
    private static final int TIME_SCALE = 8;
    /**
     * uuid 64bit划分8组每组8bit
     */
    private static final int COMPRESS_BIT = 8;
    /**
     * 签名长度
     */
    private static final int SIGNATURE_LEN = 16;
    /**
     * uuid部分长度
     */
    private static final int UUID_LEN = 8;
    /**
     * 时间戳部分长度
     */
    private static final int TIMESTAMP_LEN = 8;
    /**
     * 签名最多有效30天(mills)
     */
    private static final int SIGNATURE_VALID_DAY = 30;

    private BuildSignatureUtil() {
    }

    /**
     * @author liuyang
     * @date 2018-12-08 Sat 14:39:33
     */
    public static String createSignature() {
        return generateSignature();
    }

    public static String createSignature(String uuid) {
        return generateSignature(uuid);
    }

    /**
     * 签名校验
     *
     * @param signature 签名
     */
    public static boolean verifySignature(String signature) {
        try {
            return checkLength(signature) && checkChars(signature) && checkTime(signature);
        } catch (Exception e) {
            LOG.error("签名校验时出错，可能含有非法字符：", e);
        }
        return false;
    }

    /**
     * 获取签名生成时间
     *
     * @param signature 签名
     */
    public static long getSignatureTime(String signature) {
        if (!checkLength(signature)) {
            throw new RuntimeException("签名长度不合法");
        }
        String timestamp = signature.substring(TIMESTAMP_LEN, SIGNATURE_LEN);
        return ModUtil.mod64ToDec(timestamp);
    }

    /**
     * 签名组成为：8为uuid压缩字符+8位linux时间戳64位字符表示
     */
    private static String generateSignature() {
        return generateSignature(UUID.randomUUID());
    }

    private static String generateSignature(String uuid) {
        return generateSignature(UUID.fromString(uuid));
    }

    private static String generateSignature(UUID uuid) {
        return compressUuid(uuid) + timeToMod64(new Date());
    }

    /**
     * linux时间戳截取8位
     */
    private static String timeToMod64(Date date) {
        String s = ModUtil.decToMod64(date.getTime());
        return round(s, ModUtil.MOD64_DIGIT[0], TIME_SCALE);
    }

    /**
     * uuid截取8位
     */
    private static String compressUuid() {
        return compress(UUID.randomUUID().getLeastSignificantBits(), COMPRESS_BIT);
    }

    private static String compressUuid(UUID uuid) {
        return compress(uuid.getLeastSignificantBits(), COMPRESS_BIT);
    }

    /**
     * 将long分成8组，每组是一个8位的数字，进行64个字符取模压缩
     *
     * @param x 源值
     * @param bit 每组有几bit
     * @author liuyang
     * @date 2018-12-08 Sat 12:35:10
     */
    private static String compress(long x, int bit) {
        return ModUtil.toString(x, bit, ModUtil.MOD64_DIGIT);
    }

    /**
     * 将字符串保留至指定长度（头部多截少补）
     *
     * @param source 源字符串
     * @param prefix 前缀
     * @param scale 保留位数
     */
    private static String round(String source, char prefix, int scale) {
        //还需要补多少位
        int lack = 0;
        if (null == source || source.length() == 0) {
            lack = scale;
        } else {
            lack = scale - source.length();
        }

        if (lack > 0) {
            StringBuilder sb = new StringBuilder();
            while (lack-- > 0) {
                sb.append(prefix);
            }
            source = sb.append(source).toString();
        } else if (lack < 0) {
            source = source.substring(0, -lack);
        }

        return source;
    }

    /**
     * 校验长度
     *
     * @param signature 签名
     */
    private static boolean checkLength(String signature) {
        return signature != null && signature.length() == SIGNATURE_LEN;
    }

    /**
     * 校验字符是否都合法
     *
     * @param signature 签名
     */
    private static boolean checkChars(String signature) {
        char[] chars = signature.toCharArray();
        for (char c : chars) {
            Integer index = ModUtil.indexOf(c, ModUtil.MOD64_DIGIT);
            if (index == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验时间
     *
     * @param signature 签名
     */
    private static boolean checkTime(String signature) {
        String timestamp = signature.substring(TIMESTAMP_LEN, SIGNATURE_LEN);
        long signatureTime = ModUtil.mod64ToDec(timestamp);
        Date now = new Date();
        //时钟的误差时间控制在1个小时
        return (signatureTime >= now.getTime() - DateUtil.getDayTime(SIGNATURE_VALID_DAY))
            && (signatureTime <= now.getTime() + DateUtil.getHourTime(1));
    }

}

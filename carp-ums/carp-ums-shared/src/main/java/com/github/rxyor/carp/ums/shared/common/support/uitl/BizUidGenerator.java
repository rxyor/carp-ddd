package com.github.rxyor.carp.ums.shared.common.support.uitl;

import com.github.rxyor.common.util.lang2.RadixUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/10/12 周六 11:16:00
 * @since 1.0.0
 */
public class BizUidGenerator {

    /**
     * 0~9,A~Z，去除0、O、I、L 4个易混淆的字符还是32位
     */
    public final static char[] FRIENDLY_DIGITS = {
        '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
        'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M',
        'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z'};

    public final static char[] DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z',};

    private BizUidGenerator() {
    }


    /**
     *<p>
     *生成订单编号
     *</p>
     *
     * @author liuyang
     * @date 2019-12-13 周五 15:05:50
     * @return
     */
    public static String nextUid() {
        final int len = 16;
        String uuid = nextUid(DIGITS);
        String uid = append(DIGITS, uuid, 16, 2);
        if (uid.length() < len) {
            return uid;
        }
        return uid;
    }

    /**
     *<p>
     * 追加随机字符串到指定长度
     *</p>
     *
     * @author liuyang
     * @date 2019-12-13 周五 15:07:30
     * @param digits 字符编码数组
     * @param code 源编码
     * @param totalLen 总长度
     * @param randomHeadLen 头部追加的随机字符个数
     * @return
     */
    private static String append(final char[] digits, String code, final int totalLen, final int randomHeadLen) {
        Preconditions.checkArgument(StringUtils.isNotBlank(code), "input string can't be blank");
        Preconditions.checkArgument(digits != null && digits.length >= 2,
            "digits must contain 2 chars at least");

        if (totalLen <= 0 || totalLen <= code.length()) {
            return code;
        }
        final int remainLen = totalLen - code.length();
        if (remainLen <= 0) {
            return code;
        }

        final int digitLen = digits.length;
        StringBuilder randomCodes = new StringBuilder();
        for (int i = 0; i < remainLen; i++) {
            int random = RandomUtils.nextInt(0, digitLen - 1);
            randomCodes.append(digits[random]);
        }

        if (randomHeadLen >= randomCodes.length()) {
            return randomCodes.append(code).toString();
        } else {
            return randomCodes.subSequence(0, randomHeadLen) + code
                + randomCodes.subSequence(randomHeadLen, randomCodes.length());
        }
    }

    /**
     *<p>
     * 使用snowflake生成一个long uid, 并根据传入的编码字符转换字符串
     *</p>
     *
     * @author liuyang
     * @date 2019-12-13 周五 15:08:55
     * @param digits digits
     * @return
     */
    private static String nextUid(char[] digits) {
        Preconditions.checkArgument(digits != null && digits.length >= 2,
            "digits must contain 2 chars at least");

        final int decimal = digits.length;
        Long uid = DistributedUidHelper.nextUID();
        return RadixUtil.builder().digits(digits).build().convert2String(uid, decimal);
    }
}

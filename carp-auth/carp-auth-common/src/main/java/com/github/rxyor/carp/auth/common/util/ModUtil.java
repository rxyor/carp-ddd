package com.github.rxyor.carp.auth.common.util;

import java.util.Objects;

/**
 *<p>
 *取模工具
 *</p>
 *
 * @author liuyang
 * @date 2018/12/8 Sat 13:13:00
 * @since 1.0.0
 */
public class ModUtil {

    public static final char[] HEX_DIGIT = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
    };

    public static final char[] MOD64_DIGIT = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
        'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
        'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
        'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z', '_', '!'
    };

    public static String decToHex(long x) {
        //16=2^4
        return toString(x, 4, HEX_DIGIT);
    }

    public static long hexToDec(String s) {
        Objects.requireNonNull(s, "hexToDec: 参数不能是null");
        return toLong(s.toLowerCase(), 4, HEX_DIGIT);
    }

    public static String decToMod64(long x) {
        //16=2^4
        return toString(x, 6, MOD64_DIGIT);
    }

    public static long mod64ToDec(String s) {
        Objects.requireNonNull(s, "mod64ToDec: 参数不能是null");
        return toLong(s, 6, MOD64_DIGIT);
    }

    /**
     *分组拆分
     *
     * @author liuyang
     * @date 2018-12-08 Sat 12:35:10
     * @param x 源值
     * @param bit 进制的bit位数，8进制是3，16进制是4
     * @param digit 表示符索引表
     */
    public static String toString(long x, int bit, char[] digit) {
        x = (x < 0) ? -x : x;
        StringBuilder sb = new StringBuilder();
        do {
            //取模
            int index = (int) ((x & ((1 << bit) - 1)) % (long) digit.length);
            sb.append(digit[index]);
            x >>= bit;
        } while (x > 0);
        return sb.reverse().toString();
    }

    /**
     *转换为10进制
     *
     * @author liuyang
     * @date 2018-12-08 Sat 13:45:37
     * @param s 字符串
     * @param bit 进制的bit位数，8进制是3，16进制是4
     * @param digit 表示符索引表
     */
    public static long toLong(String s, int bit, char[] digit) {
        //字符串反转，将低位放到前边
        char[] chars = (new StringBuilder(s)).reverse().toString().toCharArray();
        long result = 0L;
        for (int i = 0; i < chars.length; i++) {
            //获取字符对应索引位置
            Integer index = indexOf(chars[i], digit);
            if (index == null) {
                throw new NumberFormatException("toLong: 参数含有非法的字符");
            }
            //index转为long型，防止溢出
            long cast = index;
            //高位要左移bit*i位
            result |= cast <<= (bit * i);
        }
        return result;
    }

    public static Integer indexOf(char c, char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == c) {
                return i;
            }
        }
        return null;
    }

}

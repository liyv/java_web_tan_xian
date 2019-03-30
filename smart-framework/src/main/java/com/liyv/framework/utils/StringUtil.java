package com.liyv.framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 */
public class StringUtil {
    //字符串分隔符
    public static final String SEPARATOR = String.valueOf((char) 29);

    public static boolean isEmpty(String str) {
        if (null != str) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}

package com.liyv.framework.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 */
public final class ArrayUtil {
    //判断数组是否非空
    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}


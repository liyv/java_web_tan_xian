package com.liyv.framework;

import com.liyv.framework.helper.*;
import com.liyv.framework.utils.ClassUtil;

public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (int i = 0; i < classList.length; i++) {
            ClassUtil.loadClass(classList[i].getName(), false);
        }
    }
}

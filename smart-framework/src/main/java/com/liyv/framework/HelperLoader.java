package com.liyv.framework;

import com.liyv.framework.helper.BeanHelper;
import com.liyv.framework.helper.ClassHelper;
import com.liyv.framework.helper.ControllerHelper;
import com.liyv.framework.helper.IocHelper;
import com.liyv.framework.utils.ClassUtil;

public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (int i = 0; i < classList.length; i++) {
            ClassUtil.loadClass(classList[i].getName(), false);
        }
    }
}

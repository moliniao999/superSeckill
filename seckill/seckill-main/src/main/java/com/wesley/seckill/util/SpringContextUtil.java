package com.wesley.seckill.util;

import org.springframework.context.ApplicationContext;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author weili
 * @create 2017年11月23日下午3:57
 * @see
 *      </P>
 */
public class SpringContextUtil {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static Object getBean(Class<?> classType) {
        return applicationContext.getBean(classType);
    }
}

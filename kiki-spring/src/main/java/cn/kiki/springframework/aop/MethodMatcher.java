package cn.kiki.springframework.aop;

import java.lang.reflect.Method;

/**
 * @Author hui cao
 * @Description: 方法匹配，找到表达式范围内匹配下的目标类和方法
 */
public interface MethodMatcher {

    /**
     * 执行静态检查给定方法是否匹配
     * @param method
     * @param targetClass
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);
}

package cn.kiki.springframework.aop;

/**
 * @Author hui cao
 * @Description:
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}

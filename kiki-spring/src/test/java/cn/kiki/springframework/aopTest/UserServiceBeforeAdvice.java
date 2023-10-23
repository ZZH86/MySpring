package cn.kiki.springframework.aopTest;

import cn.kiki.springframework.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * @Author hui cao
 * @Description:
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法：" + method.getName());
    }
}

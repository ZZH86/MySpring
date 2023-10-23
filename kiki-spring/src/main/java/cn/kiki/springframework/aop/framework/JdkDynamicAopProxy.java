package cn.kiki.springframework.aop.framework;

import cn.kiki.springframework.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author hui cao
 * @Description:
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), advisedSupport.getTargetSource().getTargetClass(), this);
    }

    /**
     * 代理对象的方法调用方法。首先，它检查代理对象中的方法是否与目标对象中的方法匹配。
     * 如果匹配，它将使用AdvisedSupport对象中的MethodInterceptor来调用方法。
     * 如果不匹配，它将直接调用目标对象的方法。
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1. 检查代理对象中的方法是否与目标对象中的方法匹配。
        if(advisedSupport.getMethodMatcher().matches(method, advisedSupport.getTargetSource().getTarget().getClass())){
            // 2. 如果匹配，获取AdvisedSupport对象中的MethodInterceptor。
            MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
            // 3. 使用MethodInterceptor来调用方法，传入一个ReflectiveMethodInvocation对象作为参数。
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, args));
        }
        // 4. 如果不匹配，直接调用目标对象的方法，并传入参数。
        return method.invoke(advisedSupport.getTargetSource().getTarget(), args);
    }
}

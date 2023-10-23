package cn.kiki.springframework.aop.framework;

import cn.kiki.springframework.aop.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author hui cao
 * @Description: 基于 Cglib 使用 Enhancer 代理的类可以在运行期间为接口使用底层 ASM 字节码增强技术处理对象的代理对象生成，
 *     因此被代理类不需要实现任何接口
 */
public class Cglib2AopProxy implements AopProxy{

    private final AdvisedSupport advisedSupport;

    public Cglib2AopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        // 1. 创建一个Enhancer对象。
        Enhancer enhancer = new Enhancer();

        // 2. 设置代理对象的父类为目标对象的类。
        enhancer.setSuperclass(advisedSupport.getTargetSource().getTarget().getClass());

        // 3. 设置代理对象实现的接口为目标对象的类。
        enhancer.setInterfaces(advisedSupport.getTargetSource().getTargetClass());

        // 4. 创建一个DynamicAdvisedInterceptor对象，并将其设置为代理对象的回调函数。
        enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport));

        // 5. 调用enhancer.create()方法来创建并返回代理对象。
        return enhancer.create();
    }

    // 设置动态代理的回调对象
    private static class  DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advisedSupport;

        private DynamicAdvisedInterceptor(AdvisedSupport advisedSupport) {
            this.advisedSupport = advisedSupport;
        }

        /**
         * 一个拦截器的实现方法。该方法的作用是在目标方法执行之前和之后执行一些额外的逻辑
         */
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            // 1. 创建一个CglibMethodInvocation对象，传入目标对象、目标方法、参数和方法代理对象。
            CglibMethodInvocation cglibMethodInvocation = new CglibMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, objects, methodProxy);
            // 2. 检查目标方法是否匹配方法匹配器。如果匹配，则执行下一步，否则执行最后一步。
            if(advisedSupport.getMethodMatcher().matches(method, advisedSupport.getTargetSource().getTarget().getClass())){
                // 3. 调用方法拦截器的invoke方法，传入CglibMethodInvocation对象作为参数，并返回结果。
                return advisedSupport.getMethodInterceptor().invoke(cglibMethodInvocation);
            }
            // 4. 如果目标方法不匹配方法匹配器，则调用CglibMethodInvocation对象的proceed方法，继续执行目标方法。
            return cglibMethodInvocation.proceed();
        }
    }

    /**
     * 用 cglib 方式实现反射方法
     */
    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.target, this.arguments);
        }

    }
}

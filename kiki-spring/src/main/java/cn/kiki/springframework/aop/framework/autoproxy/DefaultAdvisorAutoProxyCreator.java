package cn.kiki.springframework.aop.framework.autoproxy;

import cn.kiki.springframework.aop.*;
import cn.kiki.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import cn.kiki.springframework.aop.framework.ProxyFactory;
import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.PropertyValues;
import cn.kiki.springframework.beans.factory.BeanFactory;
import cn.kiki.springframework.beans.factory.BeanFactoryAware;
import cn.kiki.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.kiki.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * @Author hui cao
 * @Description:
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    /**
     * 判断是否是基础类
     * @param beanClass
     * @return
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    // 把创建 AOP 代理的操作从 postProcessBeforeInstantiation 移动到 postProcessAfterInitialization 中去
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isInfrastructureClass(bean.getClass())) return bean;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 过滤匹配类
            if (!classFilter.matches(bean.getClass())) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            // 返回代理对象
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;

    }


}

package cn.kiki.springframework.beans.factory.config;

import cn.kiki.springframework.beans.BeansException;

/**
 * @Author hui cao
 * @Description: 提供修改新实例化 Bean 对象的扩展点 在Bean实例化之后，修改 Bean 属性信息
 */
public interface BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}

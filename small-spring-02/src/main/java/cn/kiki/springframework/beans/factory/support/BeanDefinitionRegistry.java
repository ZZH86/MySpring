package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.factory.config.BeanDefinition;

/**
 * @Author hui cao
 * @Description: 根据 bean 定义类进行注册
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注册 BeanDefinition
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}

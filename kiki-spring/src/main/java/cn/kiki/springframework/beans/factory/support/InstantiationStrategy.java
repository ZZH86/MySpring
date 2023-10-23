package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @Author hui cao
 * @Description: 实例化策略接口
 */
public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;
}

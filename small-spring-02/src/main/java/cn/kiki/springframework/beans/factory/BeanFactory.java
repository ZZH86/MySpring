package cn.kiki.springframework.beans.factory;

import cn.kiki.springframework.beans.BeansException;

/**
 * @Author hui cao
 * @Description: bean 工厂类接口
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    /**
     * 按照类型获取 Bean
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;
}


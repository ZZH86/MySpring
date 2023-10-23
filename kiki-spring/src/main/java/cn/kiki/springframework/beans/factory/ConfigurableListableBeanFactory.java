package cn.kiki.springframework.beans.factory;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.config.AutowireCapableBeanFactory;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;
import cn.kiki.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @Author hui cao
 * @Description: 提供分析和修改 Bean 以及预先实例化的操作接口
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 获取 beanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 预先实例化
     */
    void preInstantiateSingletons() throws BeansException;
}

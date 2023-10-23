package cn.kiki.springframework.common;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.PropertyValue;
import cn.kiki.springframework.beans.PropertyValues;
import cn.kiki.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;
import cn.kiki.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.kiki.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @Author hui cao
 * @Description:
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService2");

        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "改为：阿里巴巴"));
    }
}

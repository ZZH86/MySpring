package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.BeanFactory;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;

/**
 * @Author hui cao
 * @Description: 抽象类定义模板方法
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /**
     * 根据名称获得 bean 实例化对象
     * @param name
     * @return
     */
    @Override
    public Object getBean(String name) throws BeansException{
        Object bean = getSingleton(name);
        if(bean != null){
            return bean;
        }
        return createBean(name,getBeanDefinition(name));
    }

    /**
     * 根据名称获得 bean 的定义类
     * @param name
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

    /**
     * 创建 bean 实例对象
     * @param name
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition) throws BeansException;
}

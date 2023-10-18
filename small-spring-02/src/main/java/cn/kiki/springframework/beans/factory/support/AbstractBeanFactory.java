package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.BeanFactory;
import cn.kiki.springframework.beans.factory.FactoryBean;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;
import cn.kiki.springframework.beans.factory.config.BeanPostProcessor;
import cn.kiki.springframework.beans.factory.config.ConfigurableBeanFactory;
import cn.kiki.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hui cao
 * @Description: 抽象类定义模板方法
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    //后置处理器容器
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    /**
     * ClassLoader to resolve bean class names with, if necessary
     */
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /**
     * 根据名称获得 bean 实例化对象
     * @param name
     * @return
     */
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        Object bean = getSingleton(name);
        if (bean != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectForBeanInstance(bean, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object beanInstance = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(beanInstance, name);
    }

    private Object getObjectForBeanInstance(Object bean, String name) {
        // 如果不是 factoryBean ,就直接返回
        if(!(bean instanceof FactoryBean)){
            return bean;
        }

        Object object = getCachedObjectForFactoryBean(name);

        if(object == null){
            FactoryBean<?> factoryBean = (FactoryBean<?>) bean;
            object = getObjectFromFactoryBean(factoryBean, name);
        }

        return object;
    }

    /**
     * 根据名称获得 bean 的定义类
     */
    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

    /**
     * 创建 bean 实例对象
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }


}

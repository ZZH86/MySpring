package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.core.io.DefaultResourceLoader;
import cn.kiki.springframework.core.io.ResourceLoader;

/**
 * @Author hui cao
 * @Description: Bean 定义抽象类实现
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.registry = beanDefinitionRegistry;
        this.resourceLoader = new DefaultResourceLoader();
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}

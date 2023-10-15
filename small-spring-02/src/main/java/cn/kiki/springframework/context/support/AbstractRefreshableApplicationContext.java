package cn.kiki.springframework.context.support;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.kiki.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @Author hui cao
 * @Description: 抽象类实现获取Bean工厂和加载资源
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory beanFactory;
    @Override
    protected void refreshBeanFactory() throws BeansException {

        // 1. 创建 bean 工厂
        DefaultListableBeanFactory beanFactory = createBeanFactory();

        // 2. 从配置文件中加载类到工厂
        loadBeanDefinitions(beanFactory);

        // 3. 更新 beanFactory
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return (ConfigurableListableBeanFactory) beanFactory;
    }
}

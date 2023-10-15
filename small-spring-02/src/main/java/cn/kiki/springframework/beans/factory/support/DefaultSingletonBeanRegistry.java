package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.factory.config.BeanPostProcessor;
import cn.kiki.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author hui cao
 * @Description:
 */
public abstract class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final ConcurrentHashMap<String,Object> concurrentHashMap= new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return concurrentHashMap.get(beanName);
    }

    /**
     * 增加单例对象
     * @param beanName
     * @param singletonObject
     */
    protected void addSingleton(String beanName, Object singletonObject){
        concurrentHashMap.put(beanName,singletonObject);
    }

    public abstract void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}

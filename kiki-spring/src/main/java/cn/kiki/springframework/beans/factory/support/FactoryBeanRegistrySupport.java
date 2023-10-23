package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.FactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author hui cao
 * @Description: FactoryBean 注册服务
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{

    //存储 factoryBean 对象
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 从 factoryBean 容器中获取 factoryBean 对象
     * @param beanName
     * @return
     */
    protected Object getCachedObjectForFactoryBean(String beanName){
        Object o = this.factoryBeanObjectCache.get(beanName);
        return (o != NULL_OBJECT ? o : null);
    }

    /**
     * 从 factoryBean 容器中获取 factoryBean 对象，没有就新建
     * @param factoryBean
     * @param beanName
     * @return
     */
    protected Object getObjectFromFactoryBean(FactoryBean factoryBean,String beanName){
        // 如果 factoryBean 是单例，就先从缓存中查找，为空就创建并把对象放进容器，否则返回对象
        // 如果 factoryBean 是原型，就直接返回新创建的对象
        if(factoryBean.isSingleton()){
            Object object = this.factoryBeanObjectCache.get(beanName);
            if( object == null){
                object = doGetObjectFromFactoryBean(factoryBean,beanName);
                this.factoryBeanObjectCache.put(beanName,(object != null ? object : NULL_OBJECT));
            }
            return (object != NULL_OBJECT ? object : null);
        }else {
            return doGetObjectFromFactoryBean(factoryBean,beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(FactoryBean factoryBean, String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}

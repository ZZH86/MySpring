package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.DisposableBean;
import cn.kiki.springframework.beans.factory.config.BeanPostProcessor;
import cn.kiki.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author hui cao
 * @Description:
 */
public abstract class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final ConcurrentHashMap<String,Object> concurrentHashMap= new ConcurrentHashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return concurrentHashMap.get(beanName);
    }

    /**
     * 增加单例对象
     */
    protected void addSingleton(String beanName, Object singletonObject){
        concurrentHashMap.put(beanName,singletonObject);
    }

    public abstract void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons(){
        Set<String> strings = disposableBeans.keySet();
        Object[] disposableBeanNames = strings.toArray();
        for (Object disposableBeanName : disposableBeanNames) {
            DisposableBean disposableBean = disposableBeans.remove(disposableBeanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + disposableBeanName + "' threw an exception", e);
            }
        }
    }
}

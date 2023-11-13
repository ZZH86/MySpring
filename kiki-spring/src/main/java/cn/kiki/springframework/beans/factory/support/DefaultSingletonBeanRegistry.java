package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.DisposableBean;
import cn.kiki.springframework.beans.factory.ObjectFactory;
import cn.kiki.springframework.beans.factory.config.BeanPostProcessor;
import cn.kiki.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author hui cao
 * @Description:
 */
public abstract class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();

    // 一级缓存，普通对象
    private final ConcurrentHashMap<String,Object> concurrentHashMap= new ConcurrentHashMap<>();

    // 二级缓存，提前暴漏对象，没有完全实例化的对象
    protected final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>();

    // 三级缓存，存放代理对象
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>();

    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        // 双检加锁实现单例
         Object singletonObject = concurrentHashMap.get(beanName);
        if(null == singletonObject){
            synchronized(this){
                singletonObject = concurrentHashMap.get(beanName);
                if(null == singletonObject){
                    singletonObject = earlySingletonObjects.get(beanName);
                    // 判断二级缓存中是否有对象，这个对象就是代理对象，因为只有代理对象才会放到三级缓存中
                    if(null == singletonObject){
                        ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                        if(singletonFactory != null){
                            singletonObject = singletonFactory.getObject();
                            // 把三级缓存中的代理对象中的真实对象获取出来，放入二级缓存中
                            earlySingletonObjects.put(beanName, singletonObject);
                            singletonFactories.remove(beanName);
                        }
                    }
                }
            }
        }

        return singletonObject;
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        if (!this.concurrentHashMap.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    /**
     * 增加单例对象
     */
//    protected void addSingleton(String beanName, Object singletonObject){
//        concurrentHashMap.put(beanName,singletonObject);
//    }

    /**
     * 增加单例对象
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        concurrentHashMap.put(beanName,singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    //    public abstract void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

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

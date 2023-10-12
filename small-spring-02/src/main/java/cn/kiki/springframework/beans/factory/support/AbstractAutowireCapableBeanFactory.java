package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @Author hui cao
 * @Description: 策略调用
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition,Object[] args) throws BeansException {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition, name, args);
        }catch (Exception e){
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleton(name, bean);
        return bean;
    }

    private Object createBeanInstance(BeanDefinition beanDefinition, String name, Object[] args) {
        // 目标使用的构造函数
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();

        // 获取一个类的所有声明的构造函数
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> ctor : declaredConstructors) {
            // 这里对比的方式比较简单，只是一个数量对比，实际 Spring 源码中还需要比对入参类型，否则相同数量不同入参类型的情况，就会抛异常
            if(args != null && ctor.getParameterTypes().length == args.length){
                constructorToUse = ctor;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, name, constructorToUse, args);

    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}

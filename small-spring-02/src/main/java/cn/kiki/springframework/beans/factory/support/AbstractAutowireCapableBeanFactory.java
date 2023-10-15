package cn.kiki.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.PropertyValue;
import cn.kiki.springframework.beans.PropertyValues;
import cn.kiki.springframework.beans.factory.config.AutowireCapableBeanFactory;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;
import cn.kiki.springframework.beans.factory.config.BeanPostProcessor;
import cn.kiki.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @Author hui cao
 * @Description: 策略调用
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    // cglib 实现 Bean 实例化策略
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition,Object[] args) throws BeansException {
        Object bean = null;
        try {
            // 创建 bean 实例
            bean = createBeanInstance(beanDefinition, beanName, args);
            
            // 给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);

            // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        }catch (Exception e){
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleton(beanName, bean);
        return bean;
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 待完成内容：invokeInitMethods(beanName, wrappedBean, beanDefinition);
        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {
    }

    /**
     * Bean 填充属性
     */
    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            // 获得属性值数组
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            PropertyValue[] propertyValuesList = propertyValues.getPropertyValues();

            //遍历属性值数组
            for (PropertyValue propertyValue : propertyValuesList) {

                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                if(value instanceof BeanReference){
                    // A 依赖 B，获取 B 的实例化
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }

                // 属性填充,将给定的值value设置到bean对象的指定属性name上
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values：" + beanName);
        }
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
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, name, constructorToUse, args);

    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if(null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if(null == current) return result;
            result = current;
        }
        return result;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }



}

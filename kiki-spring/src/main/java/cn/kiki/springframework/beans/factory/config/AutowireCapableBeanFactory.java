package cn.kiki.springframework.beans.factory.config;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.BeanFactory;

/**
 * @Author hui cao
 * @Description: 自动化处理Bean工厂配置
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}

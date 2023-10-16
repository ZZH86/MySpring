package cn.kiki.springframework.beans.factory;

import cn.kiki.springframework.beans.BeansException;

/**
 * @Author hui cao
 * @Description: 实现此接口能感知到所属的 BeanFactory
 */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}

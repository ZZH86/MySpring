package cn.kiki.springframework.beans.factory;

import cn.kiki.springframework.beans.BeansException;

import java.util.Map;

/**
 * @Author hui cao
 * @Description: 扩展 Bean 工厂接口的接口
 */
public interface ListableBeanFactory extends BeanFactory{

    /**
     * 按照类型返回 Bean 实例
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();
}

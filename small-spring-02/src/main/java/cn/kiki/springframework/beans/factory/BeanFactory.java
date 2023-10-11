package cn.kiki.springframework.beans.factory;

import cn.kiki.springframework.beans.BeansException;

/**
 * @Author hui cao
 * @Description: bean 工厂类接口
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;
}

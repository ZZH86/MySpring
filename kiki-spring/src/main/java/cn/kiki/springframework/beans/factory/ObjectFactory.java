package cn.kiki.springframework.beans.factory;

import cn.kiki.springframework.beans.BeansException;

/**
 * @Author hui cao
 * @Description:
 */
public interface ObjectFactory<T> {

    T getObject() throws BeansException;

}
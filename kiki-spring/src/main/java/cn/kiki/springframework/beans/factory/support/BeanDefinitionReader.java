package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.core.io.Resource;
import cn.kiki.springframework.core.io.ResourceLoader;

/**
 * @Author hui cao
 * @Description: Bean 定义读取接口
 */
public interface BeanDefinitionReader {

    //类注册
    BeanDefinitionRegistry getRegistry();

    //类加载
    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;
}

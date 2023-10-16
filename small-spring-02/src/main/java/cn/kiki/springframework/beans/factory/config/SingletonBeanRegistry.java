package cn.kiki.springframework.beans.factory.config;

/**
 * @Author hui cao
 * @Description: 单例注册接口
 */
public interface SingletonBeanRegistry {

    /**
     * 获取单例对象
     */
    Object getSingleton(String beanName);

    /**
     * 销毁单例对象
     */
    void destroySingletons();
}

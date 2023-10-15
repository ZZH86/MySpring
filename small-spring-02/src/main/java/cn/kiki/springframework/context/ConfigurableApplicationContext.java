package cn.kiki.springframework.context;

import cn.kiki.springframework.beans.BeansException;

/**
 * @Author hui cao
 * @Description:
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新容器
     *
     * @throws BeansException
     */
    void refresh() throws BeansException;
}

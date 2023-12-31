package cn.kiki.springframework.context;

import cn.kiki.springframework.beans.factory.HierarchicalBeanFactory;
import cn.kiki.springframework.beans.factory.ListableBeanFactory;
import cn.kiki.springframework.core.io.ResourceLoader;

/**
 * @Author hui cao
 * @Description: 定义上下文接口
 *    Central interface to provide configuration for an application.
 *    This is read-only while the application is running, but may be
 *    reloaded if the implementation supports this.
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}

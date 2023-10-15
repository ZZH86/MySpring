package cn.kiki.springframework.beans.factory;

/**
 * @Author hui cao
 * @Description: 在 Spring 源码中它提供了可以获取父类 BeanFactory 方法，属于是一种扩展工厂的层次子接口
 *   Sub-interface implemented by bean factories that can be part of a hierarchy.
 */
public interface HierarchicalBeanFactory extends BeanFactory{
}

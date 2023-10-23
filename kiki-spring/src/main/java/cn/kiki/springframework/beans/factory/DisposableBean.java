package cn.kiki.springframework.beans.factory;

/**
 * @Author hui cao
 * @Description: 在销毁时释放资源的 bean 实现的接口。
 *    BeanFactory 应该在释放缓存的单例时调用 destroy 方法。应用程序上下文应该在关闭时释放其所有单例。
 */
public interface DisposableBean {

    void destroy() throws Exception;
}

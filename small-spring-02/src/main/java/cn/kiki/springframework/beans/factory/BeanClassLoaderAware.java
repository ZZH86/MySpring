package cn.kiki.springframework.beans.factory;

/**
 * @Author hui cao
 * @Description: 实现此接口，既能感知到所属的 ClassLoader
 */
public interface BeanClassLoaderAware extends Aware{

    void setBeanClassLoader(ClassLoader classLoader);
}

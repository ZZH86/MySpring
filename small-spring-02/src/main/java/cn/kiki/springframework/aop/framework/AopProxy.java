package cn.kiki.springframework.aop.framework;

/**
 * @Author hui cao
 * @Description: 定义一个标准接口，用于获取代理类,具体实现代理的方式可以有 JDK 方式，也可以是 Cglib 方式
 */
public interface AopProxy {

    Object getProxy();
}

package cn.kiki.springframework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author hui cao
 * @Description: bean工厂
 */
public class BeanFactory {

    //线程安全的 HashMap 作为 bean 容器
    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 根据名称获得 bean 实例
     * @param name
     * @return
     */
    public Object getBean(String name){
        return beanDefinitionMap.get(name).getBean();
    }

    /**
     * bean 注册
     * @param name
     * @param beanDefinition
     */
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition){
        beanDefinitionMap.put(name, beanDefinition);
    }
}

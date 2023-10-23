package cn.kiki.springframework.beans.factory.support;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author hui cao
 * @Description:
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private final ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null){
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    /**
     * 提前实例化单例Bean对象
     * @throws BeansException
     */
    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> map = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if(type.isAssignableFrom(beanClass)){
                map.put(beanName,getBean(beanName, type));
            }
        });
        return map;
    }

    /**
     * 根据类的类型获取 bean ,遍历所有 beanDefinition，利用 isAssignableFrom 判断，满足就加入，最后判断时候只有唯一一个，
     *   是就利用getBean(beanNames.get(0), requiredType)，否则抛出异常。
     * @param requiredType
     * @return
     * @param <T>
     * @throws BeansException
     */
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class beanClass = entry.getValue().getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }
        if (1 == beanNames.size()) {
            return getBean(beanNames.get(0), requiredType);
        }
        throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
}

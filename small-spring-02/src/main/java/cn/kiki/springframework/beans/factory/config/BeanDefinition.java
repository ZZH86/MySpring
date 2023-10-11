package cn.kiki.springframework.beans.factory.config;

/**
 * @Author hui cao
 * @Description: bean 定义
 */
public class BeanDefinition {
    private Class beanClass;

    public BeanDefinition(Class beanClass){
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }
}

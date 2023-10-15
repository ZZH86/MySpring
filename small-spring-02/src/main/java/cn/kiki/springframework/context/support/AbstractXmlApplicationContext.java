package cn.kiki.springframework.context.support;

import cn.kiki.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.kiki.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Author hui cao
 * @Description: xml配置文件加载应用上下文
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{


    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String[] configLocations = getConfigLocations();
        if (null != configLocations){
            xmlBeanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}

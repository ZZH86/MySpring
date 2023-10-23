package cn.kiki.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.PropertyValue;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;
import cn.kiki.springframework.beans.factory.config.BeanReference;
import cn.kiki.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import cn.kiki.springframework.beans.factory.support.BeanDefinitionRegistry;
import cn.kiki.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import cn.kiki.springframework.core.io.DefaultResourceLoader;
import cn.kiki.springframework.core.io.Resource;
import cn.kiki.springframework.core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author hui cao
 * @Description: 解析XML处理Bean注册
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        } catch (IOException | ClassNotFoundException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    /**
     * 通过输入流进行类定义的加载
     * @param inputStream
     */
    private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {

        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        org.dom4j.Element root = document.getRootElement();

        // 解析 context:component-scan 标签，扫描包中的类并提取相关信息，用于组装 BeanDefinition
        Element componentScan = root.element("component-scan");
        if(null != componentScan){
            String scanPath = componentScan.attributeValue("base-package");
            if(StrUtil.isEmpty(scanPath)){
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            scanPackage(scanPath);
        }


        List<org.dom4j.Element> beanList = root.elements("bean");
        for (Element bean : beanList){

            //解析标签
            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");

            //获取类
            Class<?> clazz = Class.forName(className);
            //根据给定的id和name来确定bean的名称,优先级 id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            //如果name也为空，则使用类名的首字母小写作为bean的名称
            if(StrUtil.isEmpty(beanName)){
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            //创建 beanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(clazz);

            //额外设置到beanDefinition中
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            //设置 beanDefinition 的 scope 属性
            if(StrUtil.isNotEmpty(beanScope)){
                beanDefinition.setScope(beanScope);
            }

            List<Element> propertyList = bean.elements("property");
            //读取 PropertyValue 属性并进行填充
            for (Element property : propertyList) {
                // 解析标签：property
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String ref = property.attributeValue("ref");

                //获得属性值：引入对象、值对象
                //ref 不为 null，则为 bean 属性，否则为值属性
                Object value = StrUtil.isNotEmpty(ref) ? new BeanReference(ref) : attrValue;
                //创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                //添加属性信息到 beanDefinition 的属性值类
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

            //注册 beanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    private void scanPackage(String scanPath){
        String[] paths = StrUtil.splitToArray(scanPath, ',');
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(getRegistry());
        classPathBeanDefinitionScanner.doScan(paths);
    }
}

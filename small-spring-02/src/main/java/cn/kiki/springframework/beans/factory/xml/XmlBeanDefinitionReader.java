package cn.kiki.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.PropertyValue;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;
import cn.kiki.springframework.beans.factory.config.BeanReference;
import cn.kiki.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import cn.kiki.springframework.beans.factory.support.BeanDefinitionRegistry;
import cn.kiki.springframework.core.io.DefaultResourceLoader;
import cn.kiki.springframework.core.io.Resource;
import cn.kiki.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

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
        } catch (IOException | ClassNotFoundException e) {
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
    private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {

        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++){
            Node item = childNodes.item(i);

            //判断元素
            if(!(item instanceof Element)) continue;

            //判断对象
            if(!"bean".equals(item.getNodeName())) continue;

            //解析标签
            Element bean =(Element) item;
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");

            //增加对init-method、destroy-method的读取
            String initMethod = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");

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

            //读取 PropertyValue 属性并进行填充
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                Node node = bean.getChildNodes().item(j);
                //判断是否是元素
                if(!(node instanceof Element)) continue;
                //判断是否是属性
                if(!"property".equals(node.getNodeName())) continue;

                //解析标签
                Element property = (Element) node;
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String ref = property.getAttribute("ref");

                //获得属性值：引入对象、值对象
                //red 不为 null，则为 bean 属性，否则为值属性
                Object value = StrUtil.isNotEmpty(ref) ? new BeanReference(attrName) : attrValue;
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
}

package cn.kiki.springframework;

import cn.hutool.core.io.IoUtil;
import cn.kiki.springframework.bean.*;
import cn.kiki.springframework.beans.PropertyValue;
import cn.kiki.springframework.beans.PropertyValues;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;
import cn.kiki.springframework.beans.factory.config.BeanReference;
import cn.kiki.springframework.beans.factory.support.BeanDefinitionRegistry;
import cn.kiki.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.kiki.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import cn.kiki.springframework.common.MyBeanFactoryPostProcessor;
import cn.kiki.springframework.common.MyBeanPostProcessor;
import cn.kiki.springframework.context.support.ClassPathXmlApplicationContext;
import cn.kiki.springframework.core.io.DefaultResourceLoader;
import cn.kiki.springframework.core.io.Resource;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author hui cao
 * @Description: api测试类
 */
public class ApiTest {

//    @Test
//    public void test_BeanFactory(){
//        //1 初始化 beanFactory
//        BeanFactory beanFactory = new BeanFactory();
//
//        //2 注册bean
//        beanFactory.registerBeanDefinition("userService", new BeanDefinition(new UserService()));
//
//        //3 获取bean
//        UserService userServiceBean = (UserService) beanFactory.getBean("userService");
//
//        //4 调用bean方法
//        userServiceBean.queryUserInfo();
//    }

    @Test
    public void test_BeanFactory(){
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.注册 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 3.第一次获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
        // 4.第二次获取 bean from Singleton
        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
        userService_singleton.queryUserInfo();
    }

    @Test
    public void test_beanFactoryCglib(){
        //1 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //2 注册 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        //3 获取 bean
        UserService userService =(UserService) beanFactory.getBean("userService", "cao hui");
        userService.queryUserInfo();
    }

    @Test
    public void test_BeanFactory_bean() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. UserDao 注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3. UserService 设置属性[uId、userDao]
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10002"));
        propertyValues.addPropertyValue(new PropertyValue("userDao",new BeanReference("userDao")));

        // 4. UserService 注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService1.class, propertyValues);
        beanFactory.registerBeanDefinition("userService1", beanDefinition);

        // 5. UserService 获取bean
        UserService1 userService1 = (UserService1) beanFactory.getBean("userService1");
        userService1.queryUserInfo();
    }

    // 测试资源加载
    private DefaultResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_classpath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_file() throws IOException {
        Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_url() throws IOException {
        // 网络原因可能导致GitHub不能读取，可以放到自己的Gitee仓库。读取后可以从内容中搜索关键字；OLpj9823dZ
        Resource resource = resourceLoader.getResource("https://github.com/fuzhengwei/small-spring/blob/main/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_xml() {
        //1 初始化 beanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        //2 读取配置文件并注册Bean
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);

        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        UserService1 userService1 = (UserService1) defaultListableBeanFactory.getBean("userService1");
        userService1.queryUserInfo();
    }

    /**
     * 不用应用上下文
     */
    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor(){
        // 1. 初始化 beanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件注册 bean
        new XmlBeanDefinitionReader(beanFactory).loadBeanDefinitions("classpath:spring.xml");

        // 3. beanDefinition 加载完成 & Bean实例化之前，修改 BeanDefinition 的属性值
        MyBeanFactoryPostProcessor myBeanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        myBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4. Bean 实例化后修改 bean 属性信息
        MyBeanPostProcessor myBeanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(myBeanPostProcessor);

        // 5. 获取 bean 对象的调用方法
        UserService2 userService2 = beanFactory.getBean("userService2", UserService2.class);
        String s = userService2.queryUserInfo();
        System.out.println(s);
    }

    @Test
    public void test_xml2(){

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService2 userService2 = applicationContext.getBean("userService2", UserService2.class);

        String s = userService2.queryUserInfo();
        System.out.println(s);
    }

    //加入初始化和销毁方法
    @Test
    public void test_xml3() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2. 获取Bean对象调用方法
        UserService5 userService = applicationContext.getBean("userService5", UserService5.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }


}

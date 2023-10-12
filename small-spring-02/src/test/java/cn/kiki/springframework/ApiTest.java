package cn.kiki.springframework;

import cn.kiki.springframework.bean.UserService;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;
import cn.kiki.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

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
}

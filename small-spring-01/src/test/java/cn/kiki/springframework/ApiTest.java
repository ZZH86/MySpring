package cn.kiki.springframework;

import cn.kiki.springframework.bean.UserService;
import org.junit.Test;

/**
 * @Author hui cao
 * @Description: api测试类
 */
public class ApiTest {

    @Test
    public void test_BeanFactory(){
        //1 初始化 beanFactory
        BeanFactory beanFactory = new BeanFactory();

        //2 注册bean
        beanFactory.registerBeanDefinition("userService", new BeanDefinition(new UserService()));

        //3 获取bean
        UserService userServiceBean = (UserService) beanFactory.getBean("userService");

        //4 调用bean方法
        userServiceBean.queryUserInfo();
    }
}

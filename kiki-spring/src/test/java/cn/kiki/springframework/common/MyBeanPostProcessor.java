package cn.kiki.springframework.common;

import cn.kiki.springframework.bean.UserService2;
import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Author hui cao
 * @Description:
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("userService2".equals(beanName)){
            UserService2 userService2 = (UserService2) bean;
            userService2.setLocation("改为：深圳");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}

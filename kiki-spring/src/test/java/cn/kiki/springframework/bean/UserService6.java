package cn.kiki.springframework.bean;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.BeanClassLoaderAware;
import cn.kiki.springframework.beans.factory.BeanFactory;
import cn.kiki.springframework.beans.factory.BeanFactoryAware;
import cn.kiki.springframework.beans.factory.BeanNameAware;
import cn.kiki.springframework.context.ApplicationContext;
import cn.kiki.springframework.context.ApplicationContextAware;

/**
 * @Author hui cao
 * @Description:
 */
public class UserService6 implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    private String uId;
    private String company;
    private String location;
    private UserDao5 userDao5;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("beanFactory 设置成功！");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("Application 设置成功！");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean Name is：" + name);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader：" + classLoader);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao5 getUserDao5() {
        return userDao5;
    }

    public void setUserDao5(UserDao5 userDao5) {
        this.userDao5 = userDao5;
    }

    public String queryUserInfo() {
        return userDao5.queryUserName(uId)+", 公司："+company+", 地点"+location;
    }

}

package cn.kiki.springframework.bean;

import cn.kiki.springframework.beans.factory.DisposableBean;
import cn.kiki.springframework.beans.factory.InitializingBean;

/**
 * @Author hui cao
 * @Description:
 */
public class UserService5 implements InitializingBean, DisposableBean {

    private String uId;
    private String company;
    private String location;
    private UserDao5 userDao5;

    @Override
    public void destroy() throws Exception {
        System.out.println("执行：UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行：UserService.afterPropertiesSet");
    }

    public String queryUserInfo() {
        return userDao5.queryUserName(uId)+", 公司："+company+", 地点"+location;
    }

    // ...get/set

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
}

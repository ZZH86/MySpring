package cn.kiki.springframework.bean;

/**
 * @Author hui cao
 * @Description:
 */
public class UserService2 {

    private String uId;
    private String company;
    private String location;
    private UserDao1 userDao1;

    public String queryUserInfo() {
        return userDao1.queryUserName(uId)+", 公司："+company+", 地点"+location;
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

    public UserDao1 getUserDao1() {
        return userDao1;
    }

    public void setUserDao(UserDao1 userDao1) {
        this.userDao1 = userDao1;
    }

    // ...get/set
}

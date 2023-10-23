package cn.kiki.springframework.bean;

/**
 * @Author hui cao
 * @Description: UserService 类
 */
public class UserService {

    private String name;

    public UserService(String name) {
        this.name = name;
    }

    public void queryUserInfo(){
        System.out.println("查询用户信息" + this.name);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("").append(name);
        return stringBuilder.toString();
    }
}
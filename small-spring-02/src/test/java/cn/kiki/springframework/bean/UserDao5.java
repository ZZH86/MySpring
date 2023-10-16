package cn.kiki.springframework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author hui cao
 * @Description:
 */
public class UserDao5 {

    private static Map<String, String> hashMap = new HashMap<>();

    public void initDataMethod(){
        System.out.println("执行：init-method");
        hashMap.put("10001", "caohui");
        hashMap.put("10002", "lijie");
        hashMap.put("10003", "666");
    }

    public void destroyDataMethod(){
        System.out.println("执行：destroy-method");
        hashMap.clear();
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}
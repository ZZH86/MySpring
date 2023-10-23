package cn.kiki.springframework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author hui cao
 * @Description:
 */
public class UserDao1 {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "caohui");
        hashMap.put("10002", "lijie");
        hashMap.put("10003", "阿毛");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}

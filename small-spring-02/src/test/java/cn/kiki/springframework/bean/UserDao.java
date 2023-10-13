package cn.kiki.springframework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author hui cao
 * @Description:
 */
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "343434");
        hashMap.put("10002", "caohui");
        hashMap.put("10003", "lijie");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

    public UserDao() {
    }
}


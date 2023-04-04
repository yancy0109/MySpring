package com.yancy.springframework.test.bean;

import com.yancy.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "yancy,中国,北京");
        hashMap.put("10002", "nancy,中国,北京");
        hashMap.put("10003", "Ran,中国,北京");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}

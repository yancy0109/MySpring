package com.yancy.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试Dao
 * @author yancy0109
 */
public class UserDao {

    private static Map<String, String> records = new HashMap<>();

    public void initDataMethod() {
        System.out.println("userDao init");
        records.put("10001", "小明");
        records.put("10002", "小白");
        records.put("10003", "小李");
        records.put("10004", "小话");
    }

    public void destroyDataMethod() {
        System.out.println("UserDao destroy");
        records.clear();
    }

    public String queryUserName(String uId) {
        return records.get(uId);
    }
}

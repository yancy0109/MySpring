package com.yancy.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试Dao
 * @author yancy0109
 */
public class UserDao {

    private static Map<String, String> records = new HashMap<>();

    public UserDao() {
        records.put("10001", "小明");
        records.put("10002", "小白");
        records.put("10003", "小李");
        records.put("10004", "小话");
    }

    public String queryUserName(String uId) {
        return records.get(uId);
    }
}

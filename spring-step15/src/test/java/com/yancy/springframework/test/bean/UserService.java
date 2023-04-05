package com.yancy.springframework.test.bean;

import java.util.Random;

/**
 * @author yancy0109
 */
public class UserService implements IUserService {

    private String token;

    @Override
    public String queryUserInfo() {

        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Yancy, 10001, BeiJin" + token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

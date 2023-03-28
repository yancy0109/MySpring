package com.yancy.springframework.test.bean;

import java.util.Random;

/**
 * @author yancy0109
 */
public class UserService implements IUserService {

    @Override
    public String register(String username) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户: " + username + " success! ";
    }

    @Override
    public String queryUserInfo() {

        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "yancy, 100001, China";
    }
}

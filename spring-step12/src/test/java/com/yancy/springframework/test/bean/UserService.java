package com.yancy.springframework.test.bean;

import java.util.Random;

/**
 * 测试类
 * UserService
 * 依赖UuserDao对象
 * @author yancy0109
 */
public class UserService implements IUserService{

    @Override
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "yancy, 100001, Tianjin";
    }

    @Override
    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "注册用户: " + userName + " success";
    }
}

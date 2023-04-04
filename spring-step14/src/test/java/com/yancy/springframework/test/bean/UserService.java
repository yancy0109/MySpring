package com.yancy.springframework.test.bean;


import com.yancy.springframework.beans.factory.annotation.Autowired;
import com.yancy.springframework.beans.factory.annotation.Value;
import com.yancy.springframework.stereotype.Component;

import java.util.Random;

@Component("userService")
public class UserService implements IUserService{

    @Value("sda")
    private String token;

    @Autowired
    private UserDao userDao;

    @Override
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userDao.queryUserName("10001" ) + ", Token" + token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

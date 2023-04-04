package com.yancy.springframework.test.bean;


import com.yancy.springframework.beans.factory.annotation.Autowired;
import com.yancy.springframework.beans.factory.annotation.Value;
import com.yancy.springframework.stereotype.Component;

import java.util.Random;

@Component("userService")
public class UserService implements IUserService{

    @Value("${token}")
    private String token;

    @Autowired
    private UserDao userDao;

    @Override
    public String queryUserInfo(String uId) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userDao.queryUserName("10001" + ', Token ' + token);
    }

    
}

package com.yancy.springframework.test.bean;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.*;
import com.yancy.springframework.context.ApplicationContext;

/**
 * 测试类
 * UserService
 * 依赖UuserDao对象
 * @author yancy0109
 */
public class UserService {

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    private String uId;
    private String company;
    private String location;
    private IUserDao userDao;

    public String  queryUserInfo() {
        return userDao.queryUserName(uId) + " 公司: " + company + "，地点: " + location;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public IUserDao getUserDao() {
        return userDao;
    }
}

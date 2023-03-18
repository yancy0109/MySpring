package com.yancy.springframework.test.bean;

/**
 * 测试类
 * UserService
 * 依赖UuserDao对象
 * @author yancy0109
 */
public class UserService {

    private String uId;
    private String company;
    private String location;
    private UserDao userDao;

    public void queryUserInfo() {
        System.out.println("查询用户信息：" + userDao.queryUserName(uId) + " 公司: " + company + "，地点: " + location);
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
}

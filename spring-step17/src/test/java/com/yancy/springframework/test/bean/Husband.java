package com.yancy.springframework.test.bean;

import java.util.Date;

/**
 * @author yancy0109
 */
public class Husband {

    private String wifeName;

    private Date marriageDate;

    public String getWifeName() {
        return wifeName;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public Date getMarriageDate() {
        return marriageDate;
    }

    @Override
    public String toString() {
        return "Husband{" +
                "wifeName='" + wifeName + '\'' +
                ", marriageDate=" + marriageDate +
                '}';
    }

    public void setMarriageDate(Date marriageDate) {


        this.marriageDate = marriageDate;
    }
}

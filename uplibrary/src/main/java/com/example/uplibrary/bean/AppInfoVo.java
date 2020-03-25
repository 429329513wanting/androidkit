package com.example.uplibrary.bean;

import java.io.Serializable;

public class AppInfoVo implements Serializable {


    private String appId;
    private String appSecret;
    private String now;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

}

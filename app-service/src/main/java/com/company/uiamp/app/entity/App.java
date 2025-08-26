package com.company.uiamp.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.company.uiamp.common.entity.BaseEntity;

@TableName("sys_app")
public class App extends BaseEntity {
    private String appName;
    private String appKey;
    private String appSecret;
    private String redirectUri;
    private Integer status;

    // Getters and Setters
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
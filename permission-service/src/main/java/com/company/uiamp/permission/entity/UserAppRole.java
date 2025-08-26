package com.company.uiamp.permission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.company.uiamp.common.entity.BaseEntity;

@TableName("sys_user_app_role")
public class UserAppRole extends BaseEntity {
    private Long userId;
    private Long appId;
    private Long roleId;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
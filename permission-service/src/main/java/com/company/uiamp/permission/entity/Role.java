package com.company.uiamp.permission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.company.uiamp.common.entity.BaseEntity;

@TableName("sys_role")
public class Role extends BaseEntity {
    private String roleName;
    private String roleDesc;
    private Long appId;

    // Getters and Setters
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
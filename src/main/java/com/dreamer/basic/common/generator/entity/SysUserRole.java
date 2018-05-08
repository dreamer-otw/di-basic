package com.dreamer.basic.common.generator.entity;

import java.io.Serializable;

public class SysUserRole implements Serializable {
    private String nexusId;

    private String userId;

    private String roleId;

    private static final long serialVersionUID = 1L;

    public String getNexusId() {
        return nexusId;
    }

    public void setNexusId(String nexusId) {
        this.nexusId = nexusId == null ? null : nexusId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}
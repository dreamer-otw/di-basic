package com.dreamer.basic.common.generator.entity;

import java.io.Serializable;

public class SysRoleMenu implements Serializable {
    private String nexusId;

    private String roleId;

    private String menuId;

    private static final long serialVersionUID = 1L;

    public String getNexusId() {
        return nexusId;
    }

    public void setNexusId(String nexusId) {
        this.nexusId = nexusId == null ? null : nexusId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }
}
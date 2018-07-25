package com.dreamer.basic.backstage.sys.data;

import com.dreamer.basic.common.generator.entity.SysRole;

import java.util.List;

/**
 * > SysRole 扩展
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/24 17:47
 */
public class SysRoleData extends SysRole {
    List<String> menuIdList;

    public List<String> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        this.menuIdList = menuIdList;
    }
}

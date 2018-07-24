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
    List<Integer> menuIdList;

    public List<Integer> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Integer> menuIdList) {
        this.menuIdList = menuIdList;
    }
}

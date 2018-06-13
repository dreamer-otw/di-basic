package com.dreamer.basic.backstage.sys.data;

import com.dreamer.basic.common.generator.entity.SysMenu;

import java.util.List;

/**
 * > SysMenu 扩展
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/16 16:31
 */
public class SysMenuData extends SysMenu{

    private String parentName;
    private Boolean open;
    private List<?> list;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}

package com.dreamer.basic.backstage.sys.service;

import com.dreamer.basic.backstage.sys.data.SysMenuData;

import java.util.List;

/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/16 15:31
 */
public interface SysLoginService {
    //根据用户id获取菜单集合
    List<SysMenuData> getMenuListByUserId(int userId);
    int updatePwdByUserId(String password, int userId);
}

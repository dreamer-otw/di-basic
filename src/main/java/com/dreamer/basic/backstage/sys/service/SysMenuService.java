package com.dreamer.basic.backstage.sys.service;

import com.dreamer.basic.common.generator.entity.SysMenu;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * >
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/14 16:38
 */
@Service
public interface SysMenuService {
    //根据用户id获取菜单集合
    List<SysMenu> getMenuListByUserId(String userId);
}

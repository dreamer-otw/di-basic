package com.dreamer.basic.backstage.sys.controller;

import com.dreamer.basic.backstage.sys.data.SysRoleData;
import com.dreamer.basic.backstage.sys.service.SysRoleService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.backstage.sys.utils.Result;
import com.dreamer.basic.common.generator.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * > 角色管理
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/24 10:38
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController{
    @Autowired
    private SysRoleService sysRoleService;
    @ResponseBody
    @GetMapping("/list")
    public Page<SysRole> pageList(Integer pageNo, Integer pageSize, String sidx, String odrer) {
        return sysRoleService.getRoleListPage(pageNo,pageSize);
    }

    @GetMapping("/getRoleList")
    @ResponseBody
    public Result getRoleList() {
        List<SysRole> roleList = sysRoleService.getRoleList();
        if (roleList != null) {
            Map<String, Object> retData = new HashMap<>();
            retData.put("roleList", roleList);
            return Result.ok(retData);
        }
        return Result.error();
    }
    @PostMapping("/save")
    @ResponseBody
    public Result saveRole(SysRoleData sysRoleData) {
        sysRoleService.saveRole(sysRoleData);
        return Result.ok("保存成功");
    }

    @GetMapping("/delRole")
    @ResponseBody
    public Result delRole(Integer[] roleIds) {
        return Result.ok();
    }

    @GetMapping("/updateRole")
    @ResponseBody
    public Result updateRole(SysRole sysRole) {
        return Result.ok();
    }


}

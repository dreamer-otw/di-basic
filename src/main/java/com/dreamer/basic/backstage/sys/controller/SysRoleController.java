package com.dreamer.basic.backstage.sys.controller;

import com.dreamer.basic.backstage.sys.data.SysRoleData;
import com.dreamer.basic.backstage.sys.service.SysRoleService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.backstage.sys.utils.Result;
import com.dreamer.basic.common.generator.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public Result saveRole(@RequestBody SysRoleData sysRoleData) {
        sysRoleService.saveRole(sysRoleData);
        return Result.ok("保存成功");
    }

    @PostMapping("/delRole")
    @ResponseBody
    public Result delRole(@RequestBody String[] roleIds) {
        sysRoleService.delRole(roleIds);
        return Result.ok("删除成功");
    }

    @GetMapping("/updateRole")
    @ResponseBody
    public Result updateRole(@RequestBody SysRoleData sysRoleData) {
        sysRoleService.updateRole(sysRoleData);
        return Result.ok("更新成功");
    }
    @GetMapping("/roleInfo/{roleId}")
    @ResponseBody
    public Result getRoleInfo(@PathVariable("roleId") String roleId) {
        SysRoleData roleInfo = sysRoleService.getRoleInfo(roleId);
        if (roleInfo != null) {
            Map<String, Object> retData = new HashMap<>();
            retData.put("roleInfo", roleInfo);
            return Result.ok(retData);
        }
        return Result.error();
    }



}

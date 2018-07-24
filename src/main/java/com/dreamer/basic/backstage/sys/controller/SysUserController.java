package com.dreamer.basic.backstage.sys.controller;

import com.dreamer.basic.backstage.sys.service.SysUserService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.backstage.sys.utils.Result;
import com.dreamer.basic.common.generator.dao.SysUserMapper;
import com.dreamer.basic.common.generator.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @ResponseBody
    @GetMapping("/list")
    public Page<SysUser> getUserList(Integer pageNo, Integer pageSize, String sidx, String odrer) {
        return sysUserService.getUserList(pageNo, pageSize);
    }
    @PostMapping("/del")
    @ResponseBody
    public Result delMenu(@RequestBody Integer[] userIds) {
        int num = sysUserService.delUser(userIds);
        if (num > 0) {
            return Result.ok("删除成功");
        }
        return Result.error("删除失败");
    }


    @GetMapping("/userInfo/{userId}")
    @ResponseBody
    public Result getMenuInfoByMenuId(@PathVariable("userId") Integer userId) {
        SysUser userInfo = sysUserService.getUserById(userId);
        if (userInfo != null) {
            Map<String, Object> retData = new HashMap<>();
            retData.put("userInfo", userInfo);
            return Result.ok(retData);
        }
        return Result.error("获取失败");
    }
    @PostMapping("/updateUser")
    @ResponseBody
    public Result updateUser(SysUser sysUser) {
        int num = sysUserService.updateUser(sysUser);
        if (num > 0) {
            return Result.ok("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }
    @PostMapping("/save")
    @ResponseBody
    public Result saveUser(SysUser sysUser) {
        int num = sysUserService.updateUser(sysUser);
        if (num > 0) {
            return Result.ok("新增成功");
        } else {
            return Result.error("新增失败");
        }
    }

}

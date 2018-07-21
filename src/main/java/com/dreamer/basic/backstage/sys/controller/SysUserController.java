package com.dreamer.basic.backstage.sys.controller;

import com.dreamer.basic.backstage.sys.service.SysUserService;
import com.dreamer.basic.backstage.sys.utils.Page;
import com.dreamer.basic.common.generator.dao.SysUserMapper;
import com.dreamer.basic.common.generator.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @ResponseBody
    @GetMapping("/list")
    public Page<SysUser> getUserList() {
        return null;
    }

}

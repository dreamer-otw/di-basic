package com.dreamer.basic.backstage.sys.controller;

import com.sys.repository.entity.SysRoleEntity;
import com.sys.service.SysRoleMenuService;
import com.sys.service.SysRoleService;
import com.sys.service.SysUserRoleService;
import com.sys.utils.PageUtils;
import com.sys.utils.R;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author R & D
 * @email 908350381@qq.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit*page);

        // 查询列表数据
        List<SysRoleEntity> list = sysRoleService.queryList(map);
        int total = sysRoleService.queryTotal(map);

        PageUtils pageUtil = new PageUtils(list, total, limit, page);

        return R.ok().put("page", pageUtil);
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select() {
        // 查询列表数据
        List<SysRoleEntity> list = sysRoleService.queryList(new HashMap<String, Object>());

        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") String roleId) {
        SysRoleEntity role = sysRoleService.queryObject(roleId);

        // 查询角色对应的菜单
        List<String> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRoleEntity role) {
        try {
            if (StringUtils.isBlank(role.getRoleName())) {
                return R.error("角色名称不能为空");
            }

            sysRoleService.save(role);
        } catch (Exception e) {
            logger.error("新增角色失败", e);
            return R.error("新增角色失败");
        }
        return R.ok();
    }

    /**
     * 修改角色
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity role) {
        if (StringUtils.isBlank(role.getRoleName())) {
            return R.error("角色名称不能为空");
        }

        sysRoleService.update(role);

        return R.ok();
    }

    /**
     * 删除角色
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody String[] roleIds) {
        try {
            sysRoleService.deleteBatch(roleIds);
        } catch (Exception e) {
            logger.error("删除角色失败",e);
            return R.error("删除角色失败");
        }

        return R.ok();
    }
}

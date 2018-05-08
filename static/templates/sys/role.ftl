<!DOCTYPE html>
<html>
<head>
    <title>角色管理</title>
<#--<#include "header.html.ftl">-->
<#include "header.html.ftl">
</head>
<body>
<div id="rrapp">
    <div class="grid-btn">
    <@shiro.hasPermission name="sys:role:save">
        <a class="btn btn-default" href="role_add.html">新增</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="sys:role:update">
        <button type="button" class="btn btn-default" @click="update">修改</button>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="sys:role:update">
        <button type="button" class="btn btn-default" @click="del">删除</button>
    </@shiro.hasPermission>

    </div>
    <table id="jqGrid"></table>
    <div id="jqGridPager"></div>
</div>

<script src="${req.contextPath}/js/sys/role.js?_${.now?string('yyyyMMddHHmmssSSS')}"></script>
</body>
</html>
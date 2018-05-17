<!DOCTYPE html>
<html>
<head>
    <title>菜单管理</title>
</head>
<body>
<div id="rrapp">
    <div class="grid-btn">
        <@shiro.hasPermission name="sys:menu:save">
            <a class="btn btn-default" href="menu_add.html">新增</a>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="sys:menu:update">
            <button type="button" class="btn btn-default" @click="update">修改</button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="sys:menu:delete">
            <button type="button" class="btn btn-default" @click="del">删除</button>
        </@shiro.hasPermission>
    </div>
    <table id="jqGrid"></table>
    <div id="jqGridPager"></div>
</div>

<script src="${req.contextPath}/js/sys/menu.js?_${.now?string('yyyyMMddHHmmssSSS')}"></script>
</body>
</html>
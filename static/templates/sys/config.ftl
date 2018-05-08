<!DOCTYPE html>
<html>
<head>
<title>参数管理</title>
<#include "header.html.ftl">
</head>
<body>
<div id="rrapp">
	<div class="grid-btn">
		<#--<a class="btn btn-default" href="config_add.html">新增</a>
		<button type="button"  class="btn btn-default" @click="update">修改</button>
		<button type="button" class="btn btn-default" @click="del">删除</button>-->
	    <@shiro.hasPermission name="sys:config:save">
            <a class="btn btn-default" href="config_add.html">新增</a>
		</@shiro.hasPermission>
        <@shiro.hasPermission name="sys:config:update">
            <button type="button" class="btn btn-default" @click="update">修改</button>
		</@shiro.hasPermission>
        <@shiro.hasPermission name="sys:config:delete">
            <button type="button" class="btn btn-default" @click="del">删除</button>
		</@shiro.hasPermission>
	</div>
    <table id="jqGrid"></table>
    <div id="jqGridPager"></div>
</div>

<script src="${req.contextPath}/js/sys/config.js?_${.now?string('yyyyMMddHHmmssSSS')}"></script>
</body>
</html>
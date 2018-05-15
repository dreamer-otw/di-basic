<!DOCTYPE html>
<html>
<head>
<title>编辑管理员</title>
<#include "header.html.ftl">
</head>
<body>
<div class="panel panel-default" id="rrapp" v-cloak>
	<div class="panel-heading">{{title}}</div>
	<form class="form-horizontal">
		<div class="form-group">
		   	<div class="col-sm-2 control-label">用户名</div>
		   	<div class="col-sm-10">
		      <input type="text" class="form-control" v-model="user.username" placeholder="登录账号"/>
		    </div>
		</div>
		<div class="form-group">
		   	<div class="col-sm-2 control-label">密码</div>
		   	<div class="col-sm-10">
		      <input type="text" class="form-control" v-model="user.password" placeholder="密码"/>
		    </div>
		</div>
		<div class="form-group">
		   	<div class="col-sm-2 control-label">邮箱</div>
		   	<div class="col-sm-10">
		      <input type="text" class="form-control" v-model="user.email" placeholder="邮箱"/>
		    </div>
		</div>
		<div class="form-group">
		   	<div class="col-sm-2 control-label">手机号</div>
		   	<div class="col-sm-10">
		      <input type="text" class="form-control" v-model="user.mobile" placeholder="手机号"/>
		    </div>
		</div>
		<div class="form-group">
		   	<div class="col-sm-2 control-label">角色</div>
		   	<div class="col-sm-10">
			   	<label v-for="role in roleList" class="checkbox-inline">
				  <input type="checkbox" :value="role.roleId" v-model="user.roleIdList">{{role.roleName}}
				</label>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 control-label">状态</div> 
			<label class="radio-inline">
			  <input type="radio" name="status" value="0" v-model="user.status"/> 禁用
			</label>
			<label class="radio-inline">
			  <input type="radio" name="status" value="1" v-model="user.status"/> 正常
			</label>
		</div>
		<div class="form-group">
			<div class="col-sm-2 control-label"></div> 
			<input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
			&nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="back" value="返回"/>
		</div>
	</form>
</div>
<script src="${req.contextPath}/js/sys/user_add.js?_${.now?string('yyyyMMddHHmmssSSS')}"></script>
</body>
</html>
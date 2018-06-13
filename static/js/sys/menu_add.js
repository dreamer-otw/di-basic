var setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "menuId",
			pIdKey: "parentId",
			rootPId: -1
		},
		key: {
			url:"nourl",
			name: "menuName"
		}
	}
};
var ztree;

//菜单ID
var menuId = T.p("menuId");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增菜单",
		menu:{
			parentName:"",
			parentId:-1,
			menuType:0,
			orderNum:0
		}
	},
	created: function() {
		if(menuId != null){
			this.title = "修改菜单";
			this.getMenu(menuId)
		} else {
		    this.selectMenuType()
		}
		//加载菜单树
		$.get("../sys/menu/getMenuTree", function(r) {
			ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuTree);
		})
    },
	methods: {
	    selectMenuType: function() {
	        var radioCheck;
	        var radios = $("input[name='menuType']");
	        for (var i=0;i<radios.length;i++) {
                if (radios[i].checked) {
                    radioCheck = radios[i].value;
                }
            }
            switch (radioCheck) {
                case '0':
                    this.labelMenuName = "目录名称";
                    this.labelParentName = "";
                    $("#form-menuName").show();
                    $("#form-parentName").hide().value="";
                    $("#form-menuUrl").hide().value="";
                    $("#form-perms").hide().value="";
                    $("#form-orderNum").show();
                    $("#form-menuIcon").show();
                    break;
                case '1':
                    this.labelMenuName = "菜单名称";
                    this.labelParentName = "上级菜单";
                    $("#form-menuName").show();
                    $("#form-parentName").show();
                    $("#form-menuUrl").show();
                    $("#form-perms").show();
                    $("#form-orderNum").show();
                    $("#form-menuIcon").show();
                    break;
                case '2':
                    this.labelMenuName = "按钮名称";
                    this.labelParentName = "所属菜单";
                    $("#form-menuName").show();
                    $("#form-parentName").show();
                    $("#form-menuUrl").hide().value="";
                    $("#form-perms").show();
                    $("#form-orderNum").hide().value="";
                    $("#form-menuIcon").hide().value="";
                    break;
                default:
                    this.labelMenuName = "目录名称";
                    this.labelParentName = "";
                    $("#form-menuName").show();
                    $("#form-parentName").hide().value="";
                    $("#form-menuUrl").hide().value="";
                    $("#form-perms").hide().value="";
                    $("#form-orderNum").show();
                    $("#form-menuIcon").show();
            }
	    },
		getMenu: function(menuId){
			$.get("../sys/menu/menuInfo/"+menuId, function(r){
                vm.menu = r.menuInfo;
            });
		},
		saveOrUpdate: function (event) {
			var url = vm.menu.menuId == null ? "../sys/menu/save" : "../sys/menu/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.menu),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.back();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		menuTree: function(){
			layer.open({
				type: 1,
				offset: '50px',
				skin: 'layui-layer-molv',
				title: "选择菜单",
				area: ['300px', '450px'],
				shade: 0,
				shadeClose: false,
				content: jQuery("#menuLayer"),
				btn: ['确定', '取消'],
				btn1: function (index) {
					var node = ztree.getSelectedNodes();
					//选择上级菜单
					vm.menu.parentId = node[0].menuId;
					vm.menu.parentName = node[0].menuName;
					
					layer.close(index);
	            }
			});
		},
		back: function (event) {
			history.go(-1);
		}
	}
});




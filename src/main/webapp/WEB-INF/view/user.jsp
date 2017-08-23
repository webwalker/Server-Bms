<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="bms.core.model.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css"
	href="style/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="style/themes/icon.css">
<link rel="stylesheet" type="text/css" href="style/themes/demo.css">
<link rel="stylesheet" type="text/css" href="style/default.css">
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.easyui.min.js"></script>
<script type="text/javascript" src="script/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="script/common.js"></script>

<script>
	var tableId = "#tb";
	var formId = "#ff";
	var dialogId = "#dlg";
	baseUrl = '<%=basePath%>';
	reqUrl = getUrl('user/list');

	$(function() {
		$(tableId).datagrid({
			title : '用户管理',
			collapsible : false,
			width : 700,
			height : 500,
			nowrap : true,
			autoRowHeight : false,
			striped : true,
			collapsible : true,
			method : 'get',
			url : reqUrl,
			remoteSort : false,
			idField : 'id',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'userName',
				title : '登录账号',
				width : 100,
				sortable : true
			}, {
				field : 'userTypes',
				title : '用户类型',
				width : 70
			}, {
				field : 'email',
				title : '电子邮件',
				width : 140
			}, {
				field : 'mobile',
				title : '手机号码',
				width : 95
			}, {
				field : 'phone',
				title : '联系电话',
				width : 100
			}, {
				field : 'description',
				title : '备注信息',
				width : 150
			}, {
				field : 'active',
				title : '账号状态',
				width : 70,
				formatter : function(value, row, index) {
					if (value == "1")
						return "已激活";
					return "未激活";
				}
			}, {
				field : 'logonTimes',
				title : '登录次数',
				width : 70
			}, {
				field : 'logonIp',
				title : 'IP',
				width : 80
			}, {
				field : 'lastLogonTime',
				title : '最近登录',
				width : 130,
				formatter : function(value, row, index) {
					return new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 130,
				formatter : function(value, row, index) {
					return new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
			}, {
				field : 'updateTime',
				title : '修改时间',
				width : 130,
				formatter : function(value, row, index) {
					return new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
			}, {
				field : 'createUser',
				title : '创建者',
				width : 120
			} ] ],
			pagination : true,
			rownumbers : false,
			fitColumns : true,
			singleSelect : false,
			toolbar : [ {
				id : 'btnadd',
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					newItem('user/add', '新增用户');
				}
			}, '-', {
				id : 'btnEdit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					editItem('user/update/', '编辑用户');
				}
			}, '-', {
				id : 'btnDel',
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					var list = getSelections();
					if (list && list.length > 0) {
						reqUrl = getUrl('user/delete/' + list.join(','));
						deleteItem(reqUrl);
					}
				}
			}, '-', {
				id : 'btnDel',
				text : '分组',
				iconCls : 'icon-ok',
				handler : function() {
					var list = getSelections();
					if (list.length > 1) {
						alert('绑定分组仅限于对单个用户进行!');
						return;
					}
					var row = getSelected();
					if (row) {
						reqUrl = getUrl('usergroup/groups/view/' + row.id);
						window.location.href = reqUrl;
					}
				}
			} ],
			onBeforeLoad : function(param) {
				param.pageNo = param.requestPage || param.pageNo || 1;
				param.requestPage = undefined;
				param.pageSize = page_size;
			}
		});
		pageInit();
	});

	var initCallback = function() {

	};
	var addCallback = function() {
		$('#userName').attr("disabled", false);
		$('#userType')[0].selectedIndex = 0;
	};
	var editCallback = function(row) {
		$('#ID').val(row.id);
		//special for datagrid.load.row above ver1.4
		$("input[name='userPass']").val('');
		$("input[name='userPass']").prev().val('');
		$("input[name='confPass']").prev().val('');
		$("input[name='userName']").prev().attr("disabled", "disabled");
	}
	var preSubmit = function() {
		if (actionType == "add" && $('#userPass').val() == '') {
			alert('登录密码不能为空');
			$('#userPass').focus();
			return false;
		}
		if ($('#confPass').val() != $('#userPass').val()) {
			alert('两次输入密码信息不一致');
			$('#confPass').focus();
			return false;
		}
		return true;
	};
</script>
</head>

<body>
	<table id="tb"></table>
	<div id="dlg" class="easyui-dialog"
		style="width:430px;height:460px;padding:10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">用户信息</div>
		<form:form id="ff" method="post" modelAttribute="user">
			<form:input path="ID" type="hidden" />
			<div class="fitem">
				<label>登录账号:</label>
				<form:input path="userName" maxlength="20" class="easyui-textbox"
					data-options="required:true,validType:'text'" style="width:150px" />
			</div>
			<div class="fitem">
				<label>登录密码:</label>
				<form:password path="userPass" maxlength="25" class="easyui-textbox"
					data-options="validType:'length[5,25]'" style="width:150px" />
				为空不修改
			</div>
			<div class="fitem">
				<label>确认密码:</label> 
				<form:password path="confPass" maxlength="25" class="easyui-textbox"
					validType="equalTo['#userPass']" style="width:150px" />
			</div>
			<div class="fitem">
				<label>用户类型:</label>
				<form:select path="userType" items="${type}" itemLabel="desc"
					itemValue="code" style="width:150px" />
			</div>
			<div class="fitem">
				<label>电子邮箱:</label>
				<form:input path="email" class="easyui-textbox"
					data-options="required:true,validType:'email'" style="width:150px" />
			</div>
			<div class="fitem">
				<label>手机号码:</label>
				<form:input path="mobile" class="easyui-textbox"
					data-options="required:true,validType:'mobile'" style="width:150px" />
			</div>
			<div class="fitem">
				<label>联系电话:</label>
				<form:input path="phone" class="easyui-textbox"
					data-options="required:true,validType:'phone'" style="width:150px" />
			</div>
			<div class="fitem">
				<label>用户描述:</label>
				<form:input path="description" class="easyui-textbox"
					data-options="required:true,validType:'length[4,20]'" rows="4"
					style="width:146px" />
			</div>
			<div class="fitem">
				<label>是否激活:</label>
				<form:checkbox path="active" style="width:150px; margin-left:-65px;" />
			</div>
		</form:form>
	</div>
	<div id="dlg-buttons">
		<a id="aSave" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a id="aCancel" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>
</body>
</html>
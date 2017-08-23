<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	reqUrl = getUrl('authset/list');

	$(function() {
		$(tableId).datagrid({
			title : '权限元素配置',
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
				title : '元素编号',
				field : 'id',
				width : 60
			}, {
				field : 'functionID',
				title : '功能标识',
				width : 140
			},{
				field : 'authName',
				title : '元素名称',
				width : 100,
				sortable : true
			}, {
				field : 'description',
				title : '描述',
				width : 150
			}, {
				field : 'status',
				title : '元素状态',
				width : 70,
				formatter : function(value, row, index) {
					if (value == "1")
						return "有效";
					return "无效";
				}
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 150,
				formatter : function(value, row, index) {
					return new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
			}, {
				field : 'updateTime',
				title : '修改时间',
				width : 150,
				formatter : function(value, row, index) {
					return new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
			}, {
				field : 'createUser',
				title : '创建者',
				width : 150
			},{
				field : 'opt',
				title : '操作',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.url == '')
						return "";
					return "<img src='images/groupmenu.gif' onclick='addUrl("
							+ row.id + ")' />";
				}
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
					newItem('/authset/add', '创建分组');
				}
			}, '-', {
				id : 'btnEdit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					editItem('/authset/update/', '修改分组');
				}
			}, '-', {
				id : 'btnDel',
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					var list = getSelections();
					if (list && list.length > 0) {
						reqUrl = getUrl('authset/delete/' + list.join(','));
						deleteItem(reqUrl);
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

	var editCallback = function(row) {
		$('#ID').val(row.id);
	}
	var addUrl = function(id) {
		window.location.href = getUrl("urlset/2/" + id);
	};	
</script>
</head>

<body>
	<table id="tb"></table>
	<div id="dlg" class="easyui-dialog"
		style="width:400px;height:290px;padding:10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">元素信息</div>
		<form:form id="ff" method="post" modelAttribute="auth">
			<form:input path="ID" type="hidden" />
			<div class="fitem">
				<label>功能标识:</label>
				<form:input path="functionID" maxlength="20"
					class="easyui-textbox" required="true"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>元素名称:</label>
				<form:input path="authName" maxlength="20"
					class="easyui-textbox" required="true"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>元素描述:</label>
				<form:input path="description" maxlength="20"
					class="easyui-textbox" required="true"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>元素状态:</label>
				<form:checkbox path="status" style="width:150px; margin-left:-65px;" />
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

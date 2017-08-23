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
	reqUrl = getUrl('menuauth/list/${menu.ID}');

	$(function() {
		$(tableId).datagrid({
			title : '菜单[${menu.menuName}]—权限设定',
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
				field : 'id',
				title : '权限编号',
				width : 50
			},  {
				field : 'functionID',
				title : '功能标识',
				width : 80
			}, {
				field : 'authName',
				title : '权限名称',
				width : 100,
				sortable : true
			}, {
				field : 'description',
				title : '描述',
				width : 120
			}, {
				field : 'status',
				title : '元素状态',
				width : 80,
				formatter : function(value, row, index) {
					if (value == "1")
						return "有效";
					return "无效";
				}
			}, {
				field : 'createTime',
				title : '绑定时间',
				width : 130,
				formatter : function(value, row, index) {
					if (value == null)
						return "—";
					return new Date(value).format('yyyy-MM-dd hh:mm:ss');
				}
			}, {
				field : 'createUser',
				title : '操作者',
				width : 120,
				formatter : function(value, row, index) {
					if (value == null)
						return "—";
					return value;
				}
			} ] ],
			pagination : true,
			rownumbers : false,
			fitColumns : true,
			singleSelect : false,
			selectOnCheck : false,
			toolbar : [ {
				id : 'btnadd',
				text : '更新绑定',
				iconCls : 'icon-add',
				handler : function() {
					addAuth();
				}
			}, '-', {
				id : 'btnEdit',
				text : '取消绑定',
				iconCls : 'icon-remove',
				handler : function() {
					deleteAuth();
				}
			} ],
			onBeforeLoad : function(param) {
				param.pageNo = param.requestPage || param.pageNo || 1;
				param.requestPage = undefined;
				param.pageSize = page_size;
			},
			onLoadSuccess : function(data) {
				if (data) {
					$.each(data.rows, function(index, item) {
						if (item.ck) {
							$(tableId).datagrid('checkRow', index);
						}
					});
				}
			}
		});
		pageInit();
	});
	function addAuth() {
		var list = getCheckeds();
		if (list && list.length > 0) {
			var rows = getRows();
			reqUrl = getUrl('menuauth/add/${menu.ID}/' + list.join(',') + '/'
					+ rows.join(','));
			post(reqUrl);
		}
	}
	function deleteAuth() {
		var list = getCheckeds();
		if (list && list.length > 0) {
			reqUrl = getUrl('menuauth/delete/${menu.ID}/' + list.join(','));
			post(reqUrl);
		}
	}
</script>
</head>

<body>
	<table id="tb"></table>
</body>
</html>
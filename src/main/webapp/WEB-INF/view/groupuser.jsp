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
	reqUrl = getUrl('usergroup/list/${group.ID}');

	$(function() {
		$(tableId).datagrid({
			title : '分组[${group.groupName}]—用户管理',
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
				field : 'userID',
				title : '用户编号',
				width : 50
			}, {
				field : 'userName',
				title : '登录账号',
				width : 100,
				sortable : true
			}, {
				field : 'userTypes',
				title : '用户类型',
				width : 60
			}, {
				field : 'description',
				title : '备注信息',
				width : 150
			}, {
				field : 'active',
				title : '账号状态',
				width : 60,
				formatter : function(value, row, index) {
					if (value == "1")
						return "已激活";
					return "未激活";
				}
			},  {
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
					addUser();
				}
			}, '-', {
				id : 'btnEdit',
				text : '取消绑定',
				iconCls : 'icon-remove',
				handler : function() {
					deleteUser();
				}
			} ],
			onBeforeLoad : function(param) {
				param.pageNo = param.requestPage || param.pageNo || 1;
				param.requestPage = undefined;
				param.pageSize = page_size;
			},
			onLoadSuccess:function(data){                    
		        if(data){
		            $.each(data.rows, function(index, item){
		                if(item.ck){
		                    $(tableId).datagrid('checkRow', index);
		                }
		            });
		        }
		    }			
		});
		pageInit();
	});
	function addUser() {
		var list = getCheckeds();
		if (list && list.length > 0) {
			var rows = getRows();
			reqUrl = getUrl('usergroup/add/${group.ID}/' + list.join(',') + '/' + rows.join(','));
			post(reqUrl);
		}
	}
	function deleteUser() {
		var list = getCheckeds();
		if (list && list.length > 0) {
			reqUrl = getUrl('usergroup/delete/${group.ID}/' + list.join(','));
			post(reqUrl);
		}
	}	
</script>
</head>

<body>
	<table id="tb"></table>
</body>
</html>
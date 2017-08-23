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
	var userName = '${user.userName}';
	var userID = '${user.ID}';
	reqUrl = getUrl('usergroup/groups/list/' + userID);

	$(function() {
		$(tableId).datagrid({
			title :  '用户[' + userName + ']—所属分组管理',
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
				title : '分组编号',
				field : 'id',
				width : 80
			}, {
				field : 'groupName',
				title : '分组名称',
				width : 120,
				sortable : true
			}, {
				field : 'description',
				title : '分组描述',
				width : 220,
				formatter : function(value, row, index) {
					if (value.length > 40)
						value = value.substring(0, 40) + "...";
					return "<span title='"+value+"'>" + value + "</span>";
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
			} ] ],
			pagination : true,
			rownumbers : false,
			fitColumns : true,
			singleSelect : false,
			toolbar : [ {
				id : 'btnadd',
				text : '更新绑定',
				iconCls : 'icon-add',
				handler : function() {
					addGroup();
				}
			}],
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
	
	function addGroup() {
		var list = getCheckeds();
		if (list && list.length > 0) {
			var rows = getRows();
			reqUrl = getUrl('usergroup/groups/bind/${user.ID}/' + list.join(',') + '/' + rows.join(','));
			post(reqUrl);
		}
	}
</script>
</head>

<body>
	<table id="tb"></table>
</body>
</html>

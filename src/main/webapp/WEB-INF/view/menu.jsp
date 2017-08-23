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
	var tbSort = "#tbSort";
	var sortDlg = "#sortDlg";
	var parentID = ${menu.parentID};
	var nodeParentID;
	baseUrl = "<%=basePath%>";
	reqUrl = getUrl('menu/list/' + parentID);

	$(function() {
		$(tableId)
				.datagrid(
						{
							title : '菜单管理',
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
							columns : [ [
									{
										field : 'ck',
										checkbox : true
									},
									{
										title : '菜单编号',
										field : 'id',
										width : 80
									},
									{
										field : 'parentID',
										hidden : true
									},
									{
										field : 'menuName',
										title : '菜单名称',
										width : 120,
										sortable : true,
										formatter : function(value, row, index) {
											return "<a href='javascript:void(0)' onclick='addSubItem("
													+ row.id
													+ ");'>"
													+ value
													+ "</a>";
										}
									},
									{
										field : 'url',
										title : '访问地址',
										width : 220,
										formatter : function(value, row, index) {
											if (value.length > 40)
												value = value.substring(0, 40)
														+ "...";
											return "<span title='"+value+"'>"
													+ value + "</span>";
										}
									},
									{
										field : 'sort',
										title : '菜单排序',
										width : 80,
										sortable : true
									},
									{
										field : 'createTime',
										title : '创建时间',
										width : 150,
										formatter : function(value, row, index) {
											return new Date(value)
													.format('yyyy-MM-dd hh:mm:ss');
										}
									},
									{
										field : 'updateTime',
										title : '修改时间',
										width : 150,
										formatter : function(value, row, index) {
											return new Date(value)
													.format('yyyy-MM-dd hh:mm:ss');
										}
									},
									{
										field : 'createUser',
										title : '创建者',
										width : 150
									},
									{
										field : 'opt',
										title : '操作',
										width : 100,
										align : 'center',										
										formatter : function(value, row, index) {
											if (row.url == '')
												return "";
											return "<img src='images/security.gif' onclick='addAuth("
													+ row.id + ")' />" + "&nbsp;<img src='images/groupmenu.gif' onclick='addUrl("
													+ row.id + ")' />";
										}
									} ] ],
							pagination : true,
							rownumbers : false,
							fitColumns : true,
							singleSelect : false,
							toolbar : [
									{
										id : 'btnadd',
										text : '新增',
										iconCls : 'icon-add',
										handler : function() {
											newItem('/menu/add', '创建菜单');
										}
									},
									'-',
									{
										id : 'btnEdit',
										text : '修改',
										iconCls : 'icon-edit',
										handler : function() {
											editItem('/menu/update/', '修改菜单');
										}
									},
									'-',
									{
										id : 'btnDel',
										text : '删除',
										iconCls : 'icon-remove',
										handler : function() {
											var list = getSelections();
											if (list && list.length > 0) {
												reqUrl = getUrl('menu/delete/'
														+ list.join(','));
												deleteItem(reqUrl);
											}
										}
									}, '-', {
										id : 'btnMgr',
										text : '排序',
										iconCls : 'icon-reload',
										handler : function() {
											sortItem();
										}
									} ],
							onBeforeLoad : function(param) {
								param.pageNo = param.requestPage
										|| param.pageNo || 1;
								param.requestPage = undefined;
								param.pageSize = page_size;
							}
						});
		$(tbSort).tree({
			url : getUrl('menu/sortree'),
			method : 'get',
			dnd : true,
			onClick : function(node) {
				nodeParentID = $(tbSort).tree('getParent', node.target).id;
			},
			onDrop : function(target, source, point) {
				dragNode(target, source, point);
			}
		});
		$(sortDlg).dialog({
			buttons : [ {
				text : '关闭',
				iconCls : 'icon-ok',
				handler : function() {
					$(sortDlg).dialog('close');
				}
			} ]
		});
		pageInit();
	});
	
	var addCallback = function() {
		$('#parentID').val(parentID);
	};
	var editCallback = function(row) {
		$('#ID').val(row.id);
		$('#parentID').val(row.parentID);
	};
	var addSubItem = function(pid) {
		window.location.href = getUrl('menu?pid=' + pid);
	}
	var addAuth = function(id) {
		window.location.href = getUrl('menuauth/' + id);
	};
	var addUrl = function(id) {
		window.location.href = getUrl('urlset/1/' + id);
	};
	var sortItem = function() {
		$(sortDlg).dialog('open').dialog('setTitle', '菜单排序管理');
	};
	var dragNode = function(target, source, point) {
		var targetId = $(tbSort).tree('getNode', target).id;
		var tParent = $(tbSort).tree('getParent', target);
		var tParentId = null;
		if(tParent != null){
			tParentId = tParent.id;
		}
		var targetText = $(tbSort).tree('getNode', target).text;
		var sort = $(tbSort).tree('getNode', target).sort;
		var sourceId = source.id;
		nodeParentID = tParentId;

		switch (point) {
		case 'append':
			nodeParentID = targetId;
			break;
		case 'top':
			sort = sort - 1;
			break;
		case 'bottom':
			sort = sort + 1;
			break;
		}
		//var msg = "name:" + targetText +  "\nparent:" + tParentId + "\nsort:" + sort + "\nid:"+ targetId;
		//msg += "\n\nname:" + source.text +  "\nparent:" + nodeParentID +"\nsort:" + sort +  "\nid:" + sourceId + "\npoint:" + point;
		// alert(msg);

		$.ajax({
			url : getUrl('menu/sort'),
			data : 'id=' + sourceId + '&parent=' + nodeParentID + '&sort='
					+ sort,
			dataType : 'json',
			success : function(result) {
				$.messager.show({
					title : '提示',
					msg : result.message
				});
			}
		});
	};
</script>
</head>

<body>
	<table id="tb"></table>
	<div id="dlg" class="easyui-dialog"
		style="width:400px;height:290px;padding:10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">菜单信息</div>
		<form:form id="ff" method="post" modelAttribute="menu">
			<form:input path="ID" type="hidden" />
			<form:input path="parentID" type="hidden" />
			<div class="fitem">
				<label>菜单名称:</label>
				<form:input path="menuName" maxlength="20"
					class="easyui-textbox"
					data-options="required:true,validType:'length[2,20]'"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>访问地址:</label>
				<form:input path="url" class="easyui-textbox"
					style="width:150px" />
			</div>
			<div class="fitem">
				<label>菜单排序:</label>
				<form:input path="sort" class="easyui-textbox"
					style="width:150px"
					data-options="required:true,validType:'integer'" />
			</div>
		</form:form>
	</div>
	<div id="dlg-buttons">
		<a id="aSave" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a id="aCancel" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>

	<div id="sortDlg" class="easyui-dialog"
		style="width:270px;height:460px;padding:10px 20px" closed="true">
		<ul id="tbSort" class="easyui-tree"></ul>
	</div>
</body>
</html>

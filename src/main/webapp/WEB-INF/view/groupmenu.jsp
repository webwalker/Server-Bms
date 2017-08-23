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
	var tbTree = "#tbTree";
	baseUrl = '<%=basePath%>';
	reqUrl = getUrl('groupmenu/list');
	var groupID = ${group.ID};
	var auths = ${auth};
	var checkedmenu = ${checkedmenu};
	var checkedauth = ${checkedauth};
	var enableSubAuth = ${authType}; //是否启用页面元素级权限

	$(function() {
		$(tbTree).tree(
				{
					url : reqUrl,
					method : 'get',
					checkbox : true,
					onlyLeafCheck : false,
					cascadeCheck : true,
					onCheck : function(node, checked) {
						refreshChecks(node, checked);
					},
					formatter : function(node) {
						return formatAuthElements(node);
					},
					onLoadSuccess : function(node, data) {
						if (checkedmenu.length > 0) {
							$.each(checkedmenu, function(i, item) {
								var obj = $(tbTree).tree('find', item.menuID).target;
								var children = $(tbTree).tree('getChildren', obj);
								if(children.length <= 0){
									$(tbTree).tree('check',obj);
								}
							});
						}
						$("input:checkbox[name^='input_']").each(function() {
							$(this).prop("checked", false);
						});
						if (checkedauth.length > 0) {
							$.each(checkedauth, function(i, item) {
								$("#input_id" + item.authID).prop("checked",
										true);
							});
						}
					}
				});
	});

	var setCheck = function(node, checked) {
		$("input:checkbox[name='input_" + node.id + "']").each(function() {
			$(this).prop("checked", checked);
		});
		var children = $(tbTree).tree('getChildren', node.target);
		for (var i = 0; i < children.length; i++) {
			$("input:checkbox[name='input_" + children[i].id + "']").each(
					function() {
						$(this).prop("checked", checked);
					});
		}
	};

	//group-menu
	var getMenuChecks = function() {
		var nodes = $(tbTree).tree('getChecked', ['checked','indeterminate']);
		var arr = new Array();
		for (var i = 0; i < nodes.length; i++) {
			arr.push(nodes[i].id);
		}
		if (arr.length == 0)
			return null;
		return arr;
	};

	//group-auth
	var getAuthChecks = function() {
		if (enableSubAuth == false)
			return null;
		var arr = new Array();
		$($("input:checkbox[name^='input_']:checked")).each(function() {
			arr.push($(this).val());
		});
		if (arr.length == 0)
			return null;
		return arr;
	};

	var refreshChecks = function(node, checked) {
		//alert(node.id + "," + node.text);
		if (enableSubAuth == false)
			return;
		if (checked)
			setCheck(node, true);
		else
			setCheck(node, false);
	}

	var formatAuthElements = function(node) {
		var s = node.text;
		if (node.url == '')
			return s;
		var nodeText = s;
		var sb = new StringBuilder();
		if (enableSubAuth) {
			nodeText = s + "&nbsp;&nbsp;";
			$
					.each(
							auths,
							function(idx, item) {
								if (node.id == item.menuID) {
									sb
											.append("<label style='float:right;color:gray;'>");
									sb
											.append("<input type=checkbox name='input_"
													+ node.id + "' ");
									sb.append("value='" + item.authID + "' ");
									sb.append("id='input_id" + item.authID
											+ "' ");
									sb.append("onclick='authClick(this, "
											+ node.id + ")'");
									sb.append(" />" + item.authName
											+ "</label>");
								}
							});
		}
		//alert(nodeText);
		return nodeText + sb.toString();
	};

	var authClick = function(obj, id) {
		if ($(obj).prop("checked")) {
			$(tbTree).tree('check', $(tbTree).tree('find', id).target);
		}
	};
	var saveMenuAuth = function() {
		var menus = getMenuChecks();
		var auths = getAuthChecks();
		$.ajax({
			url : getUrl('groupmenu/saveauth'),
			data : {
				groupid : groupID,
				'menu[]' : menus,
				'auth[]' : auths
			},
			type : 'POST',
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

	分组[${group.groupName}]—菜单绑定<br /><br />
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-save'" onclick="saveMenuAuth();">保存</a>

	通过菜单授权可控制用户的菜单查看权限，通过菜单子元素授权可设定某一菜单内单个操作的授权。
	<br />
	<br />
	<table id="tbTree"></table>

</body>
</html>

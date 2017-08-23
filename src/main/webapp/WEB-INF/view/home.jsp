<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.easyui.min.js"></script>
<script type="text/javascript" src="script/common.js"></script>

<script type="text/javascript">
	var tbTree = "#tb";
	var baseUrl = "<%=basePath%>";
	$(function() {
		$(tbTree).tree({
			url : getUrl('user/tree'),
			method : 'get',
			animate : false,
			onDblClick : function(node) {
				if ($(this).tree('isLeaf', node.target))
					return;
				if (node.state == 'open') {
					$(this).tree('collapse', node.target);
				} else {
					$(this).tree('expand', node.target);
				}
			},
			onClick : function(node) {
				if (!$(this).tree('isLeaf', node.target))
					return;
				var url = node.url;
				if (typeof url != 'undefined') {
					if (url.indexOf('http://') > -1)
						$('#frame').attr('src', url);
					else
						$('#frame').attr('src', baseUrl + url);
				}
			}
		});
		$('#frame').attr('src', getUrl('welcome'));
	});
	var logout = function(){
		window.location.href=baseUrl + "/logout";
	};
</script>

</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="height:60px;background:#B3DFDA;padding:10px">
		<a href="javascript:void(0)" onclick="logout();">注销</a>
	</div>
	<div data-options="region:'west',split:true,title:'后台管理'"
		style="width:170px;padding:10px;">
		<table id="tb"></table>
	</div>
	<div
		data-options="region:'east',split:true,collapsed:true,title:'East'"
		style="width:100px;padding:10px;">right</div>
	<div data-options="region:'south',border:false"
		style="height:50px;background:#A9FACD;padding:10px;display:none">bottom</div>
	<div data-options="region:'center',title:''">
		<iframe id="frame" style="width:100%;height:100%;border:0px;"></iframe>
	</div>
</body>
</html>

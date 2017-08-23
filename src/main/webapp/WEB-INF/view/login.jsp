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
<link rel="stylesheet" type="text/css"
	href="style/themes/themes/icon.css">
<link rel="stylesheet" type="text/css" href="style/themes/demo.css">
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.easyui.min.js"></script>
<script type="text/javascript" src="script/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="script/common.js"></script>
</head>
<body>
	<form:form id="ff" method="post" modelAttribute="user">
		<form:input path="userName" cssClass="easyui-textbox"
			data-options="required:true,validType:'length[4,50]'" />
		<br>
		<form:password path="userPass" cssClass="easyui-textbox"
			data-options="required:true,validType:'length[4,50]'" />
		<br>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="$('#ff').submit();">登录</a>
	</form:form>

	<script>
		var baseUrl = "<%=basePath%>";
		$('#ff').form({
			onSubmit : function() {
				var isValid = $('#ff').form('validate');
				if (!isValid)
					return false;
				return true;
			},
			success : function(result) {
				window.location.href = getUrl('home');
			}
		});
	</script>


</body>
</html>

Date.prototype.format = function(format) {
	if (this.toString().indexOf("1970") > -1
			|| this.toString().indexOf("1900") > -1)
		return "";
	if (!format) {
		format = "yyyy-MM-dd hh:mm:ss";
	}
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
function fomatDateTime(str) {
	return (new Date(parseInt(str.substring(str.indexOf('(') + 1, str
			.indexOf(')'))))).format("yyyy-MM-dd hh:mm:ss");
}

function fomatDate(str) {
	return (new Date(parseInt(str.substring(str.indexOf('(') + 1, str
			.indexOf(')'))))).format("yyyy-MM-dd");
}

function StringBuilder() {
	this.init();
};
// 初始化StringBuilder类
StringBuilder.prototype.init = function() {
	this.array = [];
};
// 追加数据到StringBuilder类
StringBuilder.prototype.append = function(element) {
	this.array.push(element);
};
// 转换成String
StringBuilder.prototype.toString = function() {
	return this.array.join("");
};

var formatStr = function(value, length) {
	if (value == null || value == "")
		return "";
	var title = value;
	if (value.length > length)
		value = value.substring(0, length) + "...";
	return "<span title='" + title + "'>" + value + "</span>";
};

$
		.extend(
				$.fn.validatebox.defaults.rules,
				{
					integer : {// 验证整数
						validator : function(value) {
							return /^[+]?[1-9]+\d*$/i.test(value);
						},
						message : '请输入整数'
					},
					text : {
						validator : function(value) {
							return /^\w{4,20}$/i.test(value);
						},
						message : '字母、数字、下划线的4-20位数'
					},
					equalTo : {
						validator : function(value, param) {
							return $(param[0]).val() == value;
						},
						message : '字段不匹配'
					},
					phone : {// 验证电话号码
						validator : function(value) {
							return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
									.test(value);
						},
						message : '格式不正确,示例:021-88888888'
					},
					mobile : {// 验证手机号码
						validator : function(value) {
							return /^(13|15|18)\d{9}$/i.test(value);
						},
						message : '手机号码格式不正确'
					},
				});

function getUrl(url) {
	return baseUrl + 'mgr/' + url;
}

$(document).ready(function() {
	$('#aSave').bind("click", function() {
		saveItem();
	});
	$('#aCancel').bind("click", function() {
		$(dialogId).dialog('close');
	});
});

function pageInit() {
	var p = $(tableId).datagrid('getPager');
	$(p).pagination({
		pageSize : page_size,
		pageList : [ 15, 20, 30 ],
		showRefresh : false,
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function() {
		},
		onSelectPage : function(pageNumber, pageSize) {
			page_no = pageNumber;
			page_size = pageSize;
			if (typeof pageCallback != 'undefined'){
				pageCallback();
				return;
			}
			$(tableId).datagrid("reload", {
				requestPage : page_no,
				pageSize : pageSize
			});
		},
		onChangePageSize : function(pageSize) {
			page_no = 1;
			page_size = pageSize;
		}
	});
	// 第一次加载时自动变化大小
	$(tableId).resizeDataGrid(0, 0, 0, 0);
	// 当窗口大小发生变化时，调整DataGrid的大小
	$(window).resize(function() {
		$(tableId).resizeDataGrid(0, 0, 0, 0);
	});
	if (typeof initCallback != 'undefined')
		initCallback();
}

function getSelected() {
	return $(tableId).datagrid('getSelected');
}

function getSelections() {
	var ids = [];
	var rows = $(tableId).datagrid('getSelections');
	for (var i = 0; i < rows.length; i++) {
		ids.push(rows[i].id);
	}
	return ids;
}

function getCheckeds() {
	var ids = [];
	var rows = $(tableId).datagrid('getChecked');
	for (var i = 0; i < rows.length; i++) {
		ids.push(rows[i].id);
	}
	return ids;
}

function getRows() {
	var ids = [];
	var rows = $(tableId).datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		ids.push(rows[i].id);
	}
	return ids;
}

$.fn.extend({
	resizeDataGrid : function(heightMargin, widthMargin, minHeight, minWidth) {
		var height = $(document.body).height() - heightMargin;
		var width = $(document.body).width() - widthMargin;
		height = height < minHeight ? minHeight : height;
		width = width < minWidth ? minWidth : width;
		$(this).datagrid('resize', {
			height : height,
			width : width
		});
	}
});

var baseUrl, reqUrl, actionType;
var page_size = 15;
var page_no = 1;

function newItem(url, title) {
	actionType = 'add';
	$(dialogId).dialog('open').dialog('setTitle', title);
	$(formId).form('clear');
	reqUrl = getUrl(url);
	if (typeof addCallback != 'undefined')
		addCallback();
}

function editItem(url, title) {
	actionType = 'edit';
	var rows = getSelections();
	if (rows.length > 1) {
		alert('无法同时修改多个项目');
		return;
	}
	var row = getSelected();
	if (row) {
		$(dialogId).dialog('open').dialog('setTitle', title);
		$(formId).form('load', row);
		reqUrl = getUrl(url);
		if (typeof editCallback != 'undefined')
			editCallback(row);
	}
}

function deleteItem(url) {
	actionType = 'delete';
	confirmPost(url, '删除时关联信息都将会删除而无法恢复，确定删除吗?');
}

function post(url) {
	$.post(url, {
	// id : row.ID
	}, function(result) {
		if (result.success) {
			$(tableId).datagrid('clearSelections');
			var currows = $(tableId).datagrid('getRows').length;
			var pageNumber = $(tableId).datagrid('options').pageNumber;
			$(tableId).datagrid('reload', {
				requestPage : pageNumber
			});
		}
		$.messager.alert('提示', result.message, 'info');
	}, 'json');
}

function confirmPost(url, msg) {
	$.messager.confirm('Confirm', msg, function(r) {
		if (r) {
			post(url);
		}
	});
}

function saveItem() {
	$(formId).form('submit', {
		url : reqUrl,
		onSubmit : function() {
			var isValid = true;
			if (typeof preSubmit != 'undefined')
				isValid = preSubmit();
			if (!isValid)
				return isValid;
			return $(this).form('validate');
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (!result.success) {
				$.messager.show({
					title : 'Error',
					msg : result.message
				});
			} else {
				$(dialogId).dialog('close'); // close the dialog
				$(tableId).datagrid('reload'); // reload the user data
			}
		}
	});
}
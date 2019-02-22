var setting = {
	async : {
		enable : true,
		url : "/platform/sys/dept_tree",// 异步加载时的请求地址
		autoParam : [ "id" ],// 提交参数
		otherParam : {
			"selectedIds" : function() {
				return $("#selectedIds").val()
			}
		},
		type : 'post',
		dataType : 'json'
	},
	view : {
		showIcon : true
	},
	check : {
		enable : true,
		autoCheckTrigger : false,
		chkStyle : $("#chkStyle").val(),
		radioType : "all",
		chkboxType : {
			"Y" : "ps",
			"N" : "ps"
		}
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	expandSpeed : "",
	callback : {
		onClick : zTreeOnClick
	}
};

// 单击时获取zTree节点的Id,和value的值
function zTreeOnClick(event, treeId, treeNode, clickFlag) {
	console.log(treeNode);
};

// 这是将选中的节点的id用;分割拼接起来
function selectedNodeIds() {
	var treeObj = $.fn.zTree.getZTreeObj("treediv");
	var nodes = treeObj.getCheckedNodes();
	var selectedIds = "";
	var selectedNames = "";
	for (var i = 0; i < nodes.length; i++) {
		if (nodes[i].id != null) {
			selectedIds += (i == (nodes.length - 1)) ? nodes[i].id : nodes[i].id + ";";
			selectedNames += (i == (nodes.length - 1)) ? nodes[i].name : nodes[i].name + ";";
		}
	}
	$("#selectedIds").val(selectedIds);
	$("#selectedNames").val(selectedNames);
};

// 获取输入框勾选状态被改变的节点集合;分割拼接起来
function changeCheckedNodeIds() {
	var treeObj = $.fn.zTree.getZTreeObj("treediv");
	var nodes = treeObj.getChangeCheckedNodes();
	var changeCheckedIds = "";
	for (var i = 0; i < nodes.length; i++) {
		if (nodes[i].id != null) {
			changeCheckedIds += (i == (nodes.length - 1)) ? nodes[i].id : nodes[i].id + ";";
		}
	}
	$("#changeCheckedIds").val(changeCheckedIds);
};

$(function() {
	$.ajax({
		url : '/platform/sys/dept_tree',
		type : 'post',
		dataType : 'json',
		data : $("#treeparams").serialize(),
		success : function(data) {
			// 加载树
			$.fn.zTree.init($("#treediv"), setting, data);

			// 默认展开第一个节点
			var treeObj = $.fn.zTree.getZTreeObj("treediv");
			var nodes = treeObj.getNodes();

			if (nodes.length > 0) {
				// zTreeObj.expandNode(treeNode, expandFlag, sonSign, focus, callbackFlag)
				// 展开节点
				treeObj.expandNode(nodes[0], true, true, true);
			}
		}
	});
});

// 选择框回调函数 弹出层的返回值
function callbackdata() {

	selectedNodeIds();
	changeCheckedNodeIds();

	var selectedIds = $("#selectedIds").val();
	var selectedNames = $("#selectedNames").val();
	var changeCheckedIds = $("#changeCheckedIds").val();

	var obj = new Object();
	obj.selectedIds = selectedIds;
	obj.selectedNames = selectedNames;
	obj.changeCheckedIds = changeCheckedIds;

	return obj;
}


var setting = {
				check : {
					enable : true,
					chkStyle : $("#chkStyle").val(),
					radioType : "all",
				},
				data : {
					simpleData : {
						enable : true,
						idKey: "id",
						pIdKey: "pId",
					},
					key: {
						name: "name"
					}
				}
};
var zNodes = list_;
var code;
function showCode(str) {
	if (!code) code = $("#code");
	code.empty();
	code.append("<li>" + str + "</li>");
}
$(document).ready(function() {
	$.fn.zTree.init($("#treediv"), setting, zNodes);
	 var treeObj = $.fn.zTree.init($("#treediv"), setting, zNodes);
	 var nodes = treeObj.getNodes();
     for (var i = 0; i < nodes.length; i++) { //设置节点展开  只展开一级节点
        treeObj.expandNode(nodes[i], true, false, true);
     }
});


// 单击时获取zTree节点的Id,和value的值
function zTreeOnClick(event, treeId, treeNode, clickFlag) {
	console.log(treeNode);
};

// 这是将选中的节点的id用;分割拼接起来
function selectedNodeIds() {
	var treeObj = $.fn.zTree.getZTreeObj("treediv");
	var nodes = treeObj.getCheckedNodes();
	var selectedIds = [];
	var selectedNames = [];
	for (var i = 0; i < nodes.length; i++) {
		if (nodes[i].id != null) {
			selectedIds.push( (i == (nodes.length - 1)) ? nodes[i].id : nodes[i].id );
			selectedNames.push( (i == (nodes.length - 1)) ? nodes[i].name : nodes[i].name);
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
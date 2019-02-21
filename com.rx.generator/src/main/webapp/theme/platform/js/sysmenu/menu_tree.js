
var setting = {
	view : {
		showIcon : true
	},
	check : {
		enable : false,
		autoCheckTrigger : false,
		chkStyle : "checkbox",
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
	//console.log(treeNode);
};

// 这是将选中的节点的id用;分割拼接起来
function selectedNodeIds() {
	var treeObj = $.fn.zTree.getZTreeObj("treediv");
	var nodes = treeObj.getCheckedNodes();
	var selectedIds = "";
	for (var i = 0; i < nodes.length; i++) {
		if (nodes[i].id != null) {
			selectedIds += (i == (nodes.length - 1)) ? nodes[i].id : nodes[i].id + ";";
		}
	}
	$("#selectedIds").val(selectedIds);
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

function loadMeunTre(){
	$.ajax({
		url : '/platform/sys/menu_author',
		type : 'post',
		dataType : 'json',
		data : $("#treeparams").serialize(),
		success : function(data) {
			// 加载树
			$.fn.zTree.init($("#treediv"), setting, data);

			// 自动展开所有节点
			var treeObj = $.fn.zTree.getZTreeObj("treediv");
			treeObj.expandAll(true); 
		}
	});
}

$(function() {
	loadMeunTre();
});
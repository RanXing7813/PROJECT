var setting = {
	async : {
		enable : true,
		url : "/platform/sys/dept_tree",// 异步加载时的请求地址
		autoParam : [ "id" ],// 提交参数
		type : 'get',
		dataType : 'json'
	},
	view : {
		showIcon : true
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
	//var treeValue = treeNode.id + "," + treeNode.name;
	//console.log(treeNode);

	var listtype = $("#listtype").val();  
	if (listtype == "user") {
		parent.$("#deptUserList").attr("src", "/platform/sys/userdept_list?deptcode=" + treeNode.deptcode);
	} else if (listtype == "userCheckbox") {
		parent.$("#deptUserList").attr("src", "/platform/sys/userdept_list?type=checkbox&deptcode=" + treeNode.deptcode);
	}else {
		parent.$("#deptList").attr("src", "/platform/sys/dept_list?rootId=" + treeNode.id + "&rootdeptname=" + treeNode.name);
	}
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
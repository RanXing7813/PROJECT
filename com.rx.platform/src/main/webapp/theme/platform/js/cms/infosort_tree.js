var layer=null;
layui.config({
		base : "js/"
	}).use(['layer'], function() {
		layer = layui.layer;
	});

var setting = {
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false
	},
	edit : {
		enable : true,
		editNameSelectAll : true,
		showRemoveBtn : showRemoveBtn,
		showRenameBtn : showRenameBtn
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeDrag : beforeDrag,
		beforeEditName : beforeEditName,
		beforeRemove : beforeRemove,
		beforeRename : beforeRename,
		// onRemove : onRemove,
		onRename : onRename,
		onClick : zTreeOnClick
	}
};

var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}
function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	var zTree = $.fn.zTree.getZTreeObj("treediv");
	zTree.selectNode(treeNode);
	setTimeout(function() {
		var indexNum = top.layer.confirm("确定修改'" + treeNode.name + "'吗？", {
			icon : 3,
			title : '提示信息'
		}, function(indexNum) {
			top.layer.close(indexNum);
			setTimeout(function() {
				zTree.editName(treeNode);
			}, 0);
		})
	}, 0);
	return false;
}

// 删除
function delConfirm(name) {
	var isok = false;
	top.layer.confirm("确认删除'" + name + " '吗？", {
		icon : 3,
		title : '提示信息',
		yes : function(index) {
			isok = true;
			top.layer.close(index);
		},
		cancel : function(index) {
			isok = false;
			top.layer.close(index);
		}
	});
	return isok;
}
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	var zTree = $.fn.zTree.getZTreeObj("treediv");
	zTree.selectNode(treeNode);
	top.layer.confirm("确认删除'" + treeNode.name + " '吗？", {
		icon : 3,
		title : '提示信息',
	}, function(index) {
		
		var zTree = $.fn.zTree.getZTreeObj("treediv");
		zTree.removeNode(treeNode);
		
		//删除数据库中的数据
		delInfoSort(treeNode.id);
		
		top.layer.close(index);
		return true;
	}, function(index) {
		top.layer.close(index);
		return false;
	});
	return false;

}

function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "" : "dark");

	if (newName.length == 0) {
		setTimeout(function() {
			var zTree = $.fn.zTree.getZTreeObj("treediv");
			zTree.cancelEditName();
			top.layer.msg("分类名称不能为空！", {
				icon : 5,
				time : 1500
			});
		}, 0);
		return false;
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
	// console.log("编辑节点 "+treeNode.name);
	var id = treeNode.id;
	var pId = treeNode.pId;
	var name = treeNode.name;
	addInfoSort(id, pId, name)
}
function showRemoveBtn(treeId, treeNode) {
	// console.log(treeNode);
	if (treeNode.level < 1 || treeNode.isParent == true) {// 跟节点或者父节点不能删除
		return false;
	} else {
		return true;
	}

}
function showRenameBtn(treeId, treeNode) {
	if (treeNode.level < 1) {// 跟节点不能编辑
		return false;
	} else {
		return true;
	}
}

function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
			.getSeconds(), ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}
var newCount = 1;
function addHoverDom(treeId, treeNode) {
	if (treeNode.level < 3) {// 最多4级
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
			return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		if (btn) {
			btn.bind("click", function() {
				var zTree = $.fn.zTree.getZTreeObj("treediv");

				var id = generateUUID();
				var pId = treeNode.id;
				var name = "新分类" + (newCount++);

				addInfoSort(id, pId, name);// 保存到数据库

				zTree.addNodes(treeNode, {
					id : id,
					pId : pId,
					name : name
				});
				return false;
			});
		}
	}
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};
function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("treediv");
	zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}
// 单击时获取zTree节点的Id,和value的值
function zTreeOnClick(event, treeId, treeNode, clickFlag) {

	if (!treeNode.isParent) {
		// var treeValue = treeNode.id + "," + treeNode.name;
		parent.$("#infolinkIframe").attr(
				"src",
				"/platform/cms/infolink_list?infosortid=" + treeNode.id
						+ "&infosortname=" + treeNode.name);
	}
	//
};

// 新增
function addInfoSort(id, pid, name) {
	var sort = {
		id : id,
		pid : pid,
		name : name,
		state : '1'
	};
	$.ajax({
		url : '/platform/cms/infosort_save',
		type : 'post',
		dataType : 'json',
		data : sort,
		success : function(data) {
			alert(data);
		}
	});
}

// 删除
function delInfoSort(id) {
	var sort = {
		id : id
	};
	$.ajax({
		url : '/platform/cms/infosort_del',
		type : 'post',
		dataType : 'json',
		data : sort,
		success : function(data) {
			alert(data);
		}
	});
}

// 唯一序列
function generateUUID() {
	var d = new Date().getTime();
	var uuid = 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		var r = (d + Math.random() * 16) % 16 | 0;
		d = Math.floor(d / 16);
		return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
	});
	return uuid;
};

$(function() {
	$.ajax({
		url : '/platform/cms/infosort_tree',
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

});

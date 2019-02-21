/**
 * 组件集合，包括以下功能：
 * 
 * 1、组织机构选择树
 * 
 *2、人员选择树
 * 
 *3、菜单选择树（授权）
 *
 *4、文件上传
 *
 *5、文本编辑器
 *
 *6、组织机构树
 * 
 */

/**
 *  单位选择
 * @param chkStyle 树类型 radio;checkbox 默认radio
 * @param rootId  根节点
 * @param selectedIds  已选择的值  多个用“;”拼接
 * @param idsObjId  回写选中ids的对象ID
 * @param namesObjId  回写选中names的对象ID
 * @returns
 */
function selectDeptByTree(chkStyle, rootId, selectedIds, idsObjId, namesObjId) {
	var type = "select";// 默认参数
	if (chkStyle == null || chkStyle == '') {
		chkStyle = "radio";
	}
	var url = "/platform/sys/dept_tree_page?type=" + type + "&chkStyle=" + chkStyle + "&id=" + rootId + "&selectedIds=" + selectedIds;
	var index = layui.layer.open({
		title : "单位选择树",
		type : 2,
		area : [ '500px', '400px' ],
		btn : [ '确定', '取消' ],
		btnAlign : 'r',// 按钮排列
		content : url,
		success : function(layero, index) {

		},
		yes : function(index, layero) {
			// 当点击‘确定’按钮的时候，获取弹出层返回的值
			var obj = window["layui-layer-iframe" + index].callbackdata();
			$("#" + idsObjId).val(obj.selectedIds);
			$("#" + namesObjId).val(obj.selectedNames);

			// 最后关闭弹出层
			layui.layer.close(index);
		},
		btn2 : function(index, layero) {
			layui.layer.close(index);
		},
		cancel : function() {
			layui.layer.close(index);
		}
	})
}

/**
 * 人员选择
 * @param callbackFunc 回调函数名称
 * @returns
 */
function selectUser(callbackFunc) {

	var url = "/platform/sys/deptuser_select_iframe";
	var index = layui.layer.open({
		title : "人员选择",
		type : 2,
		area : [ '900px', '500px' ],
		btn : [ '确定', '取消' ],
		btnAlign : 'r',// 按钮排列
		content : url,
		success : function(layero, index) {

		},
		yes : function(index, layero) {
			// 当点击‘确定’按钮的时候，获取弹出层返回的值
			var obj = window["layui-layer-iframe" + index].callbackdata();

			callbackFunc(obj);// 回调函数
			// 最后关闭弹出层
			layui.layer.close(index);
		},
		btn2 : function(index, layero) {
			layui.layer.close(index);
		},
		cancel : function() {
			layui.layer.close(index);
		}
	})
}

/**
 *  菜单选择 授权
 * @param roleId 角色ID
 * @param roleName 角色名称
 * @returns
 */
function showAuthMenu(roleId, roleName) {

	var url = "/platform/sys/menu_author_page?roleId=" + roleId;
	var index = layui.layer.open({
		title : "菜单选择（" + roleName + "）",
		type : 2,
		area : [ '400px', '470px' ],
		btn : [ '确定', '取消' ],
		btnAlign : 'r',// 按钮排列
		content : url,
		success : function(layero, index) {

		},
		yes : function(index, layero) {
			// 当点击‘确定’按钮的时候，获取弹出层返回的值
			var obj = window["layui-layer-iframe" + index].callbackdata();
			// 最后关闭弹出层
			layui.layer.close(index);
		},
		btn2 : function(index, layero) {
			layui.layer.close(index);
		},
		cancel : function() {
			layui.layer.close(index);
		}
	})
}

/**
 * 文件上传
 * @param fkid 关联附件的主键
 * @param callbackFunc  回调函数名称
 * @returns
 */
function uploafile(fkid, callbackFunc) {
	var url = "/file/upload_module?fkid=" + fkid;
	var index = layui.layer.open({
		title : "文件上传",
		type : 2,
		area : [ '800px', '470px' ],
		btn : [ '确定', '取消' ],
		btnAlign : 'r',// 按钮排列
		content : url,
		success : function(layero, index) {

		},
		yes : function(index, layero) {
			// 当点击‘确定’按钮的时候，获取弹出层返回的值
			var obj = window["layui-layer-iframe" + index].callbackdata();

			callbackFunc(obj);// 回调函数

			// 最后关闭弹出层
			layui.layer.close(index);
		},
		btn2 : function(index, layero) {
			layui.layer.close(index);
		},
		cancel : function() {
			layui.layer.close(index);
		}
	})
}

/**
 *  组织机构
 * @param chkStyle 树类型 radio;checkbox 默认radio
 * @param rootId  根节点
 * @param selectedIds  已选择的值  多个用“;”拼接
 * @param idsObjId  回写选中ids的对象ID
 * @param namesObjId  回写选中names的对象ID
 * @returns
 */
function selectOfficeByTree(chkStyle, rootId, selectedIds, idsObjId, namesObjId) {
	var type = "select";// 默认参数
	if (chkStyle == null || chkStyle == '') {
		chkStyle = "radio";
	}
	var url = "/platform/sys/office_tree_page?type=" + type + "&chkStyle=" + chkStyle + "&id=" + rootId + "&selectedIds=" + selectedIds;
	var index = layui.layer.open({
		title : "组织机构选择树",
		type : 2,
		area : [ '500px', '400px' ],
		btn : [ '确定', '取消' ],
		btnAlign : 'r',// 按钮排列
		content : url,
		success : function(layero, index) {

		},
		yes : function(index, layero) {
			// 当点击‘确定’按钮的时候，获取弹出层返回的值
			var obj = window["layui-layer-iframe" + index].callbackdata();
			$("#" + idsObjId).val(obj.selectedIds);
			$("#" + namesObjId).val(obj.selectedNames);

			// 最后关闭弹出层
			layui.layer.close(index);
		},
		btn2 : function(index, layero) {
			layui.layer.close(index);
		},
		cancel : function() {
			layui.layer.close(index);
		}
	})
}

/**
 *  菜单选择 新增菜单
 * @returns
 */
function showMenu(idsObjId, namesObjId) {

	var url = "/platform/sys/menu_select_page";
	var index = layui.layer.open({
		title : "菜单选择",
		type : 2,
		area : [ '400px', '470px' ],
		btn : [ '确定', '取消' ],
		btnAlign : 'r',// 按钮排列
		content : url,
		success : function(layero, index) {

		},
		yes : function(index, layero) {
			// 当点击‘确定’按钮的时候，获取弹出层返回的值
			var obj = window["layui-layer-iframe" + index].callbackdata();
			
			console.log(obj);
			
			$("#" + idsObjId).val(obj.selectedIds);
			$("#" + namesObjId).val(obj.selectedNames);
			// 最后关闭弹出层
			layui.layer.close(index);
		},
		btn2 : function(index, layero) {
			layui.layer.close(index);
		},
		cancel : function() {
			layui.layer.close(index);
		}
	})
}

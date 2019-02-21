$(function() {
	
	$("#deptname").click(function() {
		var deptid = $("#deptid").val();
		selectDeptByTree("radio", "0", deptid, "deptid", "deptname");
	});

	layui.config({
		base : "theme/js/"
	}).use([ 'form', 'layer', 'jquery', 'layedit', 'laydate'], function() {
		var form = layui.form, layer = parent.layer === undefined ? layui.layer : parent.layer;
		form.on("submit(addUser)", function(data) {
			$(".layui-form").submit();
		});
		// 关闭
		$(".closeCurPage").click(function() {
			layer.closeAll("iframe");
		});
	});

});

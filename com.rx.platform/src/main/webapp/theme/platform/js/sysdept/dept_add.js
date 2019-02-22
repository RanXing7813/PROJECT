$(function() {

	$("#pdeptname").click(function() {
		selectDeptByTree("radio", "0", "", "pdeptid", "pdeptname");
	});

	layui.config({
		base : "theme/js/"
	}).use([ 'form', 'layer', 'jquery', 'layedit', 'laydate' ], function() {
		var form = layui.form, layer = parent.layer === undefined ? layui.layer : parent.layer;
		form.on("submit(addDept)", function(data) {
			$(".layui-form").submit();
		});
		// 关闭
		$(".closeCurPage").click(function() {
			layer.closeAll("iframe");
		});
	});
	
});

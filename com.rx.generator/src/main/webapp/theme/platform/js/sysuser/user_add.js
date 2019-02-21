$(function() {

	// 重名校验
	$("#loginname").blur(function() {
		$.ajax({
			url : "/platform/sys/user_check",
			dataType : "json",
			data : $('.layui-form').serialize(),
			type : "post",
			success : function(result) {
				if (result.state == '1') {
					top.layer.msg(result.msg, {
						icon : 5,
						time : 1000
					});
					$("#loginname").focus();
				}
			},
			error : function(result) {
			}
		});
	});
	$("#deptname").click(function() {
		selectDeptByTree("radio", "0", "", "deptid", "deptname");
	});

	layui.config({
		base : "theme/js/"
	}).use([ 'form', 'layer', 'jquery', 'layedit', 'laydate' ], function() {
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

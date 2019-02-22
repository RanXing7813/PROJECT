$(function() {

	// 重名校验
	$("#roleName").blur(function() {
		$.ajax({
			url : "/platform/sys/rolename_check",
			dataType : "json",
			data : $('.layui-form').serialize(),
			type : "post",
			success : function(result) {
				console.log(result);
				if (result.state == '1') {
					top.layer.msg(result.msg, {
						icon : 5,
						time : 1000
					});
					$("#roleName").focus();
				}
			},
			error : function(result) {
			}
		});
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

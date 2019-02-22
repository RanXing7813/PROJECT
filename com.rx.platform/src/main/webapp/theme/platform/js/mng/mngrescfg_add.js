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

	/*$("#sourcename").click(function() {
		selectDeptByTree("radio", "0", "", "sourcecode", "sourcename");
	});*/
	// 处理 radio
	var type = $(":radio[name='type']:checked").val();
	initUrl(type);
	function initUrl(type) {
		if (type == '0') {
			$("#reslable").html('<span style="color: red;">*</span>资源库表名称');
		} else if (type == '1') {
			$("#reslable").html('<span style="color: red;">*</span>文件路径');
		} else {
			$("#reslable").html('<span style="color: red;">*</span>文件夹（路径）');
		}
	}
	layui.config({
		base : "theme/js/"
	}).use([ 'form', 'layer', 'jquery', 'layedit', 'laydate' ], function() {
		var form = layui.form, layer = parent.layer === undefined ? layui.layer : parent.layer;
		form.on("submit(addUser)", function(data) {
			$(".layui-form").submit();
		});
		form.on('radio(type)', function(data) {
			initUrl(data.value);
		});
	});

});

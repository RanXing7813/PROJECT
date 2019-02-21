layui.config({
	base : "js/"
}).use([ 'form', 'layer', 'jquery' ], function() {
	var form = layui.form, layer = parent.layer === undefined ? layui.layer : parent.layer, $ = layui.jquery;
	// video背景
	$(window).resize(function() {
		if ($(".video-player").width() > $(window).width()) {
			$(".video-player").css({
				"height" : $(window).height(),
				"width" : "auto",
				"left" : -($(".video-player").width() - $(window).width()) / 2
			});
		} else {
			$(".video-player").css({
				"width" : $(window).width(),
				"height" : "auto",
				"left" : -($(".video-player").width() - $(window).width()) / 2
			});
		}
	}).resize();

	// 登录按钮事件
	form.on("submit(login)", function(data) {

		// 弹出loading
		var index = layer.msg('数据提交中，请稍候', {
			icon : 16,
			time : false,
			shade : 0.8
		});
		
		$.ajax({
			url : "/login",
			dataType : "json",
			data : $('.layui-form').serialize(),
			type : "post",
			success : function(result) {

				if (result.state == '0') {
					layer.close(index);
					layer.msg(result.msg, {
						icon : 1,
						time : 200
					}, function() {
						window.location.href = "gxwz";// 登录成功后跳转到首页
					});

				} else {
					layer.close(index);
					$("#verifycodea").click();
					layer.msg(result.msg, {
						icon : 5,
						time : 1000
					});
				}
			},
			error : function(result) {
			}
		});
		return false;
	})
})

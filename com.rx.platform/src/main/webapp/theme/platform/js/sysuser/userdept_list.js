layui.config({
		base : "js/"
	}).use([ 'form', 'layer', 'jquery', 'laypage', 'element' ], function() {
		var form = layui.form, layer = parent.layer === undefined ? layui.layer : parent.layer, laypage = layui.laypage, $ = layui.jquery;
		var element = layui.element;
		// 查询
		$(".search_btn").click(function() {
			$("#search_form").submit();
		});

		// 批量删除
		$(".batchDel").click(function() {
			var $checkbox = $('.news_list tbody input[type="checkbox"][name="checked"]');
			var $checked = $('.news_list tbody input[type="checkbox"][name="checked"]:checked');
			if ($checkbox.is(":checked")) {
				layer.confirm('确定删除选中的信息？', {
					icon : 3,
					title : '提示信息'
				}, function(index) {
					var index = layer.msg('删除中，请稍候', {
						icon : 16,
						time : false,
						shade : 0.8
					});

					$.ajax({
						url : "/platform/sys/auth_removeuser",
						dataType : "json",
						data : $('#search_form').serialize(),
						type : "post",
						success : function(result) {
							if (result.state == '0') {
								top.layer.close(index);
								top.layer.msg(result.msg, {
									icon : 6,
									time : 500
								});
								$("#search_form").submit();

							} else {
								top.layer.msg(result.msg, {
									icon : 5,
									time : 1000
								});
							}
						},
						error : function(result) {
						}
					});

				})
			} else {
				layer.msg("请选择需要移除的用户", {
					icon : 6,
					time : 1000
				});
			}
		});
		// 关闭
		$(".closeCurPage").click(function() {
			// layer.confirm('确定关闭当前页面吗？', {
			// icon : 3,
			// title : '提示信息'
			// }, function(index) {
			// layer.closeAll("iframe");
			// parent.location.reload();//刷新父页面
			// })
			layer.closeAll("iframe");
			parent.location.reload();// 刷新父页面
		});

	});


	//选择框回调函数 弹出层的返回值
	function callbackdata() {
	
		var $ = layui.jquery;
		var str = "";
		var cids = $('input[name="checked"]:checked');
		for (var i = 0; i < cids.length; i++) {
			if (i == 0) {
				str += $(cids[i]).val();
			} else {
				str += ";" + $(cids[i]).val();
			}
		}
		var obj = new Object();
		obj.selectedIds = str;
	
		return obj;
	}

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
			var $checkbox = $('tbody input[type="checkbox"][name="checked"]');
			var $checked = $('tbody input[type="checkbox"][name="checked"]:checked');
			if ($checkbox.is(":checked")) {
				layer.confirm('确定删除选中的信息？', {
					icon : 3,
					title : '提示信息'
				}, function(index) {
					
					layer.close(index)
					
//					var index = layer.msg('删除中，请稍候', {
//						icon : 16,
//						time : false,
//						shade : 0.8
//					});

					$.ajax({
						url : "/platform/sys/auth_removeuser",
						dataType : "json",
						data : $('#search_form').serialize(),
						type : "post",
						success : function(result) {
							if (result.state == '0') {
								//top.layer.close(index);
								top.layer.msg(result.msg, {
									icon : 6,
									time : 1000
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

		$(".newsAdd_btn").click(function() {
			selectUser(saveAuthor);
		});

		// 保存授权
		function saveAuthor(obj) {
			if (obj.selectedIds != null && obj.selectedIds != '') {
				$("#selectedIds").val(obj.selectedIds);

				$.ajax({
					url : "/platform/sys/auth_adduser",
					dataType : "json",
					data : $('#search_form').serialize(),
					type : "post",
					success : function(result) {
						if (result.state == '0') {
							top.layer.msg(result.msg, {
								icon : 6,
								time : 1000
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
			}
		}

	});



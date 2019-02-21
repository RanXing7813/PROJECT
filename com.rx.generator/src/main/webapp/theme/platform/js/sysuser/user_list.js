
	layui.config({
		base : "js/"
	}).use([ 'form', 'layer', 'jquery', 'laypage', 'element' ], function() {
		var form = layui.form, layer = layui.layer, laypage = layui.laypage, $ = layui.jquery;
		var element = layui.element;
		// 查询
		$(".search_btn").click(function() {
			$("#search_form").submit();
		});

		// 改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).one("resize", function() {
			$(".newsAdd_btn").click(function() {
				var indexNum = layer.open({
					title : "新增用户",
					type : 2,
					content : "/platform/sys/user_add",
					success : function(layero, indexNum) {
						setTimeout(function() {
							layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
								tips : 3
							});
						}, 500)
					}
				})
				layer.full(indexNum);
			})
		}).resize();

		// 批量删除
		$(".batchDel").click(function() {
			var $checkbox = $('tbody input[type="checkbox"][name="checked"]');
			var $checked = $('tbody input[type="checkbox"][name="checked"]:checked');
			if ($checkbox.is(":checked")) {
				layer.confirm('确定删除选中的信息？', {
					icon : 3,
					title : '提示信息'
				}, function(indexNum) {
					var indexNum = layer.msg('删除中，请稍候', {
						icon : 16,
						time : false,
						shade : 0.8
					});

					$.ajax({
						url : "/platform/sys/user_del",
						dataType : "json",
						data : $('#search_form').serialize(),
						type : "post",
						success : function(result) {
							if (result.state == '0') {
								top.layer.close(indexNum);
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
				layer.msg("请选择需要删除的用户", {
					icon : 6,
					time : 1000
				});
			}
		});
		
		// 用户编辑
		$(".edit").click(function() {
			var id = this.alt;// 编辑用户的ID
			var indexNum = layui.layer.open({
				title : "编辑用户",
				type : 2,
				content : "/platform/sys/user_edit?id=" + id,
				success : function(layero, indexNum) {
					setTimeout(function() {
						layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
							tips : 3
						});
					}, 500)
				}
			})
			layui.layer.full(indexNum);

		});

		// 重置密码
		$(".resetPw").click(function() {

			var id = $(this).attr("alt");// 编辑用户的ID
			var username = $(this).attr("dir");// 用户的ID
			
			$("#id").val(id);

			var tip = '确定将' + username + '的密码重置为:Aa123123 吗？';

			layer.confirm(tip, {
				icon : 3,
				title : '提示信息'
			}, function(indexNum) {

				var indexNum = layer.msg('重置中，请稍候', {
					icon : 16,
					time : false,
					shade : 0.8
				});

				$.ajax({
					url : "/platform/sys/user_resetpw",
					dataType : "json",
					data : $('#search_form').serialize(),
					type : "post",
					success : function(result) {
						if (result.state == '0') {
							layer.close(indexNum);
							layer.msg(username + " " + result.msg, {
								icon : 6,
								time : 1000
							});

						} else {
							layer.close(indexNum);
							layer.msg(result.msg, {
								icon : 5,
								time : 3000
							});
						}
					},
					error : function(result) {
					}
				});

			})
		});

		// 全选
		form.on('checkbox(allChoose)', function(data) {
			var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
			child.each(function(index, item) {
				item.checked = data.elem.checked;
			});
			form.render('checkbox');
		});

		// 通过判断是否全部选中来确定全选按钮是否选中
		form.on("checkbox(choose)", function(data) {
			var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
			var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked')
			if (childChecked.length == child.length) {
				$(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
			} else {
				$(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
			}
			form.render('checkbox');
		});

	});


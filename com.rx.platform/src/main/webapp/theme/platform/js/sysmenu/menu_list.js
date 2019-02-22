
layui.config({
		base : "js/"
	}).use([ 'form', 'layer', 'jquery', 'laypage', 'element' ], function() {
		var form = layui.form, layer = parent.layer === undefined ? layui.layer : parent.layer, laypage = layui.laypage, $ = layui.jquery;
		var element = layui.element;
		// 查询
		$(".search_btn").click(function() {
			$("#search_form").submit();
		});
		
		//编辑
		$(".edit").click(function() {
			top.layer.msg("程序猿太忙，还没开发完成。。。", {
				icon : 5,
				time : 1500
			});
		});
		//刷新左侧菜单
		$(".udpateMenu").click(function() {
			parent.loadMeunTre();
			top.layer.msg("刷新左侧菜单完成！", {
				icon : 1,
				time : 1500
			});
		});
		
		$(".del").click(function() {
			var menuId = $(this).attr("alt");// 角色的ID  
			$("#menuId").val(menuId);
			if (true) {
				top.layer.confirm('确定删除选中的信息？', {
					icon : 3,
					title : '提示信息'
				}, function(index) {
					var indexNum = top.layer.msg('删除中，请稍候', {
						icon : 16,
						time : false,
						shade : 0.8
					});

					$.ajax({
						url : "/platform/sys/menu_del",
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
				layer.msg("请选择需要删除的角色", {
					icon : 6,
					time : 1000
				});
			}
		})
		
		// 改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).one("resize", function() {
			$(".newsAdd_btn").click(function() {
				var indexNum = layer.open({
					title : "新增菜单",
					type : 2,
					content : "/platform/sys/menu_add",
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

	});	


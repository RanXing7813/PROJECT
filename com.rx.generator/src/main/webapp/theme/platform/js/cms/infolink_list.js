
	layui.config({
		base : "js/"
	}).use([ 'form', 'layer', 'jquery', 'laypage', 'element' ], function() {
		var $ = layui.$,form = layui.form, layer = layui.layer, laypage = layui.laypage;
		var element = layui.element;
		// 查询
		$(".search_btn").click(function() {
			$("#search_form").submit();
		});

		// 改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).one("resize", function() {
			$(".newsAdd_btn").click(function() {
				if($("#infosortid").val()==''||$("#infosortid").val()==null){
					top.layer.msg("请先选择信息分类！", {
						icon : 5,
						time : 1500
					});
				}else{
					var indexNum = layer.open({
						title : "信息编辑",
						type : 2,
						shade: false,
				        maxmin: true,
				        area: ['90%', '90%'],
						content : "/platform/cms/infolink_add?infosortid="+$("#infosortid").val(),
						success : function(layero, indexNum) {

						}
					})
					layer.full(indexNum);
				}
			});
			// 编辑
			$(".edit").click(function() {
				var infolinkid = $(this).attr("alt");
				var indexNum = layui.layer.open({
					title : "信息编辑",
					type : 2,
					content : "/platform/cms/infolink_edit?infolinkid="+infolinkid,
					success : function(layero, indexNum) {

					}
				})
				layui.layer.full(indexNum);

			});
			// 查看
			$(".view").click(function() {
				var infolinkid = $(this).attr("alt");
				var indexNum = layui.layer.open({
					title : "信息编辑",
					type : 2,
					content : "/platform/cms/infolink_view?infolinkid="+infolinkid,
					success : function(layero, indexNum) {

					}
				})
				layui.layer.full(indexNum);

			});
		}).resize();
		
		//批量发布
		$(".batchFb").click(function() {
			var $checkbox = $('tbody input[type="checkbox"][name="checked"]');
			var $checked = $('tbody input[type="checkbox"][name="checked"]:checked');
			if ($checkbox.is(":checked")) {
				layer.confirm('确定发布选中的信息？', {
					icon : 3,
					title : '提示信息'
				}, function(indexNum) {
					$.ajax({
						url : "/platform/cms/infolink_publish",
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
				layer.msg("请选择需要发布的信息", {
					icon : 6,
					time : 1000
				});
			}
		});

		// 批量删除
		$(".batchDel").click(function() {
			var $checkbox = $('tbody input[type="checkbox"][name="checked"]');
			var $checked = $('tbody input[type="checkbox"][name="checked"]:checked');
			if ($checkbox.is(":checked")) {
				layer.confirm('确定删除选中的信息？', {
					icon : 3,
					title : '提示信息'
				}, function(indexNum) {
					
					$.ajax({
						url : "/platform/cms/infolink_del",
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
				layer.msg("请选择需要删除的信息", {
					icon : 6,
					time : 1000
				});
			}
		});

	});


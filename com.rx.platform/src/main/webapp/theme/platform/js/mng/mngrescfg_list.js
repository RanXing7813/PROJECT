$(function() {

	layui.config({
		base : "js/"
	}).use([ 'form', 'layer', 'jquery', 'laypage', 'element' ], function() {
		var form = layui.form, layer = parent.layer === undefined ? layui.layer : parent.layer, laypage = layui.laypage, $ = layui.jquery;
		var element = layui.element;
		// 查询
		$(".search_btn").click(function() {
			$("#search_form").submit();
		});

		// 改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).one("resize", function() {
			$("#addres").click(function() {
				var index = layui.layer.open({
					title : "新增资源",
					type : 2,
					content : "/platform/mng/mngrescfg_add",
					success : function(layero, index) {
						setTimeout(function() {
							layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
								tips : 3
							});
						}, 500)
					}
				})
				layui.layer.full(index);
			});
			
			// 用户编辑
			$(".edit").click(function() {
				var id = this.alt;// 编辑用户的ID
				var index = layui.layer.open({
					title : "编辑用户",
					type : 2,
					content : "/platform/mng/mngrescfg_edit?id=" + id,
					success : function(layero, index) {
						setTimeout(function() {
							layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
								tips : 3
							});
						}, 500)
					}
				})
				layui.layer.full(index);

			});
		}).resize();
		
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
						url : "/platform/mng/mngrescfg_del",
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
				layer.msg("请选择需要删除的资源", {
					icon : 6,
					time : 1000
				});
			}
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

		// 分页
		function page(limit, count, curr) {
			laypage.render({
				elem : "page",
				limit : limit,
				curr : curr,
				pages : Math.ceil(count / limit),
				count : count,
				layout : [ 'count', 'prev', 'page', 'next', 'limit', 'skip' ],
				jump : function(obj, first) { // 点击下一页
					// 首次不执行
					if (!first) {
						$("#currentpage").val(obj.curr);// 到第几页
						$("#pagesize").val(obj.limit);// 得到每页显示的条数
						$("#search_form").submit();
					}
				}
			});
		}

		var pagesize = $("#pagesize").val();
		var datacount = $("#datacount").val();
		var currentpage = $("#currentpage").val();

		page(pagesize, datacount, currentpage);

	});

})

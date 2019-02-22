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
			$(".newsAdd_btn").click(function() {
				var indexNum = layer.open({
					title : "新增机构",
					type : 2,
					content : "/platform/sys/dept_add",
					success : function(layero, index) {
						setTimeout(function() {
							layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
								tips : 3
							});
						}, 500)
					}
				})
				layui.layer.full(indexNum);
			})
		}).resize();

	});

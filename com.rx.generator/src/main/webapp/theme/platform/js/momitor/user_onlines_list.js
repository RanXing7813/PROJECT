
	layui.config({
		base : "js/"
	}).use([ 'form', 'layer', 'jquery', 'laypage', 'element' ], function() {
		var form = layui.form, layer = parent.layer === undefined ? layui.layer : parent.layer, laypage = layui.laypage, $ = layui.jquery;
		var element = layui.element;
		// 查询
		$(".search_btn").click(function() {
			$("#search_form").submit();
		});
		
		// 下线
		$(".kickout").click(function() {
			var id = this.alt;// seesion的ID
			$.ajax({
				url : "/platform/outlines?id="+id,
				dataType : "json",
				data : [],
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

		});
	});


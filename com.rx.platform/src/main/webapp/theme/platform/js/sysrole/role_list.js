
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
				var index = layui.layer.open({
					title : "新增角色",
					type : 2,
					content : "/platform/sys/role_add",
					success : function(layero, index) {
						setTimeout(function() {
							layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
								tips : 3
							});
						}, 500)
					}
				})
				layui.layer.full(index);
			})
		}).resize();
		
		// 删除角色
		$(".roleDel").click(function() {
			var roleId = this.alt;// 角色的ID  
			$("#roleId").val(roleId);
			if (true) {
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
						url : "/platform/sys/role_del",
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
				layer.msg("请选择需要删除的角色", {
					icon : 6,
					time : 1000
				});
			}
		});
		
		var curindex;//弹出层的ID

		// 用户角色授权列表
		$(".authuser").click(function() {
			var roleid = $(this).attr("alt");// 角色ID
			var rolename = $(this).attr("dir");// 角色名称 

			var url = "/platform/sys/userrole_list?roleId=" + roleid + "&rolename" + rolename;

			var index = layui.layer.open({
				title : "用户角色授权（"+rolename+"）",
				type : 2,
				content : url,
				success : function(layero, index) {
//					setTimeout(function() {
//						layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
//							tips : 3
//						});
//					}, 500)
				}
			});
			layui.layer.full(index);
			
		});
		
		// 角色菜单授权
		$(".authmenu").click(function() {
			var roleid = $(this).attr("alt");// 角色ID
			var rolename = $(this).attr("dir");// 角色名称 
			showAuthMenu(roleid,rolename);
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
						$("#pagesize").val(obj.limit);//得到每页显示的条数
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

var $, tab, skyconsWeather;

layui.config({
	base : "theme/platform/js/index/"// bodyTab的路径
}).use(
		[ 'bodyTab', 'form', 'element', 'layer', 'jquery' ],
		function() {
			var form = layui.form, layer = layui.layer, element = layui.element;
			$ = layui.jquery;
			tab = layui.bodyTab({
				openTabNum : "15", // 最大可打开窗口数量
				url : "/platform/sys/menu_tree.json" // 获取菜单json地址
			});

			// 用户编辑
			$(".view").click(function() {
				var id = $(this).attr("dir");// 用户的ID
				var index = layer.open({
					title : "个人信息",
					area : [ "500px", "300px" ],
					type : 2,
					content : "/platform/sys/user_view?id=" + id,
					success : function(layero, index) {
						setTimeout(function() {
							layui.layer.tips('点击此处关闭页面', '.layui-layer-setwin .layui-layer-close', {
								tips : 3
							});
						}, 500)
					}
				})
			});
			// 修改密码
			$(".changePwd").click(
					function() {
						var id = $(this).attr("dir");// 用户的ID
						var username = $(this).attr("lang");// 用户的姓名

						var title = "修改密码（用户名：" + username + "）";

						var content = '<div class="skins_box">' + '<form class="layui-form"><input type="hidden" name="id" value="' + id + '" />' + '<div class="layui-form-item">'
								+ '<div class="skinCustom" style="visibility: visible;">'
								+ '<input type="text" class="layui-input topColor" id="password1" name="password1" placeholder="请输入新密码" lay-verify="required|pass" style="width: 80%;"/>' + '</div>'
								+ '<div class="skinCustom" style="visibility: visible;">'
								+ '<input type="text" class="layui-input menuColor" id="password2" name="password2" placeholder="请重复输入密码" lay-verify="required|pass|checkpass" style="width: 80%;"/>'
								+ '</div></div><div class="layui-form-item skinBtn" style="text-align: center;">'
								+ '<a href="javascript:;" class="layui-btn layui-btn-small layui-btn-normal" lay-submit="" lay-filter="change">确定修改</a>' + '</div>' + '</form></div>'

						var index = top.layer.open({
							title : title,
							area : [ "310px", "220px" ],
							type : "1",
							content : content,
							success : function(index, layero) {
								form.on("submit(change)", function(data) {

									$.ajax({
										url : "/platform/sys/user_changePwd",
										dataType : "json",
										data : $('.layui-form').serialize(),
										type : "post",
										success : function(result) {
											if (result.state == '1') {
												top.layer.msg(result.msg, {
													icon : 5,
													time : 1000
												});
											} else {
												top.layer.msg(result.msg, {
													icon : 6,
													time : 1000
												});
												top.layer.closeAll("page");
											}

										},
										error : function(result) {
										}
									});
								});
							},
							cancel : function() {

							}
						})
					});

			// 退出
			$(".signOut").click(function() {
				window.sessionStorage.removeItem("menu");
				menu = [];
				window.sessionStorage.removeItem("curmenu");
			})

			// 隐藏左侧导航
			window.sessionStorage.setItem("isHide", false);// 默认展开
			$(".hideMenu").click(function() {
				$(".layui-layout-admin").toggleClass("showMenu");
				if (window.sessionStorage.getItem("isHide") == 'true') {
					window.sessionStorage.setItem("isHide", false);
					$(".hideMenu").html('<img src="theme/platform/images/icons/close_m_16.png" title="收起"/>');
				} else {
					window.sessionStorage.setItem("isHide", true);
					$(".hideMenu").html('<img src="theme/platform/images/icons/open_m_16.png" title="展开"/>');
				}

				// 渲染顶部窗口
				tab.tabMove();
			})

			// 渲染左侧菜单
			tab.render();

			$(document).on('keydown', function() {
				if (event.keyCode == 13) {
					$("#unlock").click();
				}
			});

			// 添加新窗口
			$("body").on("click", ".layui-nav .layui-nav-item a", function() {
				// 如果不存在子级
				if ($(this).siblings().length == 0) {
					addTab($(this));
					$('body').removeClass('site-mobile'); // 移动端点击菜单关闭菜单层
				}
				$(this).parent("li").siblings().removeClass("layui-nav-itemed");
			})

			// 刷新后还原打开的窗口
			if (window.sessionStorage.getItem("menu") != null) {
				menu = JSON.parse(window.sessionStorage.getItem("menu"));
				curmenu = window.sessionStorage.getItem("curmenu");
				var openTitle = '';
				for (var i = 0; i < menu.length; i++) {
					openTitle = '';
					if (menu[i].icon) {
						if (menu[i].icon.split("-")[0] == 'icon') {
							openTitle += '<i class="iconfont ' + menu[i].icon + '"><img src="theme/platform/css/icons/application_home.png" /></i>';
						} else {
							openTitle += '<i class="layui-icon"><img src="theme/platform/css/icons/application_home.png" /></i>';
						}
					}
					openTitle += '<cite>' + menu[i].title + '</cite>';
					openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="' + menu[i].layId + '"><img src="theme/platform/css/icons/cross.png" /></i>';
					element.tabAdd("bodyTab", {
						title : openTitle,
						content : "<iframe src='" + menu[i].href + "' data-id='" + menu[i].layId + "'></frame>",
						id : menu[i].layId
					})
					// 定位到刷新前的窗口
					if (curmenu != "undefined") {
						if (curmenu == '' || curmenu == "null") { // 定位到后台首页
							element.tabChange("bodyTab", '');
						} else if (JSON.parse(curmenu).title == menu[i].title) { // 定位到刷新前的页面
							element.tabChange("bodyTab", menu[i].layId);
						}
					} else {
						element.tabChange("bodyTab", menu[menu.length - 1].layId);
					}
				}
				// 渲染顶部窗口
				tab.tabMove();
			}

			// 刷新当前
			$(".refresh").on("click", function() { // 此处添加禁止连续点击刷新一是为了降低服务器压力，另外一个就是为了防止超快点击造成chrome本身的一些js文件的报错(不过貌似这个问题还是存在，不过概率小了很多)
				if ($(this).hasClass("refreshThis")) {
					$(this).removeClass("refreshThis");
					$(".clildFrame .layui-tab-item.layui-show").find("iframe")[0].contentWindow.location.reload(true);
					setTimeout(function() {
						$(".refresh").addClass("refreshThis");
					}, 2000)
				} else {
					layer.msg("您点击的速度超过了服务器的响应速度，还是等两秒再刷新吧！");
				}
			})

			// 关闭其他
			$(".closePageOther").on("click", function() {
				if ($("#top_tabs li").length > 2 && $("#top_tabs li.layui-this cite").text() != "首页") {
					var menu = JSON.parse(window.sessionStorage.getItem("menu"));
					$("#top_tabs li").each(function() {
						if ($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")) {
							element.tabDelete("bodyTab", $(this).attr("lay-id")).init();
							// 此处将当前窗口重新获取放入session，避免一个个删除来回循环造成的不必要工作量
							for (var i = 0; i < menu.length; i++) {
								if ($("#top_tabs li.layui-this cite").text() == menu[i].title) {
									menu.splice(0, menu.length, menu[i]);
									window.sessionStorage.setItem("menu", JSON.stringify(menu));
								}
							}
						}
					})
				} else if ($("#top_tabs li.layui-this cite").text() == "首页" && $("#top_tabs li").length > 1) {
					$("#top_tabs li").each(function() {
						if ($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")) {
							element.tabDelete("bodyTab", $(this).attr("lay-id")).init();
							window.sessionStorage.removeItem("menu");
							menu = [];
							window.sessionStorage.removeItem("curmenu");
						}
					})
				} else {
					layer.msg("没有可以关闭的窗口啦！", {
						icon : 6,
						time : 1000
					});
				}
				// 渲染顶部窗口
				tab.tabMove();
			})
			// 关闭全部
			$(".closePageAll").on("click", function() {
				if ($("#top_tabs li").length > 1) {
					$("#top_tabs li").each(function() {
						if ($(this).attr("lay-id") != '') {
							element.tabDelete("bodyTab", $(this).attr("lay-id")).init();
							window.sessionStorage.removeItem("menu");
							menu = [];
							window.sessionStorage.removeItem("curmenu");
						}
					})
				} else {
					layer.msg("没有可以关闭的窗口啦！", {
						icon : 6,
						time : 1000
					});
				}
				// 渲染顶部窗口
				tab.tabMove();
			})
		})

// 打开新窗口
function addTab(_this) {
	tab.tabAdd(_this);
}

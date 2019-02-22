function navBar(strData) {
	var data;
	if (typeof (strData) == "string") {
		var data = JSON.parse(strData); // 部分用户解析出来的是字符串，转换一下
	} else {
		data = strData;
	}
	var ulHtml = '<ul class="layui-nav layui-nav-tree">';
	for (var i = 0; i < data.length; i++) {
		if (data[i].spread) {
			ulHtml += '<li class="layui-nav-item layui-nav-itemed">';
		} else {
			ulHtml += '<li class="layui-nav-item">';
		}
		if (data[i].children != undefined && data[i].children.length > 0) {
			ulHtml += '<a href="javascript:;">';
			ulHtml += '<i class="layui-icon" data-icon="icon-text"><img src="' + data[i].imgpath + '" /></i>';
			ulHtml += '<cite>' + data[i].title + '</cite>';
			ulHtml += '<span class="layui-nav-more"></span>';
			ulHtml += '</a>';
			ulHtml += '<ul class="layui-nav-child">';
			for (var j = 0; j < data[i].children.length; j++) {

				var fourthMenu = data[i].children[j];
				if (fourthMenu.children != undefined && fourthMenu.children.length > 0) {
					if (fourthMenu.spread) {
						ulHtml += '<li class="layui-nav-item layui-nav-itemed">';
					} else {
						ulHtml += '<li class="layui-nav-item">';
					}
					ulHtml += '<a href="javascript:;">';
					ulHtml += '<i class="layui-icon" data-icon="icon-text"><img src="' + fourthMenu.imgpath + '" /></i>';
					ulHtml += '<cite>' + fourthMenu.title + '</cite>';
					ulHtml += '<span class="layui-nav-more"></span>';
					ulHtml += '</a>';
					ulHtml += '<ul class="layui-nav-child">';

					for (var k = 0; k < fourthMenu.children.length; k++) {
						if (fourthMenu.children[k].target == "_blank") {
							ulHtml += '<li><a href="javascript:;" data-url="' + fourthMenu.children[k].href + '" target="' + fourthMenu.children[k].target + '">';
						} else {
							ulHtml += '<li><a href="javascript:;" data-url="' + fourthMenu.children[k].href + '">';
						}
						ulHtml += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="layui-icon" data-icon="icon-text"><img src="' + fourthMenu.children[k].imgpath + '" /></i>';
						ulHtml += '<cite>' + fourthMenu.children[k].title + '</cite></a></li>';
					}
					ulHtml += "</ul>";
					ulHtml += "</li>";
				} else {
					if (fourthMenu.target == "_blank") {
						ulHtml += '<li><a href="javascript:;" data-url="' + fourthMenu.href + '" target="' + fourthMenu.target + '">';
					} else {
						ulHtml += '<li><a href="javascript:;" data-url="' + fourthMenu.href + '">';
					}
					ulHtml += '<i class="layui-icon" data-icon="icon-text"><img src="' + fourthMenu.imgpath + '" /></i>';
					ulHtml += '<cite>' + fourthMenu.title + '</cite></a></li>';
				}
			}
			ulHtml += "</ul>";
		} else {
			if (data[i].target == "_blank") {
				ulHtml += '<a href="javascript:;" data-url="' + data[i].href + '" target="' + data[i].target + '">';
			} else {
				ulHtml += '<a href="javascript:;" data-url="' + data[i].href + '">';
			}
			ulHtml += '<i class="layui-icon" data-icon="icon-text"><img src="' + data[i].imgpath + '" /></i>';
			ulHtml += '<cite>' + data[i].title + '</cite></a>';
		}
		ulHtml += '</li>';
	}
	ulHtml += '</ul>';

	return ulHtml;
}

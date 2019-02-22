
	layui.config({
		base : '/theme/layuiadmin/' //静态资源所在路径
	}).extend({
		index : 'lib/index' //主入口模块
	}).use([ 'index','form', 'layer', 'jquery', 'laypage', 'element' ], function() {
		var $ = layui.$,form = layui.form, layer = layui.layer, laypage = layui.laypage;
		var element = layui.element;
		
		$(".RefreshPage").click(function() {
			document.location.reload();
			top.layer.msg("刷新页面成功！", {
				icon : 1,
				time : 1500
			});
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

		// 分页开始
		function page(limit, count, curr) {
			laypage.render({
				elem : "page",
				limit : limit,
				limits:[5, 10, 20, 50, 100],
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
		
		// 分页结束
		
		 //列表补充空行 start
		var trCount = $('tbody').children('tr').length;
		//var tdCount = $($('thead').children('tr').get(0)).children('th').length;//已表头的列数为准，避免在无数据的情况下补充空行失败
		var tdCount = $($("thead tr:first")).children('th').length;//已表头的列数为准，避免在无数据的情况下补充空行失败
		var blankTR = parseInt(pagesize)-parseInt(trCount);

		var trHTML = "<tr>";
		for(var i=0;i<tdCount;i++){
			trHTML += "<td><span></span></td>";
		}
		trHTML += "</tr>";

		for(var i=0;i<blankTR;i++){
			$('tbody').append(trHTML);
		}

		//列表补充空行 end

	});


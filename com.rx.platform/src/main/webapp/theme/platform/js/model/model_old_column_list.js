
	layui.config({
		base : "js/"
	}).use([ 'form', 'layer', 'jquery', 'laypage', 'element' ], function() {
		var $ = layui.$,form = layui.form, layer = layui.layer, laypage = layui.laypage;
		var element = layui.element;
	    var time_ = 2000;

		//批量发布
		$(".batchFb").click(function() {
			var $checkbox = $('tbody input[type="checkbox"][name="checked"]');
			var $checked = $('tbody input[type="checkbox"][name="checked"]:checked');
			if ($checkbox.is(":checked")) {
				layer.confirm('确定新增选中的信息？', {
					icon : 3,
					title : '提示信息'
				}, function(indexNum) {
					
					
					
			 	  //获取所有选中ids
		 		  var ids_ = [];
		 		  var names_ = [];
		 		  var types_ = [];
		          $.each(eval($checked),function(n,value){
		   			ids_.push($(value).val());
		   			names_.push($(value).parent().find('input[name="column_comment"]').val())
		   			types_.push($(value).parent().find('input[name="column_type"]').val())
		   		  });
					
		          
		          
		          
		          
					var name_ = "新增";
					$.ajax({
						url : "/platform/sys/column_save",
						traditional :true, //传递数组参数,  阻止序列化
 					data:{
 						"model_id":$('#model_id').val(),
 						"ids": ids_,
 						"names": names_,
 						"types": types_,
 					},
 		 			  dataType: 'json',
						type : "post",
						success : function(result) {
							if(result.code == 'Y'){
  			 					layer.msg(name_+'成功' , {icon: 6, time: time_});
  			 					window.location.reload();
  			 					parent.location.reload();//刷新父页面
  			 				}
  			 				else {
  			 					layer.msg(name_+'失败', {icon: 5, time: time_});
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


	});


	
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
	});
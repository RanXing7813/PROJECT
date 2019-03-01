layui
		.config({
			base : '/theme/layuiadmin/' // 静态资源所在路径
		})
		.extend({
			index : 'lib/index' // 主入口模块
		})
		.use(
				[ 'form', 'layedit', 'laydate' ],
				function() {
					var form = layui.form, layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
					var time_ = 2000;
					var $ = layui.$;

					// 日期
					laydate.render({
						elem : '#date'
					});
					laydate.render({
						elem : '#date1'
					});

					// 自定义验证规则
					form .verify({
							col_name : [ /^[\S]{1,200}$/,
								'必填模板字段名称，且不能出现空格' ],
							col_comment : [ /^[\S]{1,200}$/,
								'必填模板字段名称注释，且不能出现空格' ],
								index_ : [ /^-?[0-9]\d{0,6}$/,
								'必填正整数，且不能出现空格' ],
							});

					// 监听提交
					form.on('submit(demo1)', function(data) {
						/*
						 * layer.alert(JSON.stringify(data.field), { title:
						 * '最终的提交信息' })
						 */
						
						
						if(''==$('#col_id').val()){
							//主键字段只能设置一个
							var name_ = "保存";
							var resFlag = false;
							var resMsg = '';
							$ .ajax({
										async : false,
										type : "post",
										url : "/platform/sys/data_check",
										data : {
											
											"type" : "_idcolumn",
											"col_name" : $('#col_name').val(),
											"model_id" : $('#model_id').val(),
											"col_id"	: $('#col_id').val()
										},
										dataType : 'json',
										success : function(result) {
											
											//存在主键
											if (result.code == 'N') {
												layer .msg( result.msg ,{icon : 5 } );
												return false;
											} else {//不存在主键  
												 var index = layer.load();
												 $('.layui-form').submit();
												return false;
											}
										},
									});
						}else{
							 var index = layer.load();
							 $('.layui-form').submit();
						}
						
						return false;
					});
//					    form.on('submit(*)', function(data){
//						  console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
//						  console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
//						  console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
//						  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
//						});

					// 表单初始赋值
//					form.val('example', {
//						"table_name" : "code" // "name": "value"
//						,
//						"table_cname" : "code"
//					})
					// 关闭
					$(".closeCurPage").click(function() {
						var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
						parent.layer.close(index); //再执行关闭 
					});
				});
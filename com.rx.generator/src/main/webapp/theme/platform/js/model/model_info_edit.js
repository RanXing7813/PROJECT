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
								table_cname : [ /^[\S]{1,200}$/,
										'必填表中文名称，且不能出现空格' ],
								table_name : function(value) {
									var re = /^[\S]+$/;
									var flag_ = re.test(value) ;  
									if ( !flag_ ) {
										return '必填表名称，且不能出现空格';
									}

									var name_ = "保存";
									var resFlag = false;
									var resMsg = '';
									$ .ajax({
												async : false,
												type : "post",
												url : "/platform/modInfo/info_tableName_edit_checkTableName.json",
												data : {
													"table_name" : value,
													"id" : $('#id_table_name').val()
												},
												dataType : 'json',
												success : function(result) {
													if (result.code == 'N') {
														layer .msg( name_+ '成功',{icon : 6,time : time_},function() {
														});
													} else {
														resFlag = true;
														resMsg = result.code;
													}
												},
											// error:function(){
											// layer.msg('服务器异常', {icon: 5,
											// time: time_});
											// }
											});
									if(resFlag){
										return resMsg ;
									}
									
								}
							});

					// 监听提交
					form.on('submit(demo1)', function(data) {
						/*
						 * layer.alert(JSON.stringify(data.field), { title:
						 * '最终的提交信息' })
						 */
						//eg1
						 var index = layer.load();
						 $('.layui-form').submit();
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
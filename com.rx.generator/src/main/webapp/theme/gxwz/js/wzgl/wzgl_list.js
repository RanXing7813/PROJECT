		var historySearchButtons ;
		var map={};
			map.pageSize= 5;//每页多少条
			map.page=1;//当前页
			map.name = '';
			map.typeDiv = 4;// 删除标志  0删除   1有效/草稿   3待审核  4已发布
			map.sort='desc';
			
		function tabList(pId,id,name){
			map.infost_id = id;
			map.infost_parent_id = pId;
			map.name = name;
			map.typeDiv = 4;//删除标志  0删除   1有效/草稿   3待审核  4已发布
			map.filter = null;
//			go(1,name);
			 $.ajax({
		          url : "toWzgl_list",
		          async:false,//同步
		          data:{
		          },
		          type:"POST",
		          success: function(result) {
		        	  $('#contensBody').html(result);
		        	  go(1,name);
		          }
			 });
		}
		
		/**
		 * @param num 数据状态  删除标志  0删除   1有效/草稿   3待审核  4已发布
		 * @returns
		 */
		function changetypeDiv(num){
			//切换清空选中
			$('input[type="checkbox"]').prop("checked", false)
			map.typeDiv = num;
			go(1);
		}
		
		$("#cmsSortASC").click(function(){
			if(map.sort=='desc'){
				map.sort='asc';
			}else{
				map.sort='desc';
			}
			go(1,map.name);
			
		});
		
		//list 
		function go(pages){
			map.page=pages;
			   $.ajax({
			          url : "getWzgl_list.json",
			          dataType: "json", 
			          data:{
			        	  models : JSON.stringify(map)
			          },
			          type:"POST",
			          success: function(result) {
		      	 		$("#grid"+map.typeDiv).html('');
		      	 		//alert(result.list);
		      	 			var list =   '';
		      	 		$.each(result.list, function(n,value){  
			  	 		    	var	ids = value.infolink_id;
								  list +=   
									'<li><input type="checkbox" value="'+ids+'" class="f-toe" name="checkName" style="float:left;margin: 22px 10px 0 0;"><a href="javascript:void(0);" id="'+ids+'"  onclick="codeEdit(this)" class="f-toe">'+value.infolink_title+'</a>'+
									'<span class="datetime">'+value.created_time+'</span></li>';
			  	 				$("#grid"+map.typeDiv).append(list);
		      	 		});
		      	 		$("#grid"+map.typeDiv).html(list);
		      	 		
	      				//加载分页信息
	      				//分页信息
	      				var z = result.total%map.pageSize;
	      				totalCount = result.total;
	      				//已发布 待审核 草稿箱
	      				var xxName = '';
	      				if(map.typeDiv == 4){
	      					xxName = '已发布';
	      				}else if(map.typeDiv ==3){
	      					xxName = '待审核';
	      				}else if(map.typeDiv == 1){
	      					xxName = '草稿箱';
	      				}
	      				
	      				$("#td_toalcount").html("共"+totalCount+"条 "+xxName+' '+map.name);
	      				var pageNum;
	      				if(z==0){
	      					pageNum=result.total/map.pageSize;
	      				}else{
	      					pageNum=Math.floor(result.total/map.pageSize)+1;
	      				}
	      				map.pagenum=pageNum;
	      				
	      				var pageoption=PageButten(pages,pageNum);
	      				$("#listpage"+map.typeDiv).html(pageoption);
	      				
			          },
			          error: function(result) {
			          }
			        });
		}	
		
		function add(){
			$("#contensBody").load('getWzgl_Detail?infosortId='+map.infost_id);
			
		}
		
		 	function search(){
					map.filter={
				    filters : [
					{
						field : "infolinkTitle",
						value : $("#infolinkTitle").val().trim()
					}
					]};
					go(1);
		 	}
		 	 
			$("#searchButtons").click(function() {
				var a = '请输入查询关键词';
				if (a == $("#infolinkTitle").val().trim()) {
					map.filter = null;
				} else {
					map.filter = {
						logic : "and",
						filters : [ {
							field : "infolinkTitle",
							value : $("#infolinkTitle").val().trim()
						} ]
					};
				}
				go(1);
			});
			
	
			//进来触发  通知公告的点击事件
			//点击回车
			$('#infolinkTitle').bind('keypress',function(event){  
			    if(event.keyCode == "13"){  
			    	$("#searchButtons").click();
			    }  
			});
			 /**
			  *  启用  停用  删除
			 * @param num
			 * @returns
			 */
			function qiyong(num){

				var numName_ = '';
				if(0==num){
					numName_ = '删除';
				}else if(4==num){
					numName_ = '启用'
				}else if(1==num){
					numName_ = '停用'
				}
				var ids = [];
				$('input[type="checkbox"]:checked').each(function(){
					ids.push($(this).val());
				});
				
				if(ids.length==0){
					return;
				}	
				layer.confirm('您确定要'+numName_+'吗？', {
					  btn: ['确认','取消'] //按钮
					}, function(index, layero){
					 $.ajax({
				          url : "doWzgl_ChangeStatus.json",
				          dataType: "json", 
				        //  async:false,//同步
				          cache: false,
				          traditional: true,//阻止序列化
				          data:{
				        	  ids : ids,
				        	  status : num
				          },
				          type:"POST",
				          success: function(result) {
				        	  var a = '请输入查询关键词';
								if (a == $("#infolinkTitle").val().trim()) {
									map.filter = null;
								} else {
									map.filter = {
										logic : "and",
										filters : [ {
											field : "infolinkTitle",
											value : $("#infolinkTitle").val().trim()
										} ]
									};
								}
								go(1);
				          },
				          error: function(result) {
				          }
				        });
						layer.close(index);
					}, function(index, layero){
						layer.close(index);
					});
				 
			 }
			//查看
			   function codeEdit(obj) {
	  			 var id = obj["id"];
	  			  $("#xxfw_").val(id);
	  			  var myDate = new Date();
				  var t = myDate.getTime()+Math.floor(Math.random()*10000+1);
	  			 var url = "getWzgl_Detail?infolinkId="+id+"&infosortId="+map.infost_id+"&t="+t;
	  			 $("#contensBody").load(url);
	  			 
	  			 
	           }
			   /**
				  * 
				 * @param num
				 * @returns
				 */
				function codeLook(){
					   var ids = [];
						$('input[type="checkbox"]:checked').each(function(){
							ids.push($(this).val());
						});
						if(ids.length>1){
							layer.alert("只能同时预览一条数据", {
							    skin: 'layui-layer-lan'
							    ,closeBtn: 0
							  });
							return;
						}else if(ids.length==0){
							layer.alert("请选中一条数据预览", {
							    skin: 'layui-layer-lan'
							    ,closeBtn: 0
							  });
							return;
						}
						
						  var myDate = new Date();
						  var t = myDate.getTime()+Math.floor(Math.random()*10000+1);
						 var url = "getWzgl_Look?infolinkId="+ids[0]+"&t="+t;
			  			 $("#contensBody").load(url);	
				 }
			
			
			//加载一级节点
			function ulLever1(){
				 $.ajax({
			          url : "getWzgl_ulLever1.json",
			          dataType: "json", 
			          async:false,//同步
			          cache: false,
//			          traditional: true,//阻止序列化
			          data:{
			          },
			          type:"POST",
			          success: function(result) {
			        	   var html_ = '';
			        	  $.each(result.list, function(n,value){  
				  	 		    	var	ids = value.infosort_id;
				  	 		    	var	pid = value.parent_id;
				  	 		    	var	name = value.infosort_name;
				  	 		    	var info1 = value.info1;
				  	 		    	if(info1==1){
							             html_ += '<li><a href="javascript:void(0)" class="submenu-item " id="'+ids+'_" onclick="ulLever2(\''+ids+'\');"  ><span>'+name+'</span></a><ul></ul></li>';
				  	 		    	}else{
							             html_ += '<li><a href="javascript:void(0)" class="submenu-item " id="'+ids+'_" onclick="tabList(\''+pid+'\',\''+ids+'\',\''+name+'\');" ><span>'+name+'</span></a><ul></ul></li>';
				  	 		    	}
				  	 		    	
			      	 		});
			        	  $('#ulLever1').html(html_);
			          },
			          error: function(result) {
			          }
			    });
				
				 //点击第一个页签
				 $('#ulLever1').find('a').eq(0).addClass('current');
				 $('#ulLever1').find('a').eq(0).click();
				 
				 //点击第一个页签的第一个 子页签
				 $('#ulLever1').find('a').eq(0).next().find('li').eq(0).find('a').eq(0).click();
				 $('#ulLever1').find('a').eq(0).next().find('li').eq(0).find('a').eq(0).addClass('current');
				 
			}
			
			//加载二级节点
			function ulLever2(id){
				if($('#'+id+'_').next().find('li').length>0 ){
					return;
				}
				$.ajax({
			          url : "getWzgl_ulLever2.json",
			          dataType: "json", 
			          async:false,//同步
			          cache: false,
//			          traditional: true,//阻止序列化
			          data:{
			        	  pId:id
			          },
			          type:"POST",
			          success: function(result) {
			        	  var html_ = ' ';
			        	  $.each(result.list, function(n,value){  
				  	 		    	var	ids = value.infosort_id;
				  	 		    	var	pid = value.parent_id;
				  	 		    	var	name = value.infosort_name;
				  	 		    	var info1 = value.info1; //判断是否有下级节点
				  	 		    	if(info1==1){
							             html_ += '<li><a href="javascript:void(0)" class="submenu-item " id="'+ids+'_" onclick="ulLever3(\''+ids+'\');" ><span>'+name+'</span></a><ul></ul></li>';
				  	 		    	}else{
							             html_ += '<li><a href="javascript:void(0)" class="submenu-item " id="'+ids+'_" onclick="tabList(\''+pid+'\',\''+ids+'\',\''+name+'\');" ><span>'+name+'</span></a></li>';
				  	 		    	}
				  	 		    	
			      	 		});
			        	  $('#'+id+'_').next().append(html_);
			        	  $('#'+id+'_').next().slide( ); //3、bd里面有数据了，才执行superslide	
			        	  
			        	
			        	  $("#supperSlide").html(
			        			  //'<script type="text/javascript" src="theme/js/simpla.jquery.configuration.js"></script>'+
			        				'<script type="text/javascript" src="theme/js/base.menu.js"></script>'+
			        				'<script type="text/javascript" src="/theme/js/jquery.SuperSlide.2.1.1.js" charset="utf-8"></script>');
			          },
			          error: function(result) {
			          }
			    });
			}
			
			//加载三级节点
			function ulLever3(id){
				if($('#'+id+'_').next().find('li').length>0 ){
					return;
				}
				$.ajax({
			          url : "getWzgl_ulLever2.json",
			          dataType: "json", 
			          async:false,//同步
			          cache: false,
//			          traditional: true,//阻止序列化
			          data:{
			        	  pId:id
			          },
			          type:"POST",
			          success: function(result) {
			        	  var html_ = ' ';
			        	  $.each(result.list, function(n,value){  
				  	 		    	var	ids = value.infosort_id;
				  	 		    	var	pid = value.parent_id;
				  	 		    	var	name = value.infosort_name;
				  	 		    	var info1 = value.info1; //判断是否有下级节点
				  	 		    	console.log(name+"---"+info1);
				  	 		    	if(info1==1){
							             html_ += '<li><a href="javascript:void(0)" class="thirdmenu " id="'+ids+'_" ><span>'+name+'</span></a><ul></ul></li>';
				  	 		    	}else{
							             html_ += '<li><a href="javascript:void(0)" class="no-thirdmenu " id="'+ids+'_" onclick="tabList(\''+pid+'\',\''+ids+'\',\''+name+'\');" ><span>'+name+'</span></a></li>';
				  	 		    	}
				  	 		    	
			      	 		});
			        	  $('#'+id+'_').next().append(html_);
			        	  $('#'+id+'_').next().slide( ); //3、bd里面有数据了，才执行superslide	
			        	  
			        	
			        	  $("#supperSlide").html(
			        			  //'<script type="text/javascript" src="theme/js/simpla.jquery.configuration.js"></script>'+
			        				'<script type="text/javascript" src="theme/js/base.menu.js"></script>'+
			        				'<script type="text/javascript" src="/theme/js/jquery.SuperSlide.2.1.1.js" charset="utf-8"></script>');
			          },
			          error: function(result) {
			          }
			    });
			}
			
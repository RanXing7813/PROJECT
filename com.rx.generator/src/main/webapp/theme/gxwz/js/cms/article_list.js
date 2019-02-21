


		var historySearchButtons ;
		 	var map={};
			map.pageSize= 5;//每页多少条
			map.page=1;//当前页
			map.name = '';
			map.sort='desc';
			
		function tabList(pId,id,name){
			
			
			$("#searchbutton").show();
			$('#rightwrapid').html('<div class="rlist-top">'+
			'		<table>'+
			'		<colgroup>'+
			'			<col width="660"> </col>'+
			'			<col width="120"> </col>'+
			'			<col width="120"> </col>'+
			'		</colgroup>'+
			'		<tr>'+
			'			<td class=" fn-text-right" id="td_toalcount"></td>'+
			'			<td class="fn-text-center">'+
			'				<a href="###" id="cmsSortASC"   >创建时间升序</a>'+
			'			</td>'+
					'</tr>'+
				'</table>'+
			'</div>'+
			'<div class="article-content">'+
				'<ul id="grid">'+
			'	</ul>'+
			'</div>'+
			'<div class="rlist-pagenar" id="listpage">'+
			'</div>		<script src="/theme/gxwz/js/cms/listgo.js"></script>');
			$("#rightwrapid").attr("style","margin-top: 0;");

			$("#infolinkTitle").val('请输入查询关键词');
			map.infost_id = id;
			map.infost_parent_id = pId;
			map.name = name;
			map.filter = null;
			go(1,name);
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
			          url : "getCms_list.json",
			          dataType: "json", 
			          data:{
			        	  models : JSON.stringify(map)
			          },
			          type:"POST",
			          success: function(result) {
		      	 		$("#grid").html('');
		      	 		//alert(result.list);
		      	 			var list =   '';
		      	 		$.each(result.list, function(n,value){  
			  	 		    	var	ids = value.infolink_id;
								  list +=   
									'<li><a href="#" id="'+ids+'"  onclick="codeEdit(this)" class="f-toe">'+value.infolink_title+'</a>'+
									'<span class="datetime">'+value.created_time+'</span></li>';
			  	 				$("#grid").append(list);
		      	 		});
		      	 		$("#grid").html(list);
		      	 		
		      	 		
	      				//加载分页信息
	      				//分页信息
	      				var z = result.total%map.pageSize;
	      				totalCount = result.total;
	      				$("#td_toalcount").html("共"+totalCount+"条"+map.name);
	      				var pageNum;
	      				if(z==0){
	      					pageNum=result.total/map.pageSize;
	      				}else{
	      					pageNum=Math.floor(result.total/map.pageSize)+1;
	      				}
	      				map.pagenum=pageNum;
	      				
	      				var pageoption=PageButten(pages,pageNum);
	      				$("#listpage").html(pageoption);
			          },
			          error: function(result) {
			          }
			        });
		}	
		
			//查看
		   function codeEdit(obj) {
  			 var id = obj["id"];
  			  $("#xxfw_").val(id);
  			  var myDate = new Date();
			  var t = myDate.getTime()+Math.floor(Math.random()*10000+1);
//  			  var url = "getCms_Detail?infolinkId="+id+"&openNewPage=Y";
  			    getWriteDownDetail(id,"getCms_index");
  			  
           }
		   
		   function getWriteDownDetail(poNum,orgName){
		        var  url = "";
		        //首先创建一个form表单  
		        var tempForm = document.createElement("form");    
		        tempForm.id="tempForm1";  
		        //制定发送请求的方式为post  
		        tempForm.method="POST";   
		        //此为window.open的url，通过表单的action来实现  
		        tempForm.action=url;  
		        //利用表单的target属性来绑定window.open的一些参数（如设置窗体属性的参数等）  
		        tempForm.target="_blank";   
		        
		        //创建input标签，用来设置参数  
		        var hideInput = document.createElement("input");    
		        hideInput.type="hidden";    
		        hideInput.name= "infolinkId";  
		        hideInput.value= poNum; 
		        var hideInput2 = document.createElement("input");  
		        hideInput2.type = "hidden";  
		        hideInput2.name = "openNewPage";
		        hideInput2.value = orgName;
		        var hideInput3 = document.createElement("input");  
		        hideInput3.type = "hidden";  
		        hideInput3.name = "infosortId";
		        hideInput3.value = $("#infosortId").val();
		        //将input表单放到form表单里  
		        tempForm.appendChild(hideInput); 
		        tempForm.appendChild(hideInput2);
		        tempForm.appendChild(hideInput3);
		        //将此form表单添加到页面主体body中  
		        document.body.appendChild(tempForm); 
		        //手动触发，提交表单  
		        tempForm.submit();
		        //从body中移除form表单  
		        document.body.removeChild(tempForm);  
		    }
		   
		 
		 	//进入csb页面
			function getJRFW(type){
				$.ajax({
				  url : "jrfwCSBIndex",
				  dataType: "text", 
				  data:{type:type},
				  type:"get",
				  success: function(result) {
					  if(result==null){
					  }else{
						  $("#contentDiv").html(result);
				        	  }
				          },
				      error: function(result) {
				      }
				  });
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
				go(1,map.name);
			});
			
	
			//进来触发  通知公告的点击事件
			$(function() {
				ulLever1();
			})
			
			
			//点击回车
			$('#infolinkTitle').bind('keypress',function(event){  
			    if(event.keyCode == "13"){  
			    	$("#searchButtons").click();
			    }  
			});
			
			
			//加载一级节点
			function ulLever1(){
				 $.ajax({
			          url : "getCms_ulLever1.json",
			          dataType: "json", 
			          async:false,//同步
			          cache: false,
//			          traditional: true,//阻止序列化
			          data:{
			        	  infosortId:$('#infosortId').val()
			          },
			          type:"POST",
			          success: function(result) {
			        	   var html_ = '';
			        	   console.log(result);
			        	  $.each(result.list, function(n,value){  
				  	 		    	var	ids = value.infosort_id;
				  	 		    	var	pid = value.parent_id;
				  	 		    	var	name = value.infosort_name;
				  	 		    	var info1 = value.info1;
				  	 		    	if(info1==1){
							             html_ += '<li><a href="javascript:void(0)" class="nav-top-item " id="'+ids+'_" onclick="ulLever2(\''+ids+'\');"  ><span>'+name+'</span></a><ul></ul></li>';
				  	 		    	}else{
							             html_ += '<li><a href="javascript:void(0)" class="nav-top-item " id="'+ids+'_" onclick="tabList(\''+pid+'\',\''+ids+'\',\''+name+'\');" ><span>'+name+'</span></a><ul></ul></li>';
				  	 		    	}
				  	 		    	
			      	 		});
			        	  $('ul[name="ulLever1"]').html(html_);
			        	  
			        	  //add gxcx
			        	  if('06e6f01e11fe4a1b95cfa40ea2cb3411'==$('#infosortId').val()){
			        		  var gxcxMenu_ =  '<li><a href="javascript:void(0)"'+
								'class="nav-top-item " onclick="getGXYYZC()"><span>共享应用支撑</span></a></li>'+
							/*'<li><a href="javascript:void(0)"'+
								'class="nav-top-item " onclick="getXQKFGK()"><span>需求开发概况</span></a></li>'+*/
							'<li><a href="javascript:void(0)"'+
								'class="nav-top-item " onclick="getMLTBTJ()"><span>目录资源统计</span></a></li>'+
							'<li><a href="javascript:void(0)"'+
								'class="nav-top-item " onclick="getZYSYTJ()"><span>资源挂接使用</span></a></li>';
			        		  $('ul[name="ulLever1"]').append(gxcxMenu_)
			        	  }
			        	  
			          },
			          error: function(result) {
			          }
			    });
				
				 //点击第一个页签
				 $('ul[name="ulLever1"]').find('a').eq(0).addClass('current');
				 $('ul[name="ulLever1"]').find('a').eq(0).click();
				 
				 //点击第一个页签的第一个 子页签
				// $('#ulLever1').find('a').eq(0).next().find('li').eq(0).find('a').eq(0).click();
				// $('#ulLever1').find('a').eq(0).next().find('li').eq(0).find('a').eq(0).addClass('current');
				 
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
							             html_ += '<li><a href="javascript:void(0)" class="submenu-item " id="'+ids+'_"  ><span>'+name+'</span></a></li>';
				  	 		    	}else{
							             html_ += '<li><a href="javascript:void(0)" class="submenu-item " id="'+ids+'_" onclick="tabList(\''+pid+'\',\''+ids+'\',\''+name+'\');" ><span>'+name+'</span></a></li>';
				  	 		    	}
				  	 		    	
			      	 		});
			        	  $('#'+id+'_').next().append(html_);
			        	  $('#'+id+'_').next().slide( ); //3、bd里面有数据了，才执行superslide	
			        	  
			        	
			        	  $("#supperSlide").html(
			        			  //'<script type="text/javascript" src="theme/js/simpla.jquery.configuration.js"></script>'+
			        				'<script type="text/javascript" src="theme/gxwz/js/base.menu.js"></script>'+
			        				'<script type="text/javascript" src="/theme/gxwz/js/jquery.SuperSlide.2.1.1.js" charset="utf-8"></script>');
			          },
			          error: function(result) {
			          }
			    });
			}
			
			
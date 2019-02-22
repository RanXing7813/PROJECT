  layui.config({
    base: '/theme/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'table', 'form'], function(){
    var admin = layui.admin
    ,table = layui.table
    ,$ = layui.$ 
    ,form = layui.form 
    ,time_ = 2000;
    userid_ = $('#userid').val()
    
    table.render({
       elem: '#Layui-table-toolbar'
      ,id:'Layui-table-toolbar'
      ,url: linkListS
      ,toolbar: '#Layui-table-toolbar-toolbarDemo'
      ,autoSort: false
      ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
      ,request: {
    	    pageName: 'pagecurrentnum' //页码的参数名称，默认：page
    	    ,limitName: 'selectlimitnum' //每页数据量的参数名，默认：limit
    	  }
      ,parseData: function(res){ //res 即为原始返回的数据
    	    return {
   	    	  "code": res.code, //解析接口状态
   	          "msg": res.msg, //解析提示文本
    	      "count": res.totle, //解析数据长度 pagesize  datacount
    	      "data": res.list //解析数据列表
    	    };
    	  }
      ,title: '用户数据表'
      ,cols: [[
        {type: 'checkbox', fixed: 'left'},
         {type: 'numbers', title:'序号', fixed: 'left',width:60}
        ,{width:150,field:'model_zyid', title:'模板资源编号', fixed: 'left',sort:true }
        ,{ field:'model_zyname', title:'模板资源名称',sort:true    } 
        ,{width:150,field:'model_info_table_name', title:'数据表名称',sort:true  }
        ,{width:200,field:'model_name', title:'模板导出名称',sort:true  }
        ,{width:200,field:'model_version', title:'模板版本',sort:true  }
        ,{width:200,field:'cread_name', title:'模板创建人',sort:true  }
        ,{width:150,field:'publish_status', title:'状态'  ,templet:function(res){
        	if(res.publish_status=='Y'){
        		return '已发布';
        	}else {
        		return '未发布';
        	}
        } }
        ,{fixed: 'right', title:'操作',  width:300, align: 'center', toolbar: '#Layui-table-toolbar-barDemo'}
      ]]
      ,limit:10
      ,limits:[10,30,50,100,1000]
      ,page:{
    	  layout:['prev','page','next','skip' ,'count','refresh','limit']   
      }
    });
    
    var active = {
    	      reload: function(){
    	        //执行重载
    	        table.reload('Layui-table-toolbar', {
    	          page: {
    	            curr: 1 //重新从第 1 页开始
    	          }
    	          ,where: {
    	        	    model_zyid: $('#model_zyid').val()
	        	    	,model_zyname: $('#model_zyname').val()
	        	    	,model_info_table_name: $('#model_info_table_name').val()
	        	    	,model_info_table_cname: $('#model_info_table_cname').val()
	        	    	,model_name: $('#model_name').val()
	        	    	,isMyDatas: $('#isMyDatas').val()
    	          }
    	        });
    	      }
    	    };
    $('.test-table-reload-btn .layui-btn').on('click', function(){
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });
        
 	//监听排序事件 
    table.on('sort(Layui-table-toolbar)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
      //有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
      table.reload('Layui-table-toolbar', {
        initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。
        ,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
           field: obj.field //排序字段
          ,order: obj.type //排序方式
        }
      });
    });
    
    //头工具栏事件
    table.on('toolbar(Layui-table-toolbar)', function(obj){
    	//obj.config.id = 'Layui-table-toolbar'
      var checkStatus = table.checkStatus(obj.config.id);
      switch(obj.event){
        case 'opendgviewAdd'://
        	opendgviewAdd( );
        	  break;
        case 'opendgviewDowload'://
        	var data = checkStatus.data;
        	opendgviewDowload(getCheckboxId(data) );
        	  break;
        case 'opendgviewPush'://opendgviewPush
        	var data = checkStatus.data;
        	opendgviewPush(getCheckboxId(data, true), checkStatus.data.length );
        	  break;
        case 'opendgviewBack'://
        	var data = checkStatus.data;
        	opendgviewBack(getCheckboxId(data, true), checkStatus.data.length  );
        	  break;
        case 'opendgviewDel'://
        	var data = checkStatus.data;
        	opendgviewDel(getCheckboxId(data, true), checkStatus.data.length  );
        	break;
      };
    });
    
    //监听行工具事件
    table.on('tool(Layui-table-toolbar)', function(obj){
    	var data = obj.data;
        if(obj.event === 'detail'){
            opendgviewLook(data.model_id);
        }  else if(obj.event === 'edit'){
        	opendgviewEdit(data.model_id, data.cread_id)
        }   else if(obj.event === 'sys'){
        	opendgviewSys(data.model_id, data.cread_id, data.publish_status)
        }   else if(obj.event === 'opendgviewDowload'){
        	opendgviewDowloadById(data.model_id );
        }
    });
    
    
    form.on('switch(isMyDatas)', function(data){
//    	  console.log(data.elem); //得到checkbox原始DOM对象
//    	  console.log(data.elem.checked); //开关是否开启，true或者false
//    	  console.log(data.value); //开关value值，也可以通过data.elem.value得到
//    	  console.log(data.othis); //得到美化后的DOM对象
    	  if(data.elem.checked == true){
    		  $('#isMyDatas').val(1);
    		  $('#search').click();
    	  }else{
    		  $('#isMyDatas').val(0);
    		  $('#search').click();
    	  }
    	});  
    
    
    //获取所有选中ids
 	function  getCheckboxStrId(data){
 		var ids_ = '';
 		$.each(eval(data),function(n,value){
    			ids_ += "'"+value.model_id + "',";
    	});
 		if(ids_.length>0){
 			ids_ = ids_.substring(0,ids_.length-1);
 		}
 		return ids_ ;
 	}
 	
    //获取所有选中ids
 	function  getCheckboxId(data,flag_){
 		  var ids_ = [];        		
          $.each(eval(data),function(n,value){
        	  if(flag_ &&  $('#userid').val() !='' &&  $('#userid').val() != value.cread_id){
        	  }else{
        		  ids_.push(value.model_id);
        	  }
   		  });
 		return ids_ ;
 	}
    
    
  	//新增页面
    function opendgviewAdd( ){
    	var index = layer.open({
            type: 2,
            title:'模板管理 - 模板新增',
            content:  linkAddS ,
            area: ['300px', '300px'],
            maxmin: true,
            end : function(index, layero) {
                 opendgviewAddcancel(index, layero);
                 layer.close(index)
            }
          });
          layer.full(index);
    }
    //新增页面关闭右上角按钮触发方法
    function opendgviewAddcancel(index, layero){ 
 	   //上述方法等价于
	   table.reload('Layui-table-toolbar', {
	    where: { //设定异步数据接口的额外参数，任意设
	    	 model_zyid: $('#model_zyid').val()
 	    	,model_zyname: $('#model_zyname').val()
 	    	,model_info_table_name: $('#model_info_table_name').val()
 	    	,model_info_table_cname: $('#model_info_table_cname').val()
 	    	,model_name: $('#model_name').val()
	    	,isMyDatas: $('#isMyDatas').val()

	    }
	    ,page: {
	      curr: 1 //重新从第 1 页开始
	    }
	  }); 
 	  return false; 
 	} 
    
    //编辑页面
    function opendgviewEdit(id, cread_id){
    	
    		var index = layer.open({
                type: 2,
                title:'模板管理 - 模板编辑',
                content: linkAddS+'?model_id='+id+'&cread_id='+cread_id,
                area: ['300px', '300px'],
                maxmin: true,
                end: function(index, layero){ 
              	    opendgviewAddcancel(index, layero);
              	    layer.close(index)
              	  return false; 
              	}  
              });
              layer.full(index);
    }
    function opendgviewSys(id, cread_id, publish_status){
	    	var index = layer.open({
	            type: 2,
	            title:'模板管理 - 字段设置',
	            content: linkSysS+'?model_id='+id+'&cread_id='+cread_id+'&publish_status='+publish_status,
	            area: ['90%', '90%'],
	            maxmin: true,
	            end: function(index, layero){ 
	            	 opendgviewAddcancel(index, layero);
	           	     layer.close(index)
	          	  return false; 
	          	}  
	          });
    }
    
    
   function opendgviewDowload(idsData){
	    if(idsData.length>1 ){
	    	layer.msg('请只选中一条模板数据下载', {icon: 6});
   		}else if(idsData.length==1 ){
   			   var form=$("<form>");//定义一个form表单  
	   	       form.attr("style","display:none");  
	   	       form.attr("target","");  
	   	       form.attr("method","post");  
	   	       form.attr("action","/platform/export/download_model");  //encodeURIComponent(models)  models = JSON.stringify(map);
	   	       var input1=$("<input>");  
	   	       input1.attr("type","hidden");  
	   	       input1.attr("name","model_id");  
	   	       input1.attr("value",idsData[0]);  
	   	       $("body").append(form);//将表单放置在web中  
	   	       form.append(input1);  
	   	       form.submit();//表单提交   
   		}else{
	   		layer.msg('请选中数据', {icon: 6});
	   	}
   };
    
   function opendgviewDowloadById(id){
  			   var form=$("<form>");//定义一个form表单  
	   	       form.attr("style","display:none");  
	   	       form.attr("target","");  
	   	       form.attr("method","post");  
	   	       form.attr("action","/platform/export/download_model");  //encodeURIComponent(models)  models = JSON.stringify(map);
	   	       var input1=$("<input>");  
	   	       input1.attr("type","hidden");  
	   	       input1.attr("name","model_id");  
	   	       input1.attr("value",id);  
	   	       $("body").append(form);//将表单放置在web中  
	   	       form.append(input1);  
	   	       form.submit();//表单提交   
  };
   
    
      //发布页面
      function  opendgviewPush (idsData, length_){
    	  var name_ =  '发布' ;
    	  
    	  	if( idsData.length != length_){
    	  		layer.msg('只能 修改自己创建的数据', {icon: 6});
    	  		return;
    	  	}
    	  
    		if(idsData.length>0 ){
    				var url_ = linkPushS;
    				if(idsData.length==1){
    					url_ +='?model_id='+idsData[0]
    				}
			    	var index = layui.layer.open({
			    		title : "单位选择树",
			    		type : 2,
			    		area : [ '400px', '80%' ],
			    		btn : [ '确定', '取消' ],
			    		btnAlign : 'r',// 按钮排列
			    		content : url_,
			    		success : function(layero, index) {
			
			    		},
			    		yes : function(index, layero) {
			    			// 当点击‘确定’按钮的时候，获取弹出层返回的值
			    			var obj = window["layui-layer-iframe" + index].callbackdata();
			    			   if(obj.selectedIds && obj.selectedIds.length>0)
			    		       $.ajax({
			     		 			async:false,
			     		 			type:"post",
			     		 			url:'/platform/model/data_push_save',
			     		 			traditional :true, //传递数组参数,  阻止序列化
			     					data:{
			     						"model_id":idsData,
			     						"ids": obj.selectedIds,
			     						"names":obj.selectedNames
			     					},
			     		 			dataType: 'json',
			     		 			success:function(result){
			     			 				if(result.code == 'Y'){
			     			 					layer.msg(name_+'成功' , {icon: 6, time: time_},function(){
			     			 						 //上述方法等价于
			     			 			          	  table.reload('Layui-table-toolbar', {
			     			 			          	    where: { //设定异步数据接口的额外参数，任意设
			   		  			 			             model_zyid: $('#model_zyid').val()
			   		  			 			   	    	,model_zyname: $('#model_zyname').val()
			   		  			 			   	    	,model_info_table_name: $('#model_info_table_name').val()
			   		  			 			   	    	,model_info_table_cname: $('#model_info_table_cname').val()
			   		  			 			   	    	,model_name: $('#model_name').val()
			   		  			 			   	    	,isMyDatas: $('#isMyDatas').val()

			     			 			          	    }
			     			 			          	    ,page: {
			     			 			          	      curr: 1 //重新从第 1 页开始
			     			 			          	    }
			     			 			          	  });
			     			 					});
			     			 				}
			     			 				else {
			     			 					layer.msg(name_+'失败', {icon: 5, time: time_});
			     			 				}
			     		 				},
			     		 			error:function(){
			     		 				layer.msg('服务器异常', {icon: 5, time: time_});
			     		 			}
			     		 			});
			    			
			    			// 最后关闭弹出层
			    			layui.layer.close(index);
			    		},
			    		btn2 : function(index, layero) {
			    			layui.layer.close(index);
			    		},
			    		cancel : function() {
			    			layui.layer.close(index);
			    		}
			    	})
    		}else{
      	   		layer.msg('请选中数据', {icon: 6});
      	   	}
      }
      
      //删除
      function opendgviewDel(idsData, length_){
       var name_ =  '批量删除' ;
     	 var ids = idsData ;

 	  	if( idsData.length != length_){
 	  		layer.msg('只能 修改自己创建的数据', {icon: 6});
 	  		return;
 	  	}
  	   	if( ids.length>0){
  	   	   layer.confirm('确定要'+name_+'吗？', {
  	         btn: ['确认','取消'] //按钮
  	       }
  	   	   , function(index){
  		   		   layer.close(index);
  	    	       $.ajax({
  		 			async:false,
  		 			type:"post",
  		 			url:linkDelS,
  		 			traditional :true, //传递数组参数,  阻止序列化
  					data:{
  						"ids": ids,
  					},
  		 			dataType: 'json',
  		 			success:function(result){
  			 				if(result.code == 'Y'){
  			 					layer.msg(name_+'成功' , {icon: 6, time: time_},function(){
  			 						 //上述方法等价于
  			 			          	  table.reload('Layui-table-toolbar', {
  			 			          	    where: { //设定异步数据接口的额外参数，任意设
		  			 			             model_zyid: $('#model_zyid').val()
		  			 			   	    	,model_zyname: $('#model_zyname').val()
		  			 			   	    	,model_info_table_name: $('#model_info_table_name').val()
		  			 			   	    	,model_info_table_cname: $('#model_info_table_cname').val()
		  			 			   	    	,model_name: $('#model_name').val()
		  				        	    	,isMyDatas: $('#isMyDatas').val()

  			 			          	    }
  			 			          	    ,page: {
  			 			          	      curr: 1 //重新从第 1 页开始
  			 			          	    }
  			 			          	  });
  			 					});
  			 				}
  			 				else {
  			 					layer.msg(name_+'失败', {icon: 5, time: time_});
  			 				}
  		 				},
  		 			error:function(){
  		 				layer.msg('服务器异常', {icon: 5, time: time_});
  		 			}
  		 			});
  	    	   
  	       }
  	   	   , function(){
  	       });
  	 		
  	   	}else{
  	   		layer.msg('请选中数据', {icon: 6});
  	   	}
      } 
      
    //撤销
    function opendgviewBack(  idsData, length_){
    	 var name_ =  '批量撤销' ;
     	 var ids = idsData ;

 	  	if( idsData.length != length_){
 	  		layer.msg('只能 修改自己创建的数据', {icon: 6});
 	  		return;
 	  	}
  	   	if( ids.length>0){
  	   	   layer.confirm('确定要'+name_+'吗？', {
  	         btn: ['确认','取消'] //按钮
  	       }
  	   	   , function(index){
  		   		   layer.close(index);
  	    	       $.ajax({
  		 			async:false,
  		 			type:"post",
  		 			url:linkBackS,
  		 			traditional :true, //传递数组参数,  阻止序列化
  					data:{
  						"ids": ids,
  					},
  		 			dataType: 'json',
  		 			success:function(result){
  			 				if(result.code == 'Y'){
  			 					layer.msg(name_+'成功' , {icon: 6, time: time_},function(){
  			 						 //上述方法等价于
  			 			          	  table.reload('Layui-table-toolbar', {
  			 			          	    where: { //设定异步数据接口的额外参数，任意设
		  			 			             model_zyid: $('#model_zyid').val()
		  			 			   	    	,model_zyname: $('#model_zyname').val()
		  			 			   	    	,model_info_table_name: $('#model_info_table_name').val()
		  			 			   	    	,model_info_table_cname: $('#model_info_table_cname').val()
		  			 			   	    	,model_name: $('#model_name').val()
		  				        	    	,isMyDatas: $('#isMyDatas').val()

  			 			          	    }
  			 			          	    ,page: {
  			 			          	      curr: 1 //重新从第 1 页开始
  			 			          	    }
  			 			          	  });
  			 					});
  			 				}
  			 				else {
  			 					layer.msg(name_+'失败', {icon: 5, time: time_});
  			 				}
  		 				},
  		 			error:function(){
  		 				layer.msg('服务器异常', {icon: 5, time: time_});
  		 			}
  		 			});
  	    	   
  	       }
  	   	   , function(){
  	       });
  	 		
  	   	}else{
  	   		layer.msg('请选中数据', {icon: 6});
  	   	}
    }
    


  });
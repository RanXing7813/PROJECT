  layui.config({
    base: '/theme/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'table', 'form'], function(){
    var admin = layui.admin
    ,table = layui.table;
    var time_ = 2000;
    var $ = layui.$;
    table.render({
       elem: '#Layui-table-toolbar'
      ,id:'Layui-table-toolbar'
      ,url: linkListS  +'?model_id='+$('#model_id').val()
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
        ,{width:150,field:'col_name', title:'模板字段', fixed: 'left',sort:true }
        ,{ field:'col_comment', title:'字段注释',sort:true    } 
        ,{width:150,field:'column_type', title:'字段类型'    } 
        ,{width:150,field:'is_uuid', title:'是否主键'  ,templet:function(res){
        	if(res.is_uuid=='Y'){
        		return '是';
        	}else {
        		return '否';
        	}
        } }
        ,{fixed: 'right', title:'操作', fixed: 'right',width:200, align: 'center', toolbar: '#Layui-table-toolbar-barDemo'}
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
        case 'opendgviewAdd2'://
        	opendgviewAdd2( );
        	break;
      };
    });
    
    //监听行工具事件
    table.on('tool(Layui-table-toolbar)', function(obj){
     
        if(obj.event === 'detail'){
        	 var data = obj.data;
             opendgviewLook(data.col_id);
        }else if(obj.event === 'edit'){
        	 var data = obj.data;
        	opendgviewEdit(data.col_id)
        }else if(obj.event === 'del'){
        	 var data = obj.data;
        	 opendgviewDelS(data.col_id)
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
 	function  getCheckboxId(data){
 		  var ids_ = [];
          $.each(eval(data),function(n,value){
   			ids_.push(value.model_id);
   		  });
 		return ids_ ;
 	}
    
    
  	//新增非主键页面
    function opendgviewAdd( ){
    	var index = layer.open({
            type: 2,
            title:'模板管理 - 新增非主键字段',
            content:  linkAddS +'?model_id='+$('#model_id').val() ,
            area: ['80%', '80%'],
            success : function(layero, index) {
				setTimeout(function() {
					layui.layer.tips('点击此处关闭', '.layui-layer-setwin .layui-layer-close', {
						tips : 3
					});
				}, 500)
			},
            end : function(index, layero) {
                 opendgviewAddcancel(index, layero);
                 layer.close(index)
            }
          });
    }
  //新增主键页面
    function opendgviewAdd2( ){
    	var index = layer.open({
            type: 2,
            title:'模板管理 - 新增主键字段',
            content:  link2AddS +'?model_id='+$('#model_id').val() ,
            area: ['80%', '80%'],
            success : function(layero, index) {
				setTimeout(function() {
					layui.layer.tips('点击此处关闭', '.layui-layer-setwin .layui-layer-close', {
						tips : 3
					});
				}, 500)
			},
            end : function(index, layero) {
                 opendgviewAddcancel(index, layero);
                 layer.close(index)
            }
          });
    }
    //新增页面关闭右上角按钮触发方法
    function opendgviewAddcancel(index, layero){ 
 	   //上述方法等价于
	   table.reload('Layui-table-toolbar', {
	    where: { //设定异步数据接口的额外参数，任意设
	    }
	    ,page: {
	      curr: 1 //重新从第 1 页开始
	    }
	  }); 
 	  return false; 
 	} 
    
    //编辑页面
    function opendgviewEdit(id){
    	var index = layer.open({
            type: 2,
            title:'模板管理 - 字段编辑',
            content: linkEditS+'?col_id='+id,
            area: ['80%', '80%'],
            maxmin: true,
            end: function(index, layero){ 
          	    opendgviewAddcancel(index, layero);
          	    layer.close(index)
          	  return false; 
          	}  
          });
    }
    
    //删除
    function opendgviewDelS(id){
    	 var ids_ = [];
    	     ids_.push(id);
    	
     var name_ =  '删除' ;
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
					"ids": ids_
				},
	 			dataType: 'json',
	 			success:function(result){
		 				if(result.code == 'Y'){
		 					layer.msg(name_+'成功' , {icon: 6, time: time_},function(){
		 						 //上述方法等价于
		 			          	  table.reload('Layui-table-toolbar', {
		 			          	    where: { //设定异步数据接口的额外参数，任意设
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
    } 

  });
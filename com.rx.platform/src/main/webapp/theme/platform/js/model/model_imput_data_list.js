  layui.config({
    base: '/theme/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'table', 'form'], function(){
    var admin = layui.admin
    ,table = layui.table;
    var time_ = 2000;
    
    table.render({
       elem: '#Layui-table-toolbar'
      ,id:'Layui-table-toolbar'
      ,url: linkListS + '?id='+id_
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
      ,cols:  [eval ( '['+html_+']') ] 
      ,limit:10
      ,limits:[10,30,50,100,500]
      ,page:{
    	  layout:['prev','page','next','skip' ,'count','refresh','limit']   
      }
    });
    
    var $ = layui.$, active = {
    	      reload: function(){
    	        //执行重载
    	        table.reload('Layui-table-toolbar', {
    	          page: {
    	            curr: 1 //重新从第 1 页开始
    	          }
    	          ,where: {
    	        		imp_model_name: $('#imp_model_name').val()
	        	    	,imp_table_name: $('#imp_table_name').val()
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
        case 'opendgviewAdd'://新增
        	opendgviewAdd( );
        break;
      };
    });
    
    //监听行工具事件
    table.on('tool(Layui-table-toolbar)', function(obj){
      var data = obj.data;
      if(obj.event === 'detail'){
          opendgviewLook(data.imput_id);
        } else if(obj.event === 'del'){
        	deleteById(data.imput_id)
        } else if(obj.event === 'edit'){
        	opendgviewEdit(data.imput_id)
        }
    });
    
  	//新增页面
    function opendgviewAdd( ){
    	var index = layer.open({
            type: 2,
            title:'模板管理-信息类',
            content:  link2AddS ,
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
//	    	table_name: $('#table_name').val()
//	    	,table_cname: $('#table_cname').val()
	    }
	    ,page: {
	      curr: 1 //重新从第 1 页开始
	    }
	  }); 
 	  return false; 
 	} 
    
  //查看页面
    function opendgviewLook(id){
    	var index = layer.open({
            type: 2,
            title:'模板管理-数据录入',
            content:  link2EditS +id+'?stateS=look'  ,
            area: ['300px', '300px'],
            maxmin: true
          });
          layer.full(index);
    }
    
    //编辑页面
    function opendgviewEdit(id){
    	var index = layer.open({
            type: 2,
            title:'模板管理-数据录入',
            content: linkCheckS+'?imput_id='+id,
            area: ['300px', '300px'],
            maxmin: true,
            end: function(index, layero){ 
          	    layer.close(index)
          	 //上述方法等价于
          	  table.reload('Layui-table-toolbar', {
          	    where: { //设定异步数据接口的额外参数，任意设
          	  	imp_model_name: $('#imp_model_name').val()
    	    	,imp_table_name: $('#imp_table_name').val()
        	    	,id_table_name:id
          	    }
//          	    ,page: {
//          	      curr: 1 //重新从第 1 页开始
//          	    }
          	  });
          	    
          	    
          	  return false; 
          	}  
          });
          layer.full(index);
    }
  //单一删除
    function deleteById(id){
			layer.confirm('确定要删除吗？ ', {
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
			    				imput_id:id
			    			},
			    		dataType: 'json',
			    		success:function(result){
			    				if(result.code == 'Y'){
			    					layer.msg('删除成功', {icon: 6, time: time_},function(){
			    						 //上述方法等价于
			    			          	  table.reload('Layui-table-toolbar', {
			    			          	    where: { //设定异步数据接口的额外参数，任意设
			    			          	    	imp_model_name: $('#imp_model_name').val()
			    			        	    	,imp_table_name: $('#imp_table_name').val()
			    			          	    }
			    			          	  });
			    					});
			    				}
			    				else {
			    					layer.msg('删除失败', {icon: 5, time: time_});
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
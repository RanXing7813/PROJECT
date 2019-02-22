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
       // {type: 'checkbox', fixed: 'left'},
        {type: 'numbers', title:'序号', fixed: 'left',width:60}
        ,{field:'table_name', title:'表名', fixed: 'left',sort:true }
        ,{field:'table_cname', title:'表中文名',sort:true    } 
       // ,{field:'data_count', title:'表数据量',sort:true  }
        ,{fixed: 'right', title:'操作', fixed: 'right',width:220, align: 'center', toolbar: '#Layui-table-toolbar-barDemo'}
      ]]
      ,limit:10
      ,limits:[10,30,50,100,1000]
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
    	        	  table_name: $('#table_name').val()
    	  	    	,table_cname: $('#table_cname').val()
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
      if(obj.event === 'checkOne'){
          opendgviewLook(data);
        } 
    });
    
  	//新增页面
    function opendgviewLook( data){
    	   layui.use('layer', function(){
			  var $ = layui.$
			  ,layer = layui.layer
			  ,index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			     //给父页面传值
			      var name = '#model_info_table_name', cname="#model_info_table_cname";
			      parent.layui.$(name).val(data.table_name);
			      parent.layui.$(cname).val(data.table_cname);
			      parent.layer.close(index);
			});
    }
    
    

  });
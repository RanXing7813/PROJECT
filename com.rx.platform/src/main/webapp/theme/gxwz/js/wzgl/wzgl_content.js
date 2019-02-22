function submitInfo(){
	$("#next").click();
}
function submitInfoTJ(){
	$('#deleteFlog').val(4);
	$("#next").click();
}
//询问框
$('#infosortId').val(map.infost_id);

$(".vpostForm").Validform({
	btnSubmit : "next",
	tiptype : 2,
	showAllError : true,
	ignoreHidden : true,
	beforeCheck : function(curform) {
		// 在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
		// 这里明确return false的话将不会继续执行验证操作;
	},
	beforeSubmit : function(curform) {
		// 在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
		// 这里明确return false的话表单将不会提交;
		$('#next').attr('disabled',true);
	},
	callback : function(form) {
		var name_ = '暂存';
		if(4 == $('#deleteFlog').val() ){
			name_ = '提交'
		}
		
		var check =  false;//confirm("您确定要提交表单吗？");
		layer.confirm('您确定要表单'+name_+'吗？', {
			  btn: ['确认','取消'] //按钮
			}, function(index, layero){
				 var content = editor.getContent()
				 $('#content_').val(content);
				 
				 $.post("getWzgl_Save.json", $("#form-wizard").serialize(),
	 					function(data) {
					 	if(data.code=='SUCCESS'){
					 		$('#next').attr('disabled',true);
					 		backInfo();
					 	}else{
					 		alert(data.code);
					 	}
	 			 }); 
				layer.close(index);
			}, function(index, layero){
				$('#next').attr('disabled',false);
				layer.close(index);
			});
		return false;
	}
});

		   function saveInfo(){
			  var content = editor.getContent()
			  
			  $('#content_').val(content);
			    $.post("getWzgl_Save.json", $("#form-wizard").serialize(),
	 					function(data) {
	 			}); 
		   }
		   
		   function backInfo(){
				map.filter = null;
				 $.ajax({
			          url : "toWzgl_list",
			          async:false,//同步
			          data:{
			          },
			          type:"POST",
			          success: function(result) {
			        	  $('#contensBody').html(result);
			        	  go(map.page, map.name  );
			          }
				 });
				 $('.mg-b2').find('ul').eq(0).find('li').eq(1).removeClass('on');
				 $('.mg-b2').find('ul').eq(0).find('li').eq(0).removeClass('on');
				 	
			   if(map.typeDiv == 4){
				   jQuery(".article-tab").slide({
					 	trigger: "click",
						defaultIndex:0
					})
			   }else{ 
				   jQuery(".article-tab").slide({
					 	trigger: "click",
						defaultIndex:1
					})
				   
			   }
		   }
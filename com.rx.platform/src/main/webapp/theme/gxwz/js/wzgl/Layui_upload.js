function uploadLayui(){
	var url = "uploadPage";
	var index = layer.open({
		title : "文件上传",
		type : 2,
		area : [ '610px', '360px' ],
		btn : [ '关闭' ],
		btnAlign : 'r',// 按钮排列
		content : url,
		success : function(layero, index) {

		},
		yes : function(index, layero) {
			// 当点击‘确定’按钮的时候，获取弹出层返回的值
			//给父页面传值
			//callbackFunc(obj);// 回调函数
			 var body = layer.getChildFrame('body', index);//获取子页面内容   
//			 var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
//			 iframeWin.method();     
//			 body.find("#proInfo").click();//执行子页面的方法  
//			      body.find('input').val('Hi，我是从父页来的')
			 console.log(body.find("input[name='fileIds']"))
			 if(body.find("input[name='fileIds']")["0"].files.length > 0){
				 var name_ = body.find("input[name='fileIds']")["0"].files["0"].name;
				 var id_ =   body.find('#fileId').val();
				 parent.$('#parentUploadTd').append('<tr><td>'+name_+'</td><td>   <input type="hidden" name="fileIds"  value="'+id_+'" /><a href="javascript:void(0)" onclick="delupload(\''+id_+'\',this)">删除</a></td></td>');
				 
			 }	

			 	
//				alert(  layero.$('#proInfo').text())
//			parent.$('#parentUploadTd').text();
			//$(this).$("#progressBar").css('display','block');
				//getPross();
				
			layer.close(index);
			// 最后关闭弹出层
		},
//		btn2 : function(index, layero) {
//			layer.close(index);
//		},
		cancel : function() {
			layer.close(index);
		}
	})
}

function  delupload(id,obj){
	
	
	
	
	
	
	
	layer.confirm('您确定要提删除吗？将会直接删除附件。', {
		  btn: ['确认','取消'] //按钮
		}, function(index, layero){
			$.ajax({
				 url : "delSomeInfo",
			        data:{
			        	id:id
			        },
			        type:"GET",
			        success: function(result) {
			        	if(result == 'SUCCESS'){
			        		$(obj).parent().parent().remove();
			        	}else{
			        		layer.alert(result, {
							    skin: 'layui-layer-lan'
							    ,closeBtn: 0
							  });
			        	}
			        	
			        }
			});
			layer.close(index);
		}, function(index, layero){
			$('#next').attr('disabled',false);
			layer.close(index);
		});
	
}




var index = parent.layer.getFrameIndex(window.name); //获取窗口索引


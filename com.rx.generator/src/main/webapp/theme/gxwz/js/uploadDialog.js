//上传弹窗 
function uploadDialog(){
	layer.open({
		type: 2, //iframe
		title: "上传页", //标题
		shadeClose: true, //是否显示遮罩
		shade: 0.4, //透明度
		area: ['477px', '357px'], //窗口宽高
		skin: "gd", //皮肤
		content: 'file-up'//iframe的url
	});
}

//删除上传列表（包括数据库数据）
function delupload(obj){
	 //obj.parentNode.parentNode.remove();//删除tr行 
	
	var id;
	var v=obj.parentNode.parentNode.getElementsByTagName("input");
	for (i = 0; i < v.length; i++) {	
			id=v[i].value;
		}

		 	$.ajax({
		          url : "delfloglist.json",
		          dataType: "json", 
		          data:{
		        	  id:id
		          },
		          type:"POST", 	
		 success:function(msg){
		 obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
		 		}
	 })
}
 
$(function() {
	getDZCXList();
});

function getDZCXList(){
	map.ywlx=$("#cj_code").val();
	//map.way=$("input[name='way']:checked").val();
	map.fsfdept=$("#fsfdeptcode").val();
	map.jsfdept=$("#jsfdeptcode").val();
	map.starttime=$("#starttime").val();
	map.endtime=$("#endtime").val();
	map.status=$("#status").val();
	$.ajax({
		url : "getDZCXList",
		dataType: "html", 
		data:{
			models:JSON.stringify(map)
		},
		type:"post",
		success: function(result) {
			$("#dzpanelDiv").html(result);
		},
		error: function(result) {
			console.log("getDZCXList()---error");
		}
	});
}

function getDeptIframePage(){
	var ywlx=$("#cj_code").val();
	var fsfdept=$("#fsfdeptcode").val();
	var jsfdept=$("#jsfdeptcode").val();
	layer.open({
		type: 2, //iframe
		title: "部门选择", //标题
		shadeClose: true, //是否显示遮罩
		shade: 0.4, //透明度
		area: ['750px', '485px'], //窗口宽高
		//skin: "gd", //皮肤
		content: 'getDeptIframePage?ywlx='+ywlx+'&fsfdept='+fsfdept+'&jsfdept='+jsfdept //iframe的url
	});
}


$(function() {
	getSSCXList();
});

function getSSCXList(){
	map.ywlx=$("#cj_code").val();
	$.ajax({
		url : "getSSCXList",
		dataType: "json", 
		data:{
			models:JSON.stringify(map)
		},
		type:"post",
		success: function(result) {
			$("#searchtime").html(result.searchtime);
			var fsfop='<li class="title">发送方</li>';
			var zxfop='<li class="title" style="background: #53bf8a;">中心库</li>';
			var jsfop='<li class="title" style="background: #e8b71a;">接收方</li>';
			$.each(result.fsflist,function(n,value){
				var vv1 = value.deptname;		if(vv1 == null){vv1 = "";}
       			var vv2 = value.cnt;			if(vv2 == null){vv2 = "";}
				fsfop+='<li onclick="sscxDetail()"><span class="f-fl">'+vv1+'</span><span class="f-fr">'+toThousands(vv2)+'</span></li>';
			});
			$.each(result.zxflist,function(n,value){
				var vv1 = value.deptname;		if(vv1 == null){vv1 = "";}
       			var vv2 = value.cnt;			if(vv2 == null){vv2 = "";}
				zxfop+='<li onclick="sscxDetail()"><span class="f-fl"></span><span class="f-fr">'+toThousands(vv2)+'</span></li>';
			});
			$.each(result.jsflist,function(n,value){
				var vv1 = value.deptname;		if(vv1 == null){vv1 = "";}
       			var vv2 = value.cnt;			if(vv2 == null){vv2 = "";}
				jsfop+='<li onclick="sscxDetail()"><span class="f-fl">'+vv1+'</span><span class="f-fr">'+toThousands(vv2)+'</span></li>';
			});
			$("#fsflist").html(fsfop);
			$("#zxflist").html(zxfop);
			$("#jsflist").html(jsfop);
		},
		error: function(result) {
			console.log("getSSCXList()---error");
		}
	});
}

function sscxDetail(){
	var ywlx=$("#cj_code").val();
	var searchtime=$("#searchtime").html();
	layer.open({
		type: 2, //iframe
		title: "信息类详情", //标题
		shadeClose: true, //是否显示遮罩
		shade: 0.4, //透明度
		area: ['1160px', '485px'], //窗口宽高
		//skin: "gd", //皮肤
		content: 'getSSCXIframePage?ywlx='+ywlx+'&searchtime='+searchtime //iframe的url
	});
}


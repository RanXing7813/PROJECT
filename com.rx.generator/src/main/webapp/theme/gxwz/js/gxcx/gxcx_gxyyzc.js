$(function() {
	go(1);
});

var format = function(time, format){
	var t = new Date(time);
	var tf = function(i){return (i < 10 ? '0' : '') + i};
	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
		switch(a){
		case 'yyyy':
		return tf(t.getFullYear());
		break;
		case 'MM':
		return tf(t.getMonth() + 1);
		break;
		case 'mm':
		return tf(t.getMinutes());
		break;
		case 'dd':
		return tf(t.getDate());
		break;
		case 'HH':
		return tf(t.getHours());
		break;
		case 'ss':
		return tf(t.getSeconds());
		break;
		}
	})
}
function go(page){
	map.page=page;
	map.pagesize=5;
	$.ajax({
		url : "getChangjingList",
		dataType: "json", 
		data:{
			models:JSON.stringify(map)
		},
		type:"post",
		success: function(result) {
			var option='';
			$.each(result.list,function(n,value){
				var vv1 = value.cj_name;		if(vv1 == null){vv1 = "";}
       			var vv2 = value.cj_code;		if(vv2 == null){vv2 = "";}
       			var vv3 = value.dept_count;		if(vv3 == null){vv3 = "";}
       			var vv4 = value.xxl_count;		if(vv4 == null){vv4 = "";}
       			var vv5 = value.dept_f;			if(vv5 == null){vv5 = "";}
       			var vv6 = value.frequency;		if(vv6 == null){vv6 = "";}
       			var vv7 = value.operation_flag;	if(vv7 == null){vv7 = "";}
       			var vv8 = value.data_count;		if(vv8 == null){vv8 = "";}
       			var vv9 = value.last_tj_time;		if(vv9 == null){vv9 = "";}
       			var vv5title=vv5;
       			if(vv5.length>5){
       				vv5=vv5.substring(0,4)+"...";
       			}
       			option+='<div class="dzjh-dt1 mg-b2">';
       			option+=	'<div class="f-cb mg-b2">';
       			option+=		'<div class="title f-fl">'+vv1+'</div>';
       			option+=	'</div>';
       			option+=	'<div class="f-cb">';
       			option+=		'<div class="dzjh-tb f-fl">';
       			option+=			'<div class="wrap">';
       			option+=				'<table>';
       			option+=					'<tr>';
       			option+=						'<td>牵头部门：<span title="'+vv5title+'">'+vv5+'</span></td><td>涉及部门总数：'+vv3+'</td><td>涉及信息类总数：'+vv4+'</td>';
       			option+=					'</tr>';
       			option+=					'<tr>';
       			option+=						'<td>共享频率：'+vv6+'</td><td colspan="2">最近更新时间：'+format(vv9, 'yyyy-MM-dd HH:mm:ss')+'</td>';
       			option+=					'</tr>';
       			option+=				'</table>';
       			option+=			'</div>';
       			option+=		'</div>';
       			option+=		'<div class="dzjh-data f-fl">';
       			option+=			'<div class="wrap">';
       			option+=				'<p>累计交换量</p>';
       			option+=				'<em>'+toThousands(vv8)+'</em>';
       			option+=			'</div>';
       			option+=		'</div>';
       			option+=	'</div>';
       			option+='</div>';
			});
			//分页信息
			var z = result.total%map.pagesize;
			total = result.total ;
			var pageNum;
			if(z==0){
				pageNum=result.total/map.pagesize;
			}else{
				pageNum=Math.floor(result.total/map.pagesize)+1;
			}
			map.pagenum=pageNum;
			var pageoption=PageButten(page,pageNum);
			$("#totalNum").html("共"+total+"个应用");
			$("#cj_list").html(option+pageoption);
		},
		error: function(result) {
			console.log(result);
			console.log("getChangjingList()---error");
		}
	});
}

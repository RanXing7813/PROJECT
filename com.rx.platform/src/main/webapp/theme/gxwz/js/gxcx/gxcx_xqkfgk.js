$(function() {
	go(1);
});

function go(page){
	map.page=page;
	map.pagesize=10;
	$.ajax({
		url : "getGxcx_xqkfgk_list",
		dataType: "json", 
		data:{
			models:JSON.stringify(map)
		},
		type:"post",
		success: function(result) {
			var option='<tr>'
				+'<th>需求</th>'
				+'<th>提出部门</th>'
				+'<th>联系人</th>'
				+'<th>联系电话</th>'
				+'<th>提交时间</th>'
				+'<th>评估进度</th>'
				+'<th>完成进度</th>'
				+'</tr>';
			$.each(result.list,function(n,value){
				var v1 = value.BUSINESS;
				var v1title = v1;
				if(v1.length>6){
					v1=v1.substring(0,6)+'...';
				}
				var v2 = value.REQUIREORGNAME;
				var v2title = v2;
				if(v2.length>6){
					v2=v2.substring(0,6)+'...';
				}
				var v3 = value.CONTACT;
				var v4 = value.CONTACTTEL;
				var v5 = value.REQUIRESUBMITDATE;
				var PGWCS = value.PGWCS;
				var DATAITEMCOUNT = value.DATAITEMCOUNT;
				var PRIVODEDATAITEMCOUNT = value.PRIVODEDATAITEMCOUNT;
				if(typeof(PGWCS) == "undefined"){
					PGWCS=0;
				}
				if(typeof(DATAITEMCOUNT) == "undefined"){
					DATAITEMCOUNT=0;
				}
				if(typeof(PRIVODEDATAITEMCOUNT) == "undefined"){
					PRIVODEDATAITEMCOUNT=0;
				}
				var v6 = PGWCS+"/"+DATAITEMCOUNT;
				var v7 = PRIVODEDATAITEMCOUNT+"/"+DATAITEMCOUNT;
				option+='<tr>'
				+'<td title="'+v1title+'">'+v1+'</td>'
				+'<td title="'+v2title+'">'+v2+'</td>'
				+'<td>'+v3+'</td>'
				+'<td>'+v4+'</td>'
				+'<td>'+v5+'</td>'
				+'<td>'+v6+'</td>'
				+'<td>'+v7+'</td>'
				+'</tr>';
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
			$("#rlist-content").html(option);
			$("#listpage").html(pageoption);
		},
		error: function(result) {
			console.log(result);
			console.log("getGxcx_xqkfgk_list()---error");
		}
	});
}

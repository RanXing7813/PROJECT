$(function() {
	jkfwsearch(1);
});

//点击回车
$('#search').bind('keypress',function(event){  
    if(event.keyCode == "13"){  
    	jkfwsearch();
    }  
});

function jkfwsearch(page) {
	var map={};
	map.page=page;
	map.pageSize=3;
	var search = $("#search").val();
	var jktype = $("#jktype").val();
	if(search=='请输入查询关键词'){
		search='';
	}
	
	var tdname="所属目录";
	if(jktype=="ctgc"){
		$("#searchTitle").html("海口市服务服务接口");
		tdname="分类";
	}else{
		$("#searchTitle").html("国家部委服务接口");
	}
	
	map.search=search;
	map.jktype=jktype;
	$.ajax({
		url : "jkfwsearch",
		dataType : "json",
		data : {models:JSON.stringify(map)},
		type : "post",
		success : function(result) {
			var option='';
			$.each(result.list,function(n,value){
				option+='<div class="rlist-ml fn-clear">';
				option+='<div class="rlist-ml-title">';
				option+='<h4>'+value.open_service_name+'</h4>';
				option+='</div>';
				option+='<div class="rlist-ml-info" style="width: 860px;height:auto;min-height: 80px;">';
				option+='<table>';
				option+='<col width="300"></col>';
				option+='<col></col>';
				option+='<tr>';
				option+='<td>'+tdname+'：'+value.cata_title+'</td>';
				option+='<td>提供部门：'+value.dept+'</td>';
				option+='</tr>';
				option+='<tr>';
				option+='<td>版本号：'+value.version+'</td>';
				option+='<td>发布时间：'+value.publish_date+'</td>';
				option+='</tr>';
				option+='<tr>';
				option+='<td colspan="2">接口描述：'+value.service_des+'</td>';
				option+='</tr>';
				option+='</table>';
				option+='</div>';
				option+='</div>';
			});
			$("#rlist-content").html(option);
			$("#total").html("共"+result.total+"个接口");
			$("#jktype").val(result.jktype);
			var pageoption=PageButten(result.page,result.total,result.pageSize);
			$("#listpage").html(pageoption);
		},
		error : function(result) {
		}
	});
}

function go(page) {
	jkfwsearch(page);
}

function jkfw(page,jktype) {
	$("#jktype").val(jktype);
	$("#search").val('请输入查询关键词');
	
	if(jktype=="gjpt"){
		$('#gjpt').addClass('current');
		$('#ctgc').removeClass('current');
	}else{
		$('#gjpt').removeClass('current');
		$('#ctgc').addClass('current');
	}
	jkfwsearch(page);
}


$(function(){
	getTJData();//获取首页上方4个统计数据
	getMLDHXX();//获取目录导航中数据--基础数据--主题数据--部门数据
	getTop5_info();//获取目录动态-top5
	$("#index_mlfw_qwblval").val('false');
});

//全文检索
function qwjs(){
	var qwjsval = $("#input_qwjs").val();
	$("#bodys").load("getMLFW?qwjsval="+qwjsval);
}

//获取首页上方4个统计数据
function getTJData(){
	$.ajax({
		url : "getTJDataIndex",
		dataType : "json",
		data : {},
		type : "post",
		success : function(result) {
			if(result != null){
				$("#h_yfbgxml").html(result.yfbCount);
				$("#h_ljjhsjl").html(result.ljjhCount);
				$("#zcxtyy").html(result.zcxtyyCount);
				$("#zygjl").html(result.ygjP);
				
				if("sz_gxwz"==result.gxwzname){
					$("#zygjl_name").html("已创建服务数");
					$("#zcxtyy_name").html("累计运行任务数");
				}else{
					$("#zygjl_name").html("资源挂接率");
					$("#zcxtyy_name").html("支撑主题应用");
				}
			}
		},
		error : function(result) {
		}
	});
}

//目录导航数据
function getMLDHXX(){
	$.ajax({
		url : "getMLDHXX",
		dataType : "json",
		data : {},
		type : "post",
		success : function(result) {
			if(result != null){
				pub_showMLXX(result);
			}
		},
		error : function(result) {
		}
	});
}

//目录导航中点击进行跳转
function mldtToML(id,levelcode,catalogname,parentId){
	$("#bodys").load("getMLFW?id="+id+"&levelcode="+levelcode+"&catalogname="+catalogname+"&parentId="+parentId);
}

function pub_showMLXX(result){
	//-----------基础start--------------
	var jc_content = "";
	jc_content = "<table><tr>";
	$.each(result.jclist, function(n,value){    
		jc_content += '<td>'+
		"<a herf='javascript:;'  onclick = mldtToML('"+value.ID+"','"+value.PARENT_CODE+value.CATEGORY_CODE+"','"+value.CATEGORY_NAME+"','"+value.PARENT_ID+"'); style='width:198px;'>"+
		    	'<span class="ico mg-b3"><img src="'+value.PIC_URL+'"/></span>'+
		    	'<p class="f-tac">'+value.CATEGORY_NAME+'</p>'+
		    '</a> '+
	    '</td>';
	 });
	jc_content += "</tr></table>";
	$("#div_jcxxzy").html(jc_content);
	//-----------基础end--------------
	
	//-----------主题start--------------
	var zt_page ='<li class="mg-r6"></li>';
	var zt_content  = '<table><colgroup><col width="20%" /><col width="20%" /><col width="20%" />'+
	'<col width="20%" /><col width="20%" /></colgroup>';
	var zt_value = result.ztlist;
	var zt_pageNum = 0;
	if(zt_value.length >0 && zt_value.length % 10 == 0){
		zt_pageNum = parseInt(zt_value.length/10);
	}else{
		zt_pageNum = parseInt(zt_value.length/10)+1;
	}
	for (var i = 0; i < zt_pageNum; i++) {
		if (i>0) {
			zt_page += '<li class="mg-r6"></li>';
			zt_content += '</table>';
			zt_content += '<table><colgroup><col width="20%" /><col width="20%" /><col width="20%" />'+
			'<col width="20%" /><col width="20%" /></colgroup>';
		}
		var zt_couont = 0;
		zt_content +="<tr>";
		for(var j=i*10 ; j<(i+1)*10 ; j++){
			if(zt_couont == 5){
				zt_content +="</tr><tr>";
				zt_couont = 0;
			}
			if(j < zt_value.length){
				zt_content +=  '<td>'+
				 "<a herf='javascript:;'  onclick = mldtToML('"+zt_value[j].ID+"','"+zt_value[j].PARENT_CODE+zt_value[j].CATEGORY_CODE+"','"+zt_value[j].CATEGORY_NAME+"','"+zt_value[j].PARENT_ID+"'); >"
				 +'<span class="ico mg-b3">'+
					'<img src="'+zt_value[j].PIC_URL+'"/></span>'+
					'<p class="f-tac">'+zt_value[j].CATEGORY_NAME+'</p></a>'+ 
				'</td>';
			}
			zt_couont ++;
		}
		zt_content +="</tr>";
	}
	zt_content += '</table>';
	$("#div_ztxxzy").html(zt_content);
	$("#ul_ztxxzy_page").html(zt_page);
	//-----------主题end--------------
	//-----------部门start--------------
	var bm_page ='<li class="mg-r6"></li>';
	var bm_content = '<table><colgroup><col width="25%" /><col width="25%" /><col width="25%" />'+
			'<col width="25%" /></colgroup>';
	var bm_value = result.bmlist;
	var bm_pageNum = 0;
	if(bm_value.length >0 && bm_value.length % 28 == 0){
		bm_pageNum = parseInt(bm_value.length/28);
	}else{
		bm_pageNum = parseInt(bm_value.length/28)+1;
	}
	for (var i = 0; i < bm_pageNum; i++) {
		if (i>0) {
			bm_page += '<li class="mg-r6"></li>';
			bm_content += '</table>';
			bm_content += '<table><colgroup><col width="25%" /><col width="25%" /><col width="25%" />'+
			'<col width="25%" /></colgroup>';
		}
		var bm_couont = 0;
		bm_content +="<tr>";
		for(var j=i*28 ; j<(i+1)*28 ; j++){
			if(bm_couont == 4){
				bm_content +="</tr><tr>";
				bm_couont = 0;
			}
			if(j < bm_value.length){
				var titleVal  = bm_value[j].CATEGORY_NAME;
				var contentval = titleVal;
				if(titleVal.length > 10){
					contentval = titleVal.substring(0,9)+"...";
				}
				bm_content +=  '<td>'+
						"<a style='cursor:pointer;'   title='"+titleVal+"'  herf='javascript:;'  onclick = mldtToML('"+bm_value[j].ID+"','"+bm_value[j].PARENT_CODE+bm_value[j].CATEGORY_CODE+"','"+bm_value[j].CATEGORY_NAME+"','"+bm_value[j].PARENT_ID+"'); >"
						+contentval+'</a>'+
						'</td>';
			}
			bm_couont ++;
		}
		bm_content +="</tr>";
	}
	bm_content += '</table>';
	$("#div_bmzyxx").html(bm_content);
	$("#ul_bmzyxx_page").html(bm_page);
	
	$(".slidec2").slide({
		titCell: ".parhd ul li",
		mainCell: ".parbd",
		effect: "left",
	});
	$(".slidec3").slide({
		titCell: ".parhd ul li",
		mainCell: ".parbd",
		effect: "left",
	});
	$(".slidec4").slide({
		titCell: ".parhd ul li",
		mainCell: ".parbd",
		effect: "left",
	});
	$(".slideMlBox").slide();
}

//获取最新最热目录数据
function getTop5_info(){
	var content_new = '';
	var content_hot = '';
	$.ajax({
		url : "getCatalogDynamic",
		dataType : "json",
		data : {},
		type : "post",
		success : function(result) {
			if(result != null){
				//最新
				$.each(result.mlNewestList, function(n,value){
					
					var nameTitle = value.INFONAME;
					var nameVal = nameTitle;
					if(nameVal.length > 15){
						nameVal = nameVal.substring(0,14)+"...";
					}
					
					var deptTitle = value.PROVIDEBMNAME;
					var deptVal = deptTitle;
					if(deptVal.length > 20){deptVal = deptVal.substring(0,19)+"...";}
					content_new += '<li>'+
										'<a href="javascript:;"  onclick="openDetail(\''+value.ID+'\')" class="title" title="'+nameTitle+'">'+nameVal+'<span class="hot">NEW</span></a>'+
										'<div class="f-cb">'+
											'<div class="f-fl">'+
												'<span class="source" title="'+deptTitle+'">来源：'+deptVal+'</span>'+
											'</div>'+
											'<div class="f-fr">'+
												'<span class="source">'+value.UPDATETIME+'</span>'+
											'</div>'+
										'</div>'+
									'</li>';
				 });
				$("#ul_newslist").html(content_new);
				//最热
				$.each(result.mlHottestList, function(n,value){
					var nameTitle = value.INFONAME;
					var nameVal = nameTitle;
					if(nameVal.length > 15){
						nameVal = nameVal.substring(0,14)+"...";
					}
					
					var deptTitle = value.DEPTNAME;
					var deptVal = deptTitle;
					if(deptVal.length > 20){deptVal = deptVal.substring(0,19)+"...";}
					content_hot += '<li>'+
										//'<span   class="title">'+nameVal+'<span class="hot">HOT</span></span>'+
										'<a href="javascript:;"  onclick="openDetail(\''+value.ID+'\')" class="title" title="'+nameTitle+'">'+nameVal+'<span class="hot">HOT</span></a>'+
										'<div class="f-cb">'+
											'<div class="f-fl">'+
												'<span class="source" title="'+deptTitle+'">来源：'+deptVal+'</span>'+
											'</div>'+
											'<div class="f-fr">'+
												'<span class="source">申请量：'+value.APPLY_COUNT+'次</span>'+
											'</div>'+
										'</div>'+
									'</li>';
				 });
				$("#ul_hotlist").html(content_hot);
			}
		},
		error : function(result) {
		}
	});
}

function openDetail(id){
	layer.open({
		type: 2, //iframe
		title: "详情", //标题
		shadeClose: true, //是否显示遮罩
		shade: 0.4, //透明度
		area: ['1240px', '800px'], //窗口宽高
		skin: "gd", //皮肤
		content: 'viewCatalog?id='+id //iframe的url
	});
}

//点击回车
$('#input_qwjs').bind('keypress',function(event){  
    if(event.keyCode == "13"){  
    	 qwjs();
    }  
});

function toThousands(num) {
	return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
}
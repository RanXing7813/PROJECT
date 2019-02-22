var map = {};
var catalevelcode = '';
var totalCount = '';
var searchcontent = '请输入查询关键词';
$(document).ready(function(){
	var mlfwID = $("#index_mlfw_id").val();
	var mlfwLevelcode = $("#index_mlfw_levelcode").val();
	var mlfwCatalogname = $("#index_mlfw_catalogname").val();
	var mlfwParentId = $("#index_mlfw_parentId").val();
	var mlfwQjblVal = $("#index_mlfw_qwblval").val(); //全局变量控制是否执行
	map.pageSize = 5;
	map.pagenum = 1;
	//---gxwz_infoclass_catalog_rel--读取列表//获取树
	/*if(mlfwID != null && mlfwID != '' && mlfwID != 'undefined' && mlfwID != undefined ){
	}else{mlfwID = 'jc';}
	if(mlfwLevelcode != null && mlfwLevelcode != '' && mlfwLevelcode != 'undefined' && mlfwLevelcode != undefined ){
	}else{mlfwLevelcode = '1';}
	if(mlfwCatalogname != null && mlfwCatalogname != '' && mlfwCatalogname != 'undefined' && mlfwCatalogname != undefined ){
	}else{mlfwCatalogname = '基础数据资源目录';}*/
	if(mlfwQjblVal == 'true'){
		//在首页进行搜索--start
		$("#getMLFW").prop('class',"current");
		$("#getGxwzIndex").prop('class',"");
		var mlfwQwjsVal = $("#index_mlfw_qwjsval").val().trim();
		if(mlfwQwjsVal != ''   && mlfwQwjsVal != null  && mlfwQwjsVal != undefined){
			map.searchVal = mlfwQwjsVal; // 作为查询条件
			map.levelcodestr = '%';
			go(1,'%','','search');
			//在首页进行搜索--end
		}/*else{
			getJBXXL(mlfwID,mlfwLevelcode,mlfwCatalogname);  //默认是加载基础目录
		}*/
	}
	
	//对选中的目录菜单进行样式加载
	if(mlfwParentId == 'zt'){
		$("#li_curr_jc").attr("class","submenu-item");
		$("#li_curr_zt").attr("class","submenu-item  current");
		$("#li_curr_bm").attr("class","submenu-item");
		getJBXXL('zt','2','主题信息资源目录','true');
	}else if(mlfwParentId == 'bm'){
		$("#li_curr_jc").attr("class","submenu-item");
		$("#li_curr_zt").attr("class","submenu-item");
		$("#li_curr_bm").attr("class","submenu-item  current");
		getJBXXL('bm','3','部门信息资源目录','true');
	}else if(mlfwParentId == 'jc'){
		$("#li_curr_jc").attr("class","submenu-item current");
		$("#li_curr_zt").attr("class","submenu-item");
		$("#li_curr_bm").attr("class","submenu-item");
		getJBXXL('jc','1','基础数据资源目录','true');
	}else{
		$("#li_curr_jc").attr("class","submenu-item");
		$("#li_curr_zt").attr("class","submenu-item");
		$("#li_curr_bm").attr("class","submenu-item");
		getCataList();
	}
});
function getJBXXL(id,levelcodestr,catalogname,isrun){
	$("#input_search").val(searchcontent);
	catalevelcode = levelcodestr;
	var treeHtml = '';
	$.ajax({
		url : "getCatalogTree.json",
		dataType : "json",
		data : {"pid":id,"levelcode":levelcodestr},
		type : "POST",
		success : function(result) {
			if(result != null){
				$.each(result.list, function(n,value){  
				    var checkFlag = this.HASCHILD;
				    var levelCodeVal = this.LEVELCODE;
				    var _len_levelClass = levelCodeVal.length;
				    var levelclass_str = "submenu-item"; var  levelclass_str_no = "no-submenu";
				    if(_len_levelClass <=3){
				    	levelclass_str = "submenu-item";  levelclass_str_no = "no-submenu";
				    }else if (_len_levelClass == 6){
				    	levelclass_str = "submenu-item";  levelclass_str_no = "no-thirdmenu";
				    }else if(_len_levelClass ==9){
				    	//针对部门层级
				    	levelclass_str = "submenu-item";  levelclass_str_no = "no-submenu";
				    }else if(_len_levelClass ==18){
				    	//针对部门层级
				    	levelclass_str = "thirdmenu";  levelclass_str_no = "no-forthmenu";
				    }else{
				    	levelclass_str = "thirdmenu";  levelclass_str_no = "no-thirdmenu";
				    }
				    var catalognameVal = value.RESNAME;
				    var catalognameTitle = catalognameVal;
				    if(catalognameVal.length > 9){catalognameVal = catalognameVal.substring(0,8)+"...";};
				    if(checkFlag == "true"){
				    	treeHtml += " <li ><a title='"+catalognameTitle+"' href='javascript:void(0)'   onclick=getJBXXL('"+this.RESID+"','"+this.LEVELCODE+"','"+this.RESNAME+"')     class='"+levelclass_str+"'>"+catalognameVal+"</a>"
		    	   		+"<ul id='ul_tree_"+this.RESID+"'  style='display:none;'></ul>"
		    			+"</li>";
				    }else{
				    	treeHtml += " <li><a title='"+catalognameTitle+"'  href='javascript:void(0)'  onclick=go(1,'"+this.LEVELCODE+"','"+this.RESNAME+"')       class='"+levelclass_str_no+"'>"+catalognameVal+"</a></li>";
				    }
				 });
			}
			$("#ul_tree_"+id).html(treeHtml);
		},
		error : function(result) {
		}
	});
	if(isrun != 'true'){
		go(1,levelcodestr,catalogname);//获取右侧列表
	}
}


//获取右侧列表
function go(page,levelcodestr,catagoryname,type){
	if(type == 'search' || page > 1){
	}else{
		map.searchVal = '';
		$("#input_search").val(searchcontent);
	}
	if(levelcodestr != null && levelcodestr != 'null' && levelcodestr != 'undefined' && levelcodestr != undefined){
		map.levelcodestr = levelcodestr;
	}
	/*if(catagoryname != null && catagoryname != 'null' && catagoryname != 'undefined' && catagoryname != undefined){
		map.catagoryname = catagoryname;
	}*/
	if(page != null && page != 'null' && page != 'undefined' && page != undefined){
		map.page=page;
	}
	
	var gxfstype = $('input[name="gxfstype"]:checked').val();
	var ishangontype = $('input[name="ishangontype"]:checked').val();
	map.gxfstype = gxfstype;
	map.ishangontype = ishangontype;
	var infoclassList = '';
	$.ajax({
		url : "getInfoclassList.json",
		dataType : "json",
		data:{
			models:JSON.stringify(map)
		},
		type : "post",
		success : function(result) {
			if(result != null){
				
				var mxxturl = result.mxxturl;
				
				$.each(result.list, function(n,value){  
					var id = value.ID;
					var infonameTitle = value.INFONAME;
					var infonameVal = infonameTitle;	if(infonameVal.length > 25){ infonameVal = infonameVal.substring(0,24)+"..."; }
					var visitcount = value.VISIT_COUNT; if(visitcount == 'undefined' || visitcount == undefined){ visitcount = 0; }
					var applycount = value.APPLY_COUNT; if(applycount == 'undefined' || applycount == undefined){ applycount = 0; }
					var lastTime = value.CATALOG_TABLE_LASTTIME; if(lastTime == 'undefined' || lastTime == undefined || lastTime == null || lastTime == ''){ lastTime = '暂无数据'; }
					var isGJ = value.HITCH_STATUS;if(isGJ == '1'){isGJ = '已挂接'}else{isGJ = '未挂接';}
					var sharewayval = value.SHAREMODETYPENAME; if(sharewayval == null || sharewayval == ''){sharewayval = '其他'}
					var jhlcount = value.EXCHANGE_COUNT;
					if(jhlcount != null && jhlcount != 'null' && jhlcount != undefined && jhlcount != 'undefined'){}else{jhlcount = 0;}
					var li_infoformat = '';
					if(sharewayval.indexOf("数据库")>-1){
						li_infoformat = ' <li><a href="javascript:void(0)" style="cursor:default;" class="data-on"><span style="color: #1781e0;">数据库</span></a></li>'
		                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="file"><span>文件</span></a></li>'
		                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="api"><span>接口</span></a></li>'
						+'  <li><a href="javascript:void(0)" style="cursor:default;" class="other"><span>其他</span></a></li>';
					}else if(sharewayval.indexOf("文件")>-1){
						li_infoformat = ' <li><a href="javascript:void(0)" style="cursor:default;" class="data"><span>数据库</span></a></li>'
			                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="file-on"><span style="color: #1781e0;">文件</span></a></li>'
			                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="api"><span>接口</span></a></li>'
			                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="other"><span>其他</span></a></li>';
					}else if(sharewayval.indexOf("接口")>-1){
						li_infoformat = ' <li><a href="javascript:void(0)" style="cursor:default;" class="data"><span>数据库</span></a></li>'
			                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="file"><span>文件</span></a></li>'
			                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="api-on"><span style="color: #1781e0;">接口</span></a></li>'
			                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="other"><span>其他</span></a></li>';
					}else{
						li_infoformat = ' <li><a href="javascript:void(0)" style="cursor:default;" class="data"><span>数据库</span></a></li>'
			                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="file"><span>文件</span></a></li>'
			                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="api"><span>接口</span></a></li>'
			                +'  <li><a href="javascript:void(0)" style="cursor:default;" class="other-on"><span style="color: #1781e0;">其他</span></a></li>';
					}
					var shareType = value.SHARETYPE;  //共享类型：1 有条件共享2无条件共享3不予共享
					var shareTypeVal = '暂无数据';
					if(shareType == 1){
						shareTypeVal = '有条件共享';
					}else if(shareType == 2){
						shareTypeVal = '无条件共享';
					}else if(shareType == 3){
						shareTypeVal = '不予共享';
					}
					var tgbmVal = value.PROVIDEBMNAME;
					if(tgbmVal  == null || tgbmVal == '' || tgbmVal == undefined || tgbmVal == 'undefined'){tgbmVal = '暂无数据';}
					var infotypenameTitle = value.INFOTYPENAME;
					var infotypenameVal = infotypenameTitle;	if(infotypenameVal.length > 12){ infotypenameVal = infotypenameVal.substring(0,12)+"..."; }
					infoclassList +=  ' <div class="rlist-ml fn-clear">'
		                +' <div class="rlist-ml-title"><h4 style="cursor:pointer;" title="'+infonameTitle+'" onclick="openDetail(\''+id+'\')">'+infonameVal+'</h4><span><font class="fontblue">['+shareTypeVal+']</font><font class="fontgreen">['+isGJ+']</font></span><a href="'+mxxturl+'" target="_blank">申请共享</a></div>'
		                +' <div class="rlist-ml-info">'
		                +' <table>'
		                +'  <col width="260"> </col>'
		                +'  <col > </col>'
		                +' <tr>'
		                +' <td>分类：<span title="'+infotypenameTitle+'">'+infotypenameVal+'</span></td><td>提供部门：'+tgbmVal+'</td></tr>'
		                +' <tr> <td>更新周期：'+value.UPDATECYCLE+'</td><td>最近更新时间：'+lastTime+'</td></tr>'
		                +' <tr > <td  colspan="2"><span><img src="theme/gxwz/images/icon_fwl.png" /><font>访问量 ： '+visitcount+'</font> </span>'
		                +'<span><img src="theme/gxwz/images/icon_sqcs.png" /><font>申请次数 ： '+applycount+'</font>  </span>'
		                +'<span><img src="theme/gxwz/images/detail/dzjh-data.png" style="width:14px;height:14px;padding-top:8px;padding-right:5px;"/><font>数据量 ： '+toThousands(jhlcount)+'</font>  </span>'
		                +'</td></tr>'
		                +'  </table>'
		                +'   </div>'
		                +' <div class="rlist-ml-btn">'
		                +'  <ul>'
		                + li_infoformat
		                +' </ul>'
		                +' </div>'
		                +'  </div>';
				 });
			}
			$("#div_infoclass_list").html(infoclassList);
			//分页信息
			var z = result.total%map.pageSize;
			totalCount = result.total;
			$("#td_toalcount").html("共"+totalCount+"条目录");
			var pageNum;
			if(z==0){
				pageNum=result.total/map.pageSize;
			}else{
				pageNum=Math.floor(result.total/map.pageSize)+1;
			}
			map.pagenum=pageNum;
			var pageoption=PageButten(page,pageNum);
			$("#listpage").html(pageoption);
		},
		error : function(result) {
		}
	});
}
//上方查询--输入信息类或者提供部门--查询以及排序用到该方法
function getinfoclassSearch(type,sort){
	//console.log(catalevelcode+">>>>"+map.levelcodestr);
	//上方的查询
	var searchVal = $("#input_search").val();
	map.searchVal = searchVal; //
	if(sort == 'sort'){
		map.sort = type;
		go(1,map.levelcodestr,map.catagoryname,'search');
	}else{
		go(1,map.levelcodestr,map.catagoryname,type);
	}
}
//点击【全部资源目录】需要清空levelcode
function getCataList(){
	map.levelcodestr = '%'; //更改为%为获取所有等级数据
	map.catagoryname = '';
	go(1,map.levelcodestr,map.catagoryname);
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
$('#input_search').bind('keypress',function(event){  
    if(event.keyCode == "13"){  
    	getinfoclassSearch('search');
    }  
});



function toThousands(num) {
	return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
}
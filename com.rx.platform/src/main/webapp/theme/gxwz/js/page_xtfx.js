/**
 * 分页方法
 * page:当前页
 * pagenum:总页数
 * @returns
 */
function PageButten(page,total,pageSize){
	
	var pagenum;
	var z = total%pageSize
	if(z==0){
		pagenum=total/pageSize;
	}else{
		pagenum=Math.floor(total/pageSize)+1;
	}
	var option='';
	option+='<div class="f-fr">';
	option+='<span class="pageNum">共'+pagenum+'页</span>';
	option+='<a href="javascript:void(0)" class="pageNum" onclick="go(1)">首页</a>';
	if(page>1){
		option+='<a href="javascript:void(0)" class="pageNum prev" onclick="go('+(page-1)+')">上一页</a>';
	}else{
		option+='<a href="javascript:void(0)" class="pageNum prev" style="cursor: default;">上一页</a>';
	}
	var dqPage = page;//得到当前页数
    dqPage = parseInt(dqPage);//得到的文本转成int
    var pageCount = pagenum;//得到总页数
    pageCount = parseInt(pageCount);
    var i = 1;
    i = parseInt(i);
    var item="";
    if (pageCount <= 5 ) {//总页数小于五页，则加载所有页
        for (i; i <= pageCount; i++) {
            if (i == dqPage) {
                item += '<a href="javascript:void(0)" class="pageNum active">'+i+'</a>';
            }else{
                item += '<a href="javascript:void(0)" class="pageNum" onclick="go('+i+')">'+i+'</a>';
            }
        };
    }else if (pageCount > 5) {//总页数大于五页，则加载五页
        if (dqPage < 5) {//当前页小于5，加载1-5页
            for (i; i <= 5; i++) {
                if (i == dqPage) {
                	item += '<a href="javascript:void(0)" class="pageNum active">'+i+'</a>';
                }else{
                	item += '<a href="javascript:void(0)" class="pageNum" onclick="go('+i+')">'+i+'</a>';
                }
            };
            if (dqPage <= pageCount-2) {//最后一页追加“...”代表省略的页
            	item += '<a href="javascript:void(0)" class="pageNum" style="cursor: default;"> . . . </a>';
            }
        }else if (dqPage >= 5) {//当前页大于5页
            for (i; i <= 2; i++) {//1,2页码始终显示
            	item += '<a href="javascript:void(0)" class="pageNum" onclick="go('+i+')">'+i+'</a>';
            }
            item += '<a href="javascript:void(0)" class="pageNum" style="cursor: default;"> . . . </a>';//2页码后面用...代替部分未显示的页码
            if (dqPage+1 == pageCount) {//当前页+1等于总页码
                for(i = dqPage-1; i <= pageCount; i++){//“...”后面跟三个页码当前页居中显示
                    if (i == dqPage) {
                    	item += '<a href="javascript:void(0)" class="pageNum active">'+i+'</a>';
                    }else{
                    	item += '<a href="javascript:void(0)" class="pageNum" onclick="go('+i+')">'+i+'</a>';
                    }
                }
            }else if (dqPage == pageCount) {//当前页数等于总页数则是最后一页页码显示在最后
                for(i = dqPage-2; i <= pageCount; i++){//...后面跟三个页码当前页居中显示
                    if (i == dqPage) {
                    	item += '<a href="javascript:void(0)" class="pageNum active">'+i+'</a>';
                    }else{
                    	item += '<a href="javascript:void(0)" class="pageNum" onclick="go('+i+')">'+i+'</a>';
                    }
                }
            }else{//当前页小于总页数，则最后一页后面跟...
                for(i = dqPage-1; i <= dqPage+1; i++){//dqPage+1页后面...
                    if (i == dqPage) {
                    	item += '<a href="javascript:void(0)" class="pageNum active">'+i+'</a>';
                    }else{
                    	item += '<a href="javascript:void(0)" class="pageNum" onclick="go('+i+')">'+i+'</a>';
                    }
                }
                item += '<a href="javascript:void(0)" class="pageNum" style="cursor: default;"> . . . </a>';
            }
        }
    }
    option+=item;
    if(page<pagenum){
		option+='<a href="javascript:void(0)" class="pageNum prev" onclick="go('+(page+1)+')">下一页</a>';
	}else{
		option+='<a href="javascript:void(0)" class="pageNum next" style="cursor: default;">下一页</a>';
	}
	option+='<a href="javascript:void(0)" class="pageNum" onclick="go('+pagenum+')">末页</a>';
	option+='<div class="pageNum"><input type="text" id="page_a" value="'+page+'" onkeyup="pagekeyup(this.value)" />';
	option+=	'<a href="javascript:void(0)" onclick="PageChangeButten()">跳转</a>';
	option+='</div>';
	option+='</div>';
	return option;
}

/**
 * 跳转按钮
 * @returns
 */
function PageChangeButten(){
	var page=$("#page_a").val();
	if(page==""){
		alert("跳转页不能为空！");
		return;
	}
	go(page);
}

/**
 * 跳转文本框
 * @param val
 * @returns
 */
function pagekeyup(val){
    if(val.length ==1){  
        $('#page_a').val(val.replace(/[^1-9]/g,''));  
    }else{
        $('#page_a').val(val.replace(/\D/g,''));  
    }
    if($('#page_a').val()>map.pagenum){
    	$('#page_a').val(map.pagenum);  
    }
}

/**
 * 千分位
 * @param num
 * @returns
 */
function toThousands(num) {
	var result = '';
	if(num === undefined){
		result = '0'
	}else{
		num =Number(num).toFixed(0).replace(/(\d)(?=(\d{3})+\.)/g, '$1,') 
	    var num = (num || 0).toString();
	    while (num.length > 3) {
	        result = ',' + num.slice(-3) + result;
	        num = num.slice(0, num.length - 3);
	    }
	    if (num) { result = num + result; }
	}
    return result;
}
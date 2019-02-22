//注意：parent 是 JS 自带的全局对象，可用于操作父页面
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

function changeDept(type,deptdiv){
	//将选中的进行变色(选中或未选中状态)	
	$("#"+deptdiv).toggleClass("bluecolor");
	$("#"+deptdiv).children().children().toggleClass("bluefontcolor");
	var name=$("#"+deptdiv).children().children().next().attr("name");
	var choicemodel="";
	var depts="";
	if("1"==type){
		//是否处于未选中状态
		if(""==name){
			$("#"+deptdiv).children().children().next().attr("name","fsfdeptcode");
			$("#"+deptdiv).children().children().next().next().attr("name","fsfdeptname");
		}else{
			$("#"+deptdiv).children().children().next().attr("name","");
			$("#"+deptdiv).children().children().next().next().attr("name","");
		}
		$('input[name="fsfdeptcode"]').each(function () {
			depts+=$(this).val()+",";
        });
		//通过发送方获取接收方的值(获取并修改接收方的值)
		choicemodel="2";
	}else{
		//是否处于未选中状态
		if(""==name){
			$("#"+deptdiv).children().children().next().attr("name","jsfdeptcode");
			$("#"+deptdiv).children().children().next().next().attr("name","jsfdeptname");
		}else{
			$("#"+deptdiv).children().children().next().attr("name","");
			$("#"+deptdiv).children().children().next().next().attr("name","");
		}
		$('input[name="jsfdeptcode"]').each(function () {
			depts+=$(this).val()+",";
        });
		//通过接收方获取发送方的值(获取并修改发送方的值)
		choicemodel="1";
	}
	if(depts.length>0){
		depts=depts.substring(0,depts.length-1);
	}
	var map={};
	map.ywlx=$("#ywlx").val();
	map.depts=depts;
	map.choicemodel=choicemodel;
	//跳转后台更新页面
	$.ajax({
		url : "changeDeptIframePage",
		dataType: "json", 
		data:{
			models:JSON.stringify(map)
		},
		type:"post",
		success: function(result) {
			var option='';
			//修改发送方
			if("1"==choicemodel){
				//需要显示的
				var showdept="";
				//不需要显示的(先获取全部)
				var hiddendpet=$("#fsfdept").val();
				
				$.each(result.deptlist,function(n,value){
					showdept+=value.dept+",";
					hiddendpet=remove(hiddendpet, value.dept);
				});
				if(showdept.length>0){
					showdept=showdept.substring(0,showdept.length-1);
				}
				if(showdept.length>0){
					//形成数组
					var showdepts=showdept.split(",");
					//循环处理
					for(var i=0;i<showdepts.length;i++){
						$("#fsf_"+showdepts[i]).attr("class","jhdz-dt1 f-fl bluecolor");
						$("#fsf_"+showdepts[i]).children().children().attr("class","bluefontcolor");
						$("#fsf_"+showdepts[i]).children().children().next().attr("name","fsfdeptcode");
						$("#fsf_"+showdepts[i]).children().children().next().next().attr("name","fsfdeptname");
					}
				}
				if(hiddendpet.length>0){
					//形成数组
					var hiddendepts=hiddendpet.split(",");
					for(var i=0;i<hiddendepts.length;i++){
						$("#fsf_"+hiddendepts[i]).attr("class","jhdz-dt1 f-fl");
						$("#fsf_"+hiddendepts[i]).children().children().attr("class","");
						$("#fsf_"+hiddendepts[i]).children().children().next().attr("name","");
						$("#fsf_"+hiddendepts[i]).children().children().next().next().attr("name","");
					}
				}
			}else{//修改接收方
				//需要显示的
				var showdept="";
				//不需要显示的(先获取全部)
				var hiddendpet=$("#jsfdept").val();
				
				$.each(result.deptlist,function(n,value){
					showdept+=value.dept+",";
					hiddendpet=remove(hiddendpet, value.dept);
				});
				if(showdept.length>0){
					showdept=showdept.substring(0,showdept.length-1);
				}
				if(showdept.length>0){
					//形成数组
					var showdepts=showdept.split(",");
					//循环处理
					for(var i=0;i<showdepts.length;i++){
						$("#jsf_"+showdepts[i]).attr("class","jhdz-dt1 f-fl bluecolor");
						$("#jsf_"+showdepts[i]).children().children().attr("class","bluefontcolor");
						$("#jsf_"+showdepts[i]).children().children().next().attr("name","jsfdeptcode");
						$("#jsf_"+showdepts[i]).children().children().next().next().attr("name","jsfdeptname");
					}
				}
				if(hiddendpet.length>0){
					//形成数组
					var hiddendepts=hiddendpet.split(",");
					for(var i=0;i<hiddendepts.length;i++){
						$("#jsf_"+hiddendepts[i]).attr("class","jhdz-dt1 f-fl");
						$("#jsf_"+hiddendepts[i]).children().children().attr("class","");
						$("#jsf_"+hiddendepts[i]).children().children().next().attr("name","");
						$("#jsf_"+hiddendepts[i]).children().children().next().next().attr("name","");
					}
				}
			}
		},
		error: function(result) {
			console.log("changeDeptIframePage()---error");
		}
	});
}
function remove(a,b){
    var c=a.split(','),d="";
    for(var i=0;i<c.length;i++){
        d+=c[i]==b?"":","+c[i];
    }
    return d.length>0?d.substring(1):"";
}

//给父页面传值
$('#savabutten').on('click', function(){
	var fsfcode="";
	$('input[name="fsfdeptcode"]').each(function () {
		fsfcode+=$(this).val()+",";
    });
	if(fsfcode.length>0){
		fsfcode=fsfcode.substring(0,fsfcode.length-1);
	}else{
		alert("发送方选择不能为空！");
		return;
	}
	var jsfcode="";
	$('input[name="jsfdeptcode"]').each(function () {
		jsfcode+=$(this).val()+",";
    });
	if(jsfcode.length>0){
		jsfcode=jsfcode.substring(0,jsfcode.length-1);
	}else{
		alert("接收方选择不能为空！");
		return;
	}
	
	var fsfname="";
	$('input[name="fsfdeptname"]').each(function () {
		fsfname+=$(this).val()+",";
    });
	if(fsfname.length>0){
		fsfname=fsfname.substring(0,fsfname.length-1);
	}
	var jsfname="";
	$('input[name="jsfdeptname"]').each(function () {
		jsfname+=$(this).val()+",";
    });
	if(jsfname.length>0){
		jsfname=jsfname.substring(0,jsfname.length-1);
	}
	parent.$('#fsfdeptcode').val(fsfcode);
    parent.$('#jsfdeptcode').val(jsfcode);
    parent.$('#fsfdeptname').text(fsfname);
    parent.$('#jsfdeptname').text(jsfname);
    parent.layer.close(index);
});
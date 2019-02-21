//年份切换(五证合一使用)
$("input[name='model']").change(function(){
	var r=$("input[name='model']:checked").val(); 
	if(r==1){
		$("#modeltd").attr("colspan","3");
        $("#search_year_th").attr("style","display: none;");
        $("#search_year_td").attr("style","display: none;");
	}else{
		$("#modeltd").attr("colspan","1");
	    $("#search_year_th").attr("style","");
	    $("#search_year_td").attr("style","");
	}
});

//选择发送方重新查询接收方
$("#fsfdept").change(function(){
	map.ywlx=$("#cj_code").val();
	map.choicemodel="2";
	map.depts=$("#fsfdept").val();
	$.ajax({
		url : "changeDeptIframePage",
		dataType: "json", 
		data:{
			models:JSON.stringify(map)
		},
		type:"post",
		success: function(result) {
			var option='';
			$.each(result.deptlist,function(n,value){
				option+='<option value="'+value.dept+'">'+value.deptname+'</option>';
			});
			$("#jsfdept").html(option);
		},
		error: function(result) {
			console.log("getDZCXList()---error");
		}
	});
});


function getUniscIdCXList(){
	map.ywlx=$("#cj_code").val();
	map.model=$("input[name='model']:checked").val();
	map.year=$("#search_year").val();
	map.fsdepts=$("#fsfdept").val();
	map.jsdepts=$("#jsfdept").val();
	map.uniscid=$("#uniscid").val();
	$.ajax({
		url : "getUniscIdCXList",
		dataType: "html", 
		data:{
			models:JSON.stringify(map)
		},
		type:"post",
		success: function(result) {
			$("#dzpanelDiv").html(result);
		},
		error: function(result) {
			console.log("getUniscIdCXList()---error");
		}
	});
}



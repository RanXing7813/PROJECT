
//进入共享应用支撑
function getGXYYZC() {
	$("#searchbutton").attr("style","display: none;");
	$("#rightwrapid").attr("style","margin-top: -60px;");
	$.ajax({
		url : "getGxcx_gxyyzc",
		dataType : "text",
		data : {
		},
		type : "get",
		success : function(result) {
			$("#rightwrapid").html(result);
		},
		error : function(result) {
		}
	});
}

//进入需求开发概况
function getXQKFGK() {
	$("#searchbutton").attr("style","display: none;");
	$("#rightwrapid").attr("style","margin-top: -80px;");
	$.ajax({
		url : "getGxcx_xqkfgk",
		dataType : "text",
		data : {
		},
		type : "get",
		success : function(result) {
			$("#rightwrapid").html(result);
		},
		error : function(result) {
		}
	});
}

//进入目录填报统计
function getMLTBTJ() {
	$("#searchbutton").attr("style","display: none;");
	$("#rightwrapid").attr("style","margin-top: -80px;");
	$.ajax({
		url : "getGxcx_mltbtj",
		dataType : "text",
		data : {
		},
		type : "get",
		success : function(result) {
			$("#rightwrapid").html(result);
		},
		error : function(result) {
		}
	});
}

//进入资源使用统计
function getZYSYTJ() {
	$("#searchbutton").attr("style","display: none;");
	$("#rightwrapid").attr("style","margin-top: -80px;");
	$.ajax({
		url : "getGxcx_zysytj",
		dataType : "text",
		data : {
		},
		type : "get",
		success : function(result) {
			$("#rightwrapid").html(result);
		},
		error : function(result) {
		}
	});
}
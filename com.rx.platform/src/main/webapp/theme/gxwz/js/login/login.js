//登录窗口
var api = null;
//注意：parent 是 JS 自带的全局对象，可用于操作父页面
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
// 登录
function dengluAjax() {
	var nameStr = $("#username").val();
	var pasStr = $("#password").val();
	var codeStr = $("#vcode").val();
	// 显示基础信息
	$.ajax({
		url : "login",
		dataType : "json",
		data : {
			"username" : nameStr,
			"password" : pasStr,
			"vcode" : codeStr
		},
		type : "POST",
		success : function(result) {
			if (result.errorMessage != '') {
				$("#errorMessage_span").html(result.errorMessage);
				$("#verifycodea").click();
			} else if(result.changeIndex != '' && typeof (result.changeIndex) != "undefined"){
				// 关闭登录窗口 并且调整到 指定页面
				parent.layer.close(index);
				parent.location.href = result.changeIndex;
			} else {
				var method = $("#method").val();
				if (method == '' || method == null || typeof (method) == "undefined") {
					//frameElement.api.reload(frameElement.lhgDG);
					parent.layer.close(index);
					parent.location.href = "/";
				} else {
					// 关闭登录窗口 并且调整到 指定页面
					parent.layer.close(index);
					parent.location.href = method;
				}
				// frameElement.api.close();
			}
		},
		error : function(result) {
		}
	});
}

//退出
function logout(){
	/*$.dialog.confirm('您确定要退出吗？', function(){
		window.parent.location.href = "logout";
	}, function(){
	});*/
	$.dialog({
		id : 'testID',
		title:'确认退出',
	    content: '您确定要退出吗？',
	    //ok: function(){
	    //	window.parent.location.href = "logout";
	    //},
	    //cancelVal: '取消',
	    cancel: false ,/*为true等价于function(){}*/
		width : '200px',
		height : '80px',
		max : false,
		min : false,
		drag : false,
		resize : false,
		lock : true,
	    button: [
					{
					    name: '确定',
					    callback: function(){
					        window.parent.location.href='logout';
					    }
					},
			        {
			            name: '取消'
			        }
			    ]
	});
}

function forwardToAuth(){
	var redirectUrl = "";// 统一认证平台
	$.ajax({
        url : "getParameter.json",
        dataType: "json", 
        async: false,
        type:"POST",
        success: function(result) {
        	redirectUrl=result.address;
        },
        error: function(result) {
        }
	});
	
	// 对url进行编码，否则如果使用反向代理则会无法重定向
	//redirectUrl = escape(redirectUrl);
	// window.location.href=
	//正式地址19.15.247.56:7084
	// "http://19.16.10.66/am/oauth2/authorize?service=initService&response_type=code&client_id=lytest2&scope=all&client_secret=guangzhou111&redirect_uri="+redirectUrl;
	//window.parent.location.href = "http://19.224.1.196:7007/am/oauth2/authorize?service=initService&response_type=code&client_id=zwtest&scope=cn+uid+mail+idCardNumber+telephoneNumber+version+userIdCode+createTime+credenceState+orgInfo+extProperties&client_secret=111111&redirect_uri="+ redirectUrl;
	window.parent.location.href = redirectUrl;

}
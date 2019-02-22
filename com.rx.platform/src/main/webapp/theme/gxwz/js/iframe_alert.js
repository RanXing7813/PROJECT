function login(){
	layer.open({
		index:13,
		type: 2, //iframe
		title: "欢迎登录海口市政务信息共享网站", //标题
		shadeClose: true, //是否显示遮罩
		shade: 0.4, //透明度
		area: ['430px', '410px'], //窗口宽高
		//skin: "gd", //皮肤
		content: 'loginPage' //iframe的url
	});
}

function changemlxt(){
	var redirectUrl="http://19.224.1.177:8080/a/authLoginSingle";
	redirectUrl = escape(redirectUrl);
	window.open("http://19.15.247.56:7084/am/oauth2/authorize?service=initService&response_type=code&client_id=gdbs_zw_gxml&scope=cn+uid+mail+idCardNumber+telephoneNumber+version+userIdCode+createTime+credenceState+orgInfo+extProperties&client_secret=123qwe&redirect_uri="+ redirectUrl,"top");
}
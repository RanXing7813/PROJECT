$(function() {
	var ue = UE.getEditor('editor', {
		toolbars : [ [
		// 'anchor', // 锚点
		'undo', // 撤销
		'redo', // 重做
		'bold', // 加粗
		// 'snapscreen', // 截图
		'italic', // 斜体
		'underline', // 下划线
		// 'strikethrough', // 删除线
		// 'subscript', // 下标
		// 'fontborder', // 字符边框
		// 'superscript', // 上标
		'formatmatch', // 格式刷
		// 'source', // 源代码
		// 'blockquote', // 引用
		// 'pasteplain', // 纯文本粘贴模式
		'selectall', // 全选
		// 'print', // 打印
		'horizontal', // 分隔线
		'removeformat', // 清除格式
		'time', // 时间
		'date', // 日期
		// 'unlink', // 取消链接
		// 'insertrow', // 前插入行
		// 'insertcol', // 前插入列
		// 'mergeright', // 右合并单元格
		// 'mergedown', // 下合并单元格
		// 'deleterow', // 删除行
		// 'deletecol', // 删除列
		// 'splittorows', // 拆分成行
		// 'splittocols', // 拆分成列
		// /'splittocells', // 完全拆分单元格
		// 'deletecaption', // 删除表格标题
		'inserttitle', // 插入标题
		// 'mergecells', // 合并多个单元格
		'deletetable', // 删除表格
		'cleardoc', // 清空文档
		// 'insertparagraphbeforetable', // "表格前插入行"
		// 'insertcode', // 代码语言
		'fontfamily', // 字体
		'fontsize', // 字号
		'paragraph', // 段落格式
		// 'simpleupload', // 单图上传
		// 'insertimage', // 多图上传
		// 'edittable', // 表格属性
		// 'edittd', // 单元格属性
		// 'link', // 超链接
		// 'emotion', // 表情
		// 'spechars', // 特殊字符
		// 'searchreplace', // 查询替换
		// 'map', // Baidu地图
		// 'gmap', // Google地图
		// 'insertvideo', // 视频
		'justifyleft', // 居左对齐
		'justifyright', // 居右对齐
		'justifycenter', // 居中对齐
		'justifyjustify', // 两端对齐
		'forecolor', // 字体颜色
		// 'backcolor', // 背景色
		'insertorderedlist', // 有序列表
		'insertunorderedlist', // 无序列表
		// 'fullscreen', // 全屏
		// 'directionalityltr', // 从左向右输入
		// 'directionalityrtl', // 从右向左输入
		'indent', // 首行缩进
		'rowspacingtop', // 段前距
		'rowspacingbottom', // 段后距
		// 'pagebreak', // 分页
		// 'insertframe', // 插入Iframe
		// 'imagenone', // 默认
		// 'imageleft', // 左浮动
		// 'imageright', // 右浮动
		// 'attachment', // 附件
		// 'imagecenter', // 居中
		// 'wordimage', // 图片转存
		'lineheight', // 行间距
		'edittip ', // 编辑提示
		// 'customstyle', // 自定义标题
		'autotypeset', // 自动排版
		// 'webapp', // 百度应用
		// 'touppercase', // 字母大写
		// 'tolowercase', // 字母小写
		// 'background', // 背景
		// 'template', // 模板
		// 'scrawl', // 涂鸦
		// 'music', // 音乐
		'inserttable', // 插入表格
		// 'drafts', // 从草稿箱加载
		// 'charts', // 图表
		// 'preview', // 预览
		'help', // 帮助
		] ]
	});

	// 获得编辑器的内容
	function getContent() {
		return UE.getEditor('editor').getContent();
	}

	function setContent(content) {
		UE.getEditor('editor').setContent(content, true);
	}

	UE.getEditor('editor').addListener("ready", function() {
		// editor准备好之后才可以使用
		// 设置编辑器的内容
		var content = $("#content").val();
		setContent(content);

	});

	// 显示文件列表
	function showFileList(files) {

		var html = "";
		for (var i = 0; i < files.length; i++) {
			html += '<tr><td>' + files[i].fname + '</td><td><a class="layui-btn layui-btn-danger delf" alt="' + files[i].fid + '">删除</a></td></tr>';
		}
		$("#fileList").append(html);

		top.layer.msg("附件上传成功！", {
			icon : 6,
			time : 1000
		});

		// 删除文件 重新绑定事件
		$(".delf").click(function() {
			var fid = $(this).attr("alt");

			$(this).parent().parent().remove(); // 移除行

			$.ajax({
				url : "/file/delfilebyfid",
				dataType : "json",
				data : {
					fid : fid
				},
				type : "post",
				success : function(result) {
					if (result.code == '0') {
						top.layer.msg("附件删除成功！", {
							icon : 6,
							time : 1000
						});
					}
				},
				error : function(result) {
				}
			});

		});

	}

	// 处理 radio
	var infotype = $(":radio[name='infolinktype']:checked").val();
	initUrl(infotype);
	function initUrl(infotype) {
		if (infotype == '1') {
			$("#urlDiv").css("display", "block");
			$("input[name='infolinkurl']").attr("lay-verify", "required|url");
		} else {
			$("#urlDiv").css("display", "none");
			$("input[name='infolinkurl']").removeAttr("lay-verify");

		}
	}

	layui.config({
		base : "js/"
	}).use([ 'form', 'layer', 'jquery', 'layedit', 'laydate', 'element' ], function() {
		var form = layui.form, layer = parent.layer === undefined ? layui.layer : parent.layer, laypage = layui.laypage, layedit = layui.layedit, laydate = layui.laydate, $ = layui.jquery;
		var element = layui.element;

		form.on("submit(addNews)", function(data) {
			$("#content").val(getContent());
			$(".layui-form").submit();
		});
		form.on("submit(publish)", function(data) {
			$("#infolinkstate").val("1");
			$("#content").val(getContent());
			$(".layui-form").submit();
		});

		form.on('radio(infolinktype)', function(data) {
			initUrl(data.value);
		});

		// 文件上传
		$(".upolad").click(function() {
			var infolinkid = $("#infolinkid").val();
			uploafile(infolinkid, showFileList);
		});

		// 删除文件
		$(".delf").click(function() {
			var fid = $(this).attr("alt");

			$(this).parent().parent().remove(); // 移除行

			$.ajax({
				url : "/file/delfilebyfid",
				dataType : "json",
				data : {
					fid : fid
				},
				type : "post",
				success : function(result) {
					if (result.code == '0') {
						top.layer.msg("附件删除成功！", {
							icon : 6,
							time : 1000
						});
					}
				},
				error : function(result) {
				}
			});

		});
		// 关闭
		$(".closeCurPage").click(function() {
			layer.closeAll("iframe");
		});

	});
});

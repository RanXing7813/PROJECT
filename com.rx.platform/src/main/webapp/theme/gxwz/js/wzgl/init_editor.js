		$("#container").val(contents);
		
		//var ue = UE.getEditor('container');
		editor = UE.getEditor('container', {
			//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  
			toolbars : [ [ 'fullscreen', 'fontfamily', //字体
			//'attachment', //附件
			'fontsize', //字号
			'paragraph', //段落格式
			'forecolor', //字体颜色
			'undo', //撤销
			'bold', //加粗
			'italic', //斜体
			'underline', //下划线
			'subscript', //下标
			'superscript', //上标
			'formatmatch', //格式刷
			'blockquote', //引用
			'pasteplain', //纯文本粘贴模式
			'selectall', //全选
			'horizontal', //分隔线
			'removeformat', //清除格式
			//'unlink', //取消链接
			'inserttitle', //插入标题
			 'simpleupload', //单图上传
			 'insertimage', //多图上传
			//'link', //超链接
			//'spechars', //特殊字符
			//'searchreplace', //查询替换
			'justifyleft', //居左对齐
			'justifyright', //居右对齐
			'justifycenter', //居中对齐
			'justifyjustify', //两端对齐
			'insertorderedlist', //有序列表
			'insertunorderedlist', //无序列表
			'indent', //首行缩进
			'rowspacingtop', //段前距
			'rowspacingbottom', //段后距
			'lineheight', //行间距
			'edittip ' //编辑提示
			] ],
			//focus时自动清空初始化时的内容  
			autoClearinitialContent : false,
			//关闭字数统计  
			wordCount : false,
			//关闭elementPath  
			elementPathEnabled : false,
			//默认的编辑区域高度  
			initialFrameHeight : 300
		//更多其他参数，请参考ueditor.config.js中的配置项  
		});
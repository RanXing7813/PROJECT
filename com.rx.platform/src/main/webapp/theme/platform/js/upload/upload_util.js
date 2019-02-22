
$(function(){

	layui.use('upload', function(){
		  var $ = layui.jquery,upload = layui.upload;
		  
		  // 多文件列表示例
		  var demoListView = $('#demoList')
		  ,uploadListIns = upload.render({
		    elem: '#testList' // 指向容器选择器 文件选择按钮绑定
		    ,data: {fkid:$("#fkid").val()} // 可选项。额外的参数，如：{id: 123, abc: 'xxx'}
		    ,url: '/file/mult_upload'  // 服务端上传接口
		    ,accept: 'file'  // 指定允许上传时校验的文件类型，可选值有：images（图片）、file（所有文件）、video（视频）、audio（音频）
		    ,acceptMime: '' // 规定打开文件选择框时，筛选出的文件类型，值为用逗号隔开的 MIME 类型列表
		    ,exts:$("#exts").val() // 允许上传的文件后缀。
		    ,multiple: true  // 是否允许多文件上传。设置 true即可开启。不支持ie8/9
		    ,auto: true  // 是否选完文件后自动上传
		    ,size:$("#maxUploadSize").val()  // 设置文件最大可允许上传的大小，单位 KB。不支持ie8/9
		    ,number:$("#file_number").val()  // 设置同时可上传的文件数量
		    ,bindAction: '#testListAction' // 指向一个按钮触发上传
		    ,before:function(input){// 文件提交上传前的回调
		    	
		    }
		    ,choose: function(obj){   
		      var files = this.files = obj.pushFile(); // 将每次选择的文件追加到文件队列
		      // 读取本地文件
		      obj.preview(function(index, file, result){
		        var tr = $(['<tr id="upload-'+ index +'">'
		          ,'<td>'+ file.name +'</td>'
		          ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
		          ,'<td>等待上传</td>'
		          ,'<td>'
		            // ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
		            ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">移除</button>'
		          ,'</td>'
		        ,'</tr>'].join(''));
		        
		        // 单个重传
		        tr.find('.demo-reload').on('click', function(){
		          obj.upload(index, file);
		        });
		        
		        // 移除
		        tr.find('.demo-delete').on('click', function(){
		          delete files[index]; // 删除对应的文件
		          tr.remove();
		          uploadListIns.config.elem.next()[0].value = ''; // 清空 input file 值，以免删除后出现同名文件不可选
		        });
		        
		        demoListView.append(tr);
		      });
		    }
		    ,done: function(res, index, upload){// 执行上传请求后的回调
		    	// 返回三个参数，分别为：res（服务端响应信息）、index（当前文件的索引）、upload（重新上传的方法，一般在文件上传失败后使用）
		    	// res为标准的 JSON 格式
		      if(res.code == 0){ // 上传成功
		        var tr = demoListView.find('tr#upload-'+ index)
		        ,tds = tr.children();
		        // $(tr).attr("title",res.fid);
		        tds.eq(2).html('<span style="color: #5FB878;">'+res.msg+'</span>');
		        tds.eq(3).html('<input type="hidden" name="fids" value="'+res.fid+'" dir="'+res.fname+'"/><button class="layui-btn layui-btn-mini layui-btn-danger f-delete_'+res.fid+'" dir="'+res.fid+'">取消上传</button>'); // 清空操作

		        // 删除对应的文件
		        $('.f-delete_'+res.fid).click(function() {  
		        	
		        	tr.remove(); // 移除行
		        	
		        	var fid = $(this).attr("dir");
		        	$.ajax({
						url : "/file/delfilebyfid",
						dataType : "json",
						data : {fid:fid},
						type : "post",
						success : function(result) {
							if (result.code == '0') {
								// tr.remove();
							}
						},
						error : function(result) {
						}
					});
		        	
		        }); 
		        
		        return delete this.files[index]; // 删除文件队列已经上传成功的文件
		      }
		      this.error(index, upload,res);
		    }
		    ,allDone: function(obj){ // 当文件全部被提交后，才触发
		        // console.log(obj.total); // 得到总文件数
		       // console.log(obj.successful); // 请求成功的文件数
		       // console.log(obj.aborted); // 请求失败的文件数
		      }
		    ,error: function(index, upload,res){// 执行上传请求出现异常的回调 （一般为网络异常、URL 404等）
		    	// 返回两个参数，分别为：index（当前文件的索引）、upload（重新上传的方法）
		      var tr = demoListView.find('tr#upload-'+ index)
		      ,tds = tr.children();
		      tds.eq(2).html('<span style="color: #FF5722;">'+res.info+'</span>');
		      // tds.eq(3).find('.demo-reload').removeClass('layui-hide'); // 显示重传
		    }
		  });
		  
		});
		
});

function callbackdata(){
	var fids = $("input[name='fids']");
	var uf =new Array()
	for(var i=0;i<fids.length;i++){
		
		var file =new Object();
		
		file.fid=$(fids[i]).val();
		file.fname=$(fids[i]).attr("dir")
		
		uf[i]=file;
	}
	return uf;
}

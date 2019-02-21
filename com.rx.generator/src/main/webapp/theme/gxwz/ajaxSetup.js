	
	 /**
	  * 设置未来(全局)的AJAX请求默认选项
	  * 主要设置了AJAX请求遇到Session过期的情况
	  */
	 $.ajaxSetup({
	    // type: 'POST',
	     complete: function(xhr,status) {
//	    	 console.log(xhr);
//	         console.log(status);
	         var errorParameCode = xhr.responseText;
	        var top_ = getTopWinow();
	         if(errorParameCode == 'errorParameCode') {
	             alert('参数含有特殊字符.');
//	             var yes = confirm('参数含有特殊字符.');
//	             if (yes) {
//	            	 top_.location.href = '';           
//	             }
	         }
	     }
	 });

	 /**
	  * 在页面中任何嵌套层次的窗口中获取顶层窗口
	  * @return 当前页面的顶层窗口对象
	  */
	 function getTopWinow(){
	     var p = window;
	     while(p != p.parent){
	         p = p.parent;
	     }
	     return p;
	 }
	
	

	function go(pages){
			map.page=pages;
			   $.ajax({
			          url : "getCms_list.json",
			          dataType: "json", 
			          data:{
			        	  models : JSON.stringify(map)
			          },
			          type:"POST",
			          success: function(result) {
		      	 		$("#grid").html('');
		      	 		//alert(result.list);
		      	 			var list =   '';
		      	 		$.each(result.list, function(n,value){  
			  	 		    	var	ids = value.infolink_id;
								  list +=   
									'<li><a href="#" id="'+ids+'"  onclick="codeEdit(this)" class="f-toe">'+value.infolink_title+'</a>'+
									'<span class="datetime">'+value.created_time+'</span></li>';
			  	 				$("#grid").append(list);
		      	 		});
		      	 		$("#grid").html(list);
		      	 		
		      	 		
	      				//加载分页信息
	      				//分页信息
	      				var z = result.total%map.pageSize;
	      				totalCount = result.total;
	      				$("#td_toalcount").html("共"+totalCount+"条"+map.name);
	      				var pageNum;
	      				if(z==0){
	      					pageNum=result.total/map.pageSize;
	      				}else{
	      					pageNum=Math.floor(result.total/map.pageSize)+1;
	      				}
	      				map.pagenum=pageNum;
	      				
	      				var pageoption=PageButten(pages,pageNum);
	      				$("#listpage").html(pageoption);
			          },
			          error: function(result) {
			          }
			        });
		}	
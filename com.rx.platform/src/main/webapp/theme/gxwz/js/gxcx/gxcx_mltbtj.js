$(function() {
	getDeptCatalog("sj");//目录提供情况
	getDeptCatalog("ds");//目录提供情况
	getShareInfo("sj");//目录共享类型统计
	getShareInfo("ds");//目录共享类型统计
	getCatalogVisit();//日目录访问量统计
});

//目录提供情况
function getDeptCatalog(dept){
	map.page=1;
	map.pagesize=5;
	map.dept=dept;
	$.ajax({
		url : "getDeptCatalog",
		dataType: "json", 
		data:{
			models:JSON.stringify(map)
		},
		type:"post",
		success: function(result) {
			var option='<tr>'
				+'<th>序号</th>'
				+'<th>名称</th>'
				+'<th class="f-tar">提供目录数量(条)</th>'
				+'</tr>';
			$.each(result.list,function(n,value){
				var name = value.PROVIDEBMNAME;
				var nametitle = name;
				if(name.length>8){
					name=name.substring(0,8)+'...';
				}
				option+='<tr>'
					+'<td>'+(n+1)+'</td>'
					+'<td title="'+nametitle+'">'+name+'</td>'
					+'<td class="f-tar">'+value.CNT+'</td>'
					+'</tr>';
			});
			$("#"+dept+"cataloglist").html(option);
		},
		error: function(result) {
			console.log(result);
			console.log("getDeptCatalog()---error");
		}
	});
}

//目录提供情况 更多
function getDeptCatalogMore(dept){
	layer.open({
		type: 2, //iframe
		title: "目录提供情况", //标题
		shadeClose: true, //是否显示遮罩
		shade: 0.4, //透明度
		area: ['1240px', '760px'], //窗口宽高
		skin: "gd", //皮肤
		content: 'getDeptCatalogMore?pageSize=10&page=1&dept='+dept//iframe的url
	});
}

//目录共享类型统计
function getShareInfo(dept) {
	$.ajax({
		url : "getShareInfo",
		dataType : "json",
		data : {dept:dept},
		type : "post",
		success : function(result) {
			show_chart_share(dept,result.ycnt,result.wcnt,result.bycnt);
		},
		error : function(result) {
		}
	});
}
//接口注册与调用情况图
function show_chart_share(dept,ycnt,wcnt,bycnt) {
	// 基于准备好的dom，初始化echarts实例
	var char=dept+"divChart";
	var myChart = echarts.init(document.getElementById(char));
	// 指定图表的配置项和数据
	var option = {
		backgroundColor : '#f8f8f8',
		color : [ '#638ca6', '#53bf8a', '#00acfb', '#faf14e' ],
		title : {
			text : '',
			x : 'center',
			y : 'left'
		},
		grid : {
			x : 80,
			y : 25,
			x2 : 10,
			y2 : 85,
			borderWidth : 1
		},
		tooltip : {
			trigger : 'item',
		//formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			data : [ '有条件共享', '无条件共享', '不予共享']
		},
		toolbox : {
			show : false
		},
		calculable : true,
		series : 
        {
            name:'',
            type:'pie',
            radius : ['50%', '70%'],
            itemStyle : {
                normal : {
                    label : {
                        show : false
                    },
                    labelLine : {
                        show : false
                    }
                },
                emphasis : {
                    label : {
                        show : true,
                        position : 'center',
                        textStyle : {
                            fontSize : '30',
                            fontWeight : 'bold'
                        }
                    }
                }
            },
            data:[{value:ycnt, name:'有条件共享'},{value:wcnt, name:'无条件共享'},{value:bycnt, name:'不予共享'}]
        }
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

function getCatalogVisit() {
	var btime = $("#fwlbegin").val();
	var etime = $("#fwlend").val();

	$.ajax({
		url : "getCatalogVisit",
		dataType : "json",
		data : {
			btime : btime,
			etime : etime
		},
		type : "post",
		success : function(result) {
			show_chart(eval(result.xdata), 
					 eval(result.visitdata),result.start);
		},
		error : function(result) {
		}
	});
}

function show_chart(xdata,visitdata,start) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('fwldivChart'));
	// 指定图表的配置项和数据
	var option = {
		backgroundColor : '#edf5fc',
		color : [ '#00EEA7', '#638ca6', '#e8b71a'],
		title : {
			text : '',
			x : 'center',
			y : 'left'
		},
		grid : {
			x : 80,
			y : 25,
			x2 : 10,
			y2 : 85,
			borderWidth : 1
		},
		tooltip : {
			trigger : 'item',
		//formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		dataZoom : {
			show : true,
			start : start
		},
		legend : {
			data : [ '访问量' ]
		},
		toolbox : {
			show : false
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			boundaryGap : false,
			axisLabel : {
				interval : 0,
				rotate : 30
			},
			data : xdata
		} ],
		yAxis : [ {
			type : 'value',
			axisLabel : {
				formatter : '{value}'
			}
		} ],
		series : [ {
			name : '访问量',
			type : 'line',
			data : visitdata
		}]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

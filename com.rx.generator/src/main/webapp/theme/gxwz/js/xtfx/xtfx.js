$(function() {
	serviceInfo();//接口注册与调用情况
	
	infoclassStatics();//目录数与挂接数的对比
	
	sharedStatics()//部门发布资源和申请资源对比
});

function serviceInfo() {

	$.ajax({
		url : "serviceInfo",
		dataType : "json",
		data : {},
		type : "post",
		success : function(result) {
			show_chart_services(result.bwcount,result.sjcount);
		},
		error : function(result) {
		}
	});
}

function show_chart_services(bwcount,sjcount) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('divChart'));
	// 指定图表的配置项和数据
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
			data : [ '国家部委接口', '市级接口']
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
            data:[{value:bwcount, name:'国家部委接口'},{value:sjcount, name:'市级接口'}]
        }
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

function infoclassStatics() {
	$.ajax({
		url : "infoclassStatics",
		dataType : "json",
		data : {},
		type : "post",
		success : function(result) {
			
			var xdata =new Array();
			var infocount =new Array();
			var gjcount =new Array();
			
			for (var i=0;i<result.length;i++){
				xdata[i]=result[i].DEPTNAME;
				infocount[i]=result[i].INFOCOUNT;
				gjcount[i]=result[i].GJCOUNT;
			}
			show_chart_infoclass(xdata,infocount,gjcount);
		},
		error : function(result) {
		}
	});
}


function show_chart_infoclass(xdata,infocount,gjcount) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('divChart_class'));
	// 指定图表的配置项和数据
	// 指定图表的配置项和数据
	
	var option = {
		backgroundColor : '#f8f8f8',
		color : [ '#638ca6','#00EEA7', '#e8b71a'],
		title : {
			text : '',
			x : 'center',
			y : 'left'
		},
		grid : {
			x : 80,
			y : 35,
			x2 : 40,
			y2 : 85,
			borderWidth : 1
		},
		tooltip : {
			trigger : 'item',
		//formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			data : [ '目录数', '挂接数']
		},
		toolbox : {
			show : false
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			axisLabel : {
				interval : 0,
				rotate : 30,
				formatter:function(p){
					//console.log(p);
					if(p.length>10){
						return p.substring(0,10)+'...';
					}else{
						return p;
					}
				},
				textStyle:{
					color:'#010c15',
					fontSize:14,
				}
			},
			data : xdata
		} ],
		yAxis : [ {
			type : 'value',
			axisLabel : {
				formatter : '{value}',
				textStyle:{
					color:'#010c15',
					fontSize:14,
				}
			}
		} ],
		series : [ {
			name : '目录数',
			type : 'bar',
			stack: 'one',
			barWidth: 30,//固定柱子宽度
			data : infocount
		}, {
			name : '挂接数',
			type : 'bar',
			stack: 'one',
			barWidth: 30,//固定柱子宽度
			data : gjcount
		}
		]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}
function sharedStatics() {
	$.ajax({
		url : "sharedStatics",
		dataType : "json",
		data : {},
		type : "post",
		success : function(result) {
			
			var ydata =new Array();
			var tg =new Array();
			var js =new Array();
			
			for (var i=0;i<result.length;i++){
				ydata[i]=result[i].REMARK;
				tg[i]=0-result[i].TG;
				js[i]=result[i].JS;
			}
			show_chart_shared(ydata,tg,js);
		},
		error : function(result) {
		}
	});
}


function show_chart_shared(ydata,tg,js) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('divChart_shared'));
	// 指定图表的配置项和数据
	// 指定图表的配置项和数据
	
	var option = {
			backgroundColor : '#f8f8f8',
			color : [ '#638ca6','#00EEA7', '#e8b71a'],
			title : {
				text : '',
				x : 'center',
				y : 'left'
			},
			grid : {
				x : 80,
				y : 45,
				x2 : 40,
				y2 : 35,
				borderWidth : 1
			},
			tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        },
		        formatter:function(p){
		        	return p[0].name+"</br>"+p[0].seriesName+":"+(0-p[0].value)+"</br>"+p[1].seriesName+":"+p[1].value;
            	}
		    },
			legend : {
				data : [ '提供', '申请']
			},
			toolbox : {
				show : false
			},
			calculable : true,
			xAxis : [ {
				type : 'value',
				axisLabel : {
					textStyle:{
						color:'#010c15',
						fontSize:14,
					}
				}
				
			} ],
			yAxis : [ {
				type : 'category',
				axisLabel : {
					formatter : '{value}',
					textStyle:{
						color:'#010c15',
						fontSize:14,
					}
				},
				data : ydata
			} ],
			series : [ {
				name : '提供',
				type : 'bar',
				stack: '信息类',
				barWidth : 10,
				itemStyle: {
					normal: {
		                label : {
		                	show: true, 
		                	position: 'left',
		                	formatter:function(p){
		                		return 0-p.value
		                	}
		                }
	               }
	            },
				data : tg
			}, {
				name : '申请',
				type : 'bar',
				stack: '信息类',
				barWidth : 10,
				 itemStyle: {
					 normal: {
		                label : {
		                	show: true, 
		                	position: 'right',
		                	formatter:function(p){
		                		if(p.value==0){
		                			return '';
		                		}else{
		                			return p.value;
		                		}
		                	}
		                }
		              }
		           },
				data : js
			}
			]
	};
	
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}
//资源编目与挂接情况 更多
function infoclassStaticsMore(){
	layer.open({
		type: 2, //iframe
		title: "资源编目与挂接情况", //标题
		shadeClose: true, //是否显示遮罩
		shade: 0.4, //透明度
		area: ['1240px', '760px'], //窗口宽高
		skin: "gd", //皮肤
		content: 'infoclassStaticsList?pageSize=10&page=1'//iframe的url
	});
}
//接口注册与调用情况 更多
function listService(){
	layer.open({
		type: 2, //iframe
		title: "接口注册与调用情况", //标题
		shadeClose: true, //是否显示遮罩
		shade: 0.4, //透明度
		area: ['1240px', '760px'], //窗口宽高
		skin: "gd", //皮肤
		content: 'listService?pageSize=10&page=1'//iframe的url
	});
}
//接口注册与调用情况 更多
	function listInfoClassMore(){
		layer.open({
			type: 2, //iframe
			title: "共享申请与提供情况", //标题
			shadeClose: true, //是否显示遮罩
			shade: 0.4, //透明度
			area: ['1240px', '760px'], //窗口宽高
			skin: "gd", //皮肤
			content: 'listSharedMore?pageSize=10&page=1'//iframe的url
	});
}
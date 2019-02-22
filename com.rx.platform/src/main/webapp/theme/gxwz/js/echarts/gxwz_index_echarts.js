
// 资源编目 map
var mapZybm = {};
/**
 * 命名规则 : 部门目录_分布/统计_省级/地市_echart图/list
 * bmml_fb_sj_echart   
 * bmml_fb_sj_list	   
 * bmml_tj_sj_echart
 * bmml_tj_sj_list
 * @returns
 */
$(function() {
	initZybm();	  //资源目录   分布数据
	initZybm_TJ();//资源目录   统计数据
});

function initZybm() {
	$.ajax({
		url : "getCatalogDistribution",
		dataType : "json",
		data : {
			models : JSON.stringify(mapZybm)
		},
		type : "post",
		success : function(result) {
			//目录分布   //省级部门   //目录情况统计图
			var dataAxis_fb_sj_echart = result.dataAxis_fb_sj_echart;
			var     data_fb_sj_echart = result.data_fb_sj_echart;
			var     yMax_fb_sj_echart = result.yMax_fb_sj_echart;
			var       bmml_fb_sj_list = result.bmml_fb_sj_list;

			var dataAxis_fb_ds_echart = result.dataAxis_fb_ds_echart;
			var     data_fb_ds_echart = result.data_fb_ds_echart;
			var     yMax_fb_ds_echart = result.yMax_fb_ds_echart;
			var       bmml_fb_ds_list = result.bmml_fb_ds_list;

			//目录分布   //省级部门   //目录情况统计图
			getBmfb_Bmml_echart("bmml_fb_sj_echart", dataAxis_fb_sj_echart, data_fb_sj_echart, yMax_fb_sj_echart);
			getBmfb_Bmml_list("bmml_fb_sj_list", eval(bmml_fb_sj_list));

			//目录分布   //地市部门   //目录情况统计图
			getBmfb_Bmml_echart("bmml_fb_ds_echart", dataAxis_fb_ds_echart, data_fb_ds_echart, yMax_fb_ds_echart);
			getBmfb_Bmml_list("bmml_fb_ds_list", eval(bmml_fb_ds_list));

		},
		error : function(result) {
		}

	});
}

function initZybm_TJ() {
	$.ajax({
		url : "getCatalogDistribution_TJ",
		dataType : "json",
		data : {
			models : JSON.stringify(mapZybm)
		},
		type : "post",
		success : function(result) {
			//目录统计   //省级部门   //目录情况统计图
			var  dataAxis_tj_sj_wgx_echart = result.dataAxis_tj_sj_wgx_echart;
			var      data_tj_sj_wgx_echart = result.data_tj_sj_wgx_echart;
			var  dataAxis_tj_sj_ygx_echart = result.dataAxis_tj_sj_ygx_echart;
			var      data_tj_sj_ygx_echart = result.data_tj_sj_ygx_echart;
			var dataAxis_tj_sj_bygx_echart = result.dataAxis_tj_sj_bygx_echart;
			var     data_tj_sj_bygx_echart = result.data_tj_sj_bygx_echart;
			var     			   wgxlist = result.wgxlist;
			
			var  dataAxis_tj_ds_wgx_echart = result.dataAxis_tj_ds_wgx_echart;
			var      data_tj_ds_wgx_echart = result.data_tj_ds_wgx_echart;
			var  dataAxis_tj_ds_ygx_echart = result.dataAxis_tj_ds_ygx_echart;
			var      data_tj_ds_ygx_echart = result.data_tj_ds_ygx_echart;
			var dataAxis_tj_ds_bygx_echart = result.dataAxis_tj_ds_bygx_echart;
			var     data_tj_ds_bygx_echart = result.data_tj_ds_bygx_echart;
			var     			  wgxlist2 = result.wgxlist2;
			
			//目录统计   //省级部门   //目录情况统计图
			getBmfb_Bmml_echart_TJ("bmml_tj_sj_echart", dataAxis_tj_sj_wgx_echart, data_tj_sj_wgx_echart, dataAxis_tj_sj_ygx_echart, data_tj_sj_ygx_echart, dataAxis_tj_sj_bygx_echart, data_tj_sj_bygx_echart);
			getBmfb_Bmml_list_TJ("bmml_tj_sj_list", eval(wgxlist));

			//目录统计   //地市部门   //目录情况统计图
			getBmfb_Bmml_echart_TJ("bmml_tj_ds_echart", dataAxis_tj_ds_wgx_echart, data_tj_ds_wgx_echart,dataAxis_tj_ds_ygx_echart, data_tj_ds_ygx_echart, dataAxis_tj_ds_bygx_echart, data_tj_ds_bygx_echart);
			getBmfb_Bmml_list_TJ("bmml_tj_ds_list", eval(wgxlist2));

		},
		error : function(result) {
		}

	});
}

/**
 * 
 * @param divId  		echarts图像div的id值
 * @param dataAxis		X轴线的部门名称
 * @param data			series的柱状图数据值对应X轴线
 * @param yMax			Y轴线的最大值,必须
 * @returns
 */
function getBmfb_Bmml_echart(divId, dataAxis, data, yMax) {

	var bmml_fb_sj_echart_Mychart = echarts.init(
			document.getElementById(divId), divId, {
				width : 785,
				height : 300
			});

	var dataAxis = eval(dataAxis); 
	var data = eval(data); 
	var yMax = parseInt(yMax); 
	var dataShadow = [];

	for (var i = 0; i < data.length; i++) {
		dataShadow.push(yMax);
	}

	option = {
		//	    title: {
		//	        text: '部门目录情况统计图',
		//	    },
		tooltip : {},
		grid : {
			show : true,
			zlevel : 0,
			z : 2,
			left : '5%',
			top : 33,
			right : '5%',
		},
		xAxis : {
			data : dataAxis,
			axisTick : {
				show : true,
				interval : 0,
			},
			axisLine : {
				show : false
			},
			axisLabel : {
				show : true,
				showMinLabel : true,
				interval : 0,
				formatter : function(value) {
					if (value.length > 6) {
						value = value.substring(0, 6) + '...';
					}
					return value;
				},
				rotate : 25,
			}
		},
		yAxis : {
			name : '目录数量(条)',
			nameGap : 15,
			nameTextStyle : {
				fontSize : 14,
				padding : [ 0, 5, -11, 0 ],
			},
			axisTick : {
				show : false
			},
			axisLine : {
				show : false
			},
		},
		series : [ {
			type : 'bar',
			tooltip:{
	                show: false,
	            },
			barMaxWidth : 18,
			itemStyle : {
				normal : {
					color : '#d6eaff'
				},
				emphasis : {
					color : 'rgba(0,0,0,0.05)'
				}
			},
			barGap : '-100%',
			barCategoryGap : '40%',
			data : dataShadow,
			animation : false,

		}, {
			type : 'bar',
			barMaxWidth : 18,
			itemStyle : {
				normal : {
					color : '#4fa4fa',
				},
			},
			data : data
		} ]
	};//end option

	bmml_fb_sj_echart_Mychart.setOption(option);
}

/**
 * 
 * @param tableId  要遍历数据的table的id
 * @param list	   需要遍历的数据
 * @returns
 */
function getBmfb_Bmml_list(tableId, list) {
	var grid = '<tr><th width="20%">序号</th><th  width="50%">名称</th><th width="30%"> 数量(条)</th></tr>';
	
	$.each(list, function(n, value) {
		if (n >= 6)
			return;
		var name_ = value.NAME_;
		var count_ = toThousands(value.COUNT_);
		grid += '<tr> <td>' + (n + 1) + '</td><td title="' + name_ + '">'
				+ name_ + '</td><td title="' + count_ + '">' + count_
				+ '</td> </tr>'
	});

	$("#" + tableId).html(grid);
}

/**
 * @param obj  参数是个对象 {type:命名规则: 分布/统计_省级/地市}
 * @param obj.type  fb_sj | fb_ds | tj_sj | tj_ds    命名规则: 分布/统计_省级/地市    
 * @returns
 */
function getBmfb_Bmml_Morelist(obj) {
	layer.open({
		type : 2, //iframe
		title : "部门目录情况全部", //标题
		shadeClose : true, //是否显示遮罩
		shade : 0.4, //透明度
		area : [ '1270px', '800px' ], //窗口宽高
		skin : "gd", //皮肤
		content : 'getCatalogDistribution_ifream?type=' + obj.type //iframe的url
	});
}

/**
 * 堆叠图
 * @param divId  		echarts图像div的id值
 * @param dataAxis		X轴线的部门名称
 * @param data			series的柱状图数据值对应X轴线
 * @param yMax			Y轴线的最大值,必须
 * @returns
 */
function getBmfb_Bmml_echart_TJ(divId, wgxAxis, wgxData, ygxAxis, ygxData, bygxAxis, bygxData  ) {

	var bmml_fb_sj_echart_Mychart = echarts.init(document
			.getElementById(divId), divId, {
		width : 785,
		height : 300
	});

	option = {

		legend : {
			data : [ '有条件共享', '无条件共享', '不予共享' ]
		},
		tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
		grid : {
			show : true,
			zlevel : 0,
			z : 2,
			left : '5%',
			top : 33,
			right : '5%',
		},
		xAxis : {
			type : 'category',
			data : eval(wgxAxis),
			axisTick : {
				show : false
			},
			axisLine : {
				show : false
			},
			axisLabel : {
				show : true,
				showMinLabel : true,
				interval : 0,
				formatter : function(value) {
					if (value.length > 6) {
						value = value.substring(0, 6) + '...';
					}
					return value;
				},
				rotate : 25,
			}

		},
		yAxis : {
			type : 'value',
			name : '目录数量(条)',
			nameGap : 15,
			nameTextStyle : {
				fontSize : 14,
				padding : [ 0, 5, -11, 0 ],
			},
			axisTick : {
				show : false
			},
			axisLine : {
				show : false
			},

		},
		series : [

		{
			type : 'bar',
			name : '有条件共享',
			stack : '总量',
			barMaxWidth : 18,
			
			barGap : '-100%',
			barCategoryGap : '40%',
			animation : false,
			data : eval(ygxData),
		}, {
			type : 'bar',
			name : '无条件共享',
			stack : '总量',
			barMaxWidth : 18,
			itemStyle : {
				normal : {
					color : '#4fa4fa'
				},
			},
			data : eval(wgxData)
		}, {
			type : 'bar',
			name : '不予共享',
			stack : '总量',
			barMaxWidth : 18,
			itemStyle : {
				normal : {
					color : '#d6eaff'
				},
				emphasis : {
					color : 'rgba(0,0,0,0.05)'
				}
			},
			data : eval(bygxData)
		}, ]
	};//end option

	bmml_fb_sj_echart_Mychart.setOption(option);
}

/**
 * 
 * @param tableId  要遍历数据的table的id
 * @param list	   需要遍历的数据
 * @returns
 */
function getBmfb_Bmml_list_TJ(tableId, list) {
	var grid = '<tr><th width="20%">序号</th><th  width="50%">名称</th><th width="30%"> 数量(条)</th></tr>';
	$.each(list, function(n, value) {
		if (n >= 6)
			return;
		var name_ = value.NAME_;
		var count_WTJGX = toThousands(value.WTJGX);
		var count_YTJGX = toThousands(value.YTJGX);
		var count_BYGX = toThousands(value.BYGX);
		var num = count_WTJGX+"/"+count_YTJGX+"/"+count_BYGX;
		grid += '<tr> <td>' + (n + 1) + '</td><td title="' + name_ + '">'
				+ name_ + '</td><td title="' + num + '">' + num
				+ '</td> </tr>'
	});

	$("#" + tableId).html(grid);
}

function toThousands(num) {
	return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
}
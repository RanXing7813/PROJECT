layui.config({
	base : "js/"
}).use([ 'form', 'layer', 'jquery' ], function() {
	var form = layui.form, layer = parent.layer === undefined ? layui.layer : parent.layer, $ = layui.jquery;

	$(function() {
		var jvm = [];
		var ram = [];
		var cpu = [];
		var myChart = echarts.init(document.getElementById('main'));
		var res = [];
		var time = "";
		option = {
			legend : {
				x:'center',
				y:'top',
				data : [ 'jvm内存使用率', '物理内存使用率', 'cpu使用率' ]
			},
			grid : {
				x : 40,
				y : 30,
				x2 : 10,
				y2 : 35,
				borderWidth : 0,
				borderColor : "#FFFFFF"
			},
			xAxis : [ {
				axisLabel : {
					rotate : 40,
				},
				type : 'category',// 坐标轴类型，横轴默认为类目型'category'，纵轴默认为数值型'value'
				data : res
			} ],
			yAxis : [ {
				min : 0,
				max : 100,
				axisLabel : {
					formatter : '{value}%'
				}
			} ],
			series : [ {
				name : 'jvm内存使用率',
				type : 'line',
				data : []
			}, {
				name : '物理内存使用率',
				type : 'line',
				data : []
			}, {
				name : 'cpu使用率',
				type : 'line',
				data : []
			} ]
		};
		myChart.setOption(option);
		var main_one = echarts.init(document.getElementById('main_one'));
		var main_two = echarts.init(document.getElementById('main_two'));
		var main_three = echarts.init(document.getElementById('main_three'));
		one_option = {
			tooltip : {
				formatter : "{a} <br/>{b} : {c}%"
			},
			toolbox : {
				feature : {
					restore : {},
					saveAsImage : {}
				}
			},
			series : [ {
				name : '业务指标',
				type : 'gauge',
				detail : {
					formatter : '{value}%'
				},
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						color : [ [ 0.2, '#66ff00' ], [ 0.8, '#7B68EE	' ], [ 1, '#FF4500' ] ]
					}
				},
				data : [ {
					value : 0,
					name : 'JVM使用率'
				} ]
			} ]
		};
		two_option = {
			tooltip : {
				formatter : "{a} <br/>{b} : {c}%"
			},
			toolbox : {
				feature : {
					restore : {},
					saveAsImage : {}
				}
			},
			series : [ {
				name : '业务指标',
				type : 'gauge',
				detail : {
					formatter : '{value}%'
				},
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						color : [ [ 0.2, '#66ff00' ], [ 0.8, '#7B68EE	' ], [ 1, '#FF4500' ] ]

					}
				},
				data : [ {
					value : 0,
					name : '物理内存使用率'
				} ]
			} ]
		};
		three_option = {
			tooltip : {
				formatter : "{a} <br/>{b} : {c}%"
			},
			toolbox : {
				feature : {
					restore : {},
					saveAsImage : {}
				}
			},
			series : [ {
				name : '业务指标',
				type : 'gauge',
				detail : {
					formatter : '{value}%'
				},
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						color : [ [ 0.2, '#66ff00' ], [ 0.8, '#7B68EE	' ], [ 1, '#FF4500' ] ]
					}
				},
				data : [ {
					value : 0,
					name : 'JVM使用率'
				} ]
			} ]
		};

		main_one.setOption(one_option);
		main_two.setOption(two_option);
		one_option.series[0].data[0].name = '内存使用率';
		main_three.setOption(three_option);

		clearInterval(timeTicket);
		var timeTicket = setInterval(function() {

			$.ajax({
				type : "POST",
				url : '/platform/monitor/usage',
				async : false,
				dataType : 'json',
				success : function(json) {

					if (jvm.length == 20) {
						jvm.shift();
					}
					jvm.push(json.jvmUsage);
					if (ram.length == 20) {
						ram.shift();

					}
					ram.push(json.ramUsage);
					if (cpu.length == 20) {
						cpu.shift();

					}
					cpu.push(json.cpuUsage);
					// 动态数据接口 addData
					time = (new Date()).toLocaleTimeString().replace(/^\D*/, '');
					time = time.substr(time.indexOf(":") + 1);
					if (res.length == 20) {
						res.shift();

					}
					res.push(time);

					option.series[0].data = jvm;
					option.series[1].data = ram;
					option.series[2].data = cpu;
					myChart.setOption(option);
					one_option.series[0].data[0].value = json.jvmUsage;
					one_option.series[0].data[0].name = 'JVM使用率';
					main_one.setOption(one_option, true);

					two_option.series[0].data[0].value = json.cpuUsage;
					two_option.series[0].data[0].name = 'CPU使用率';
					main_two.setOption(two_option, true);

					three_option.series[0].data[0].value = json.ramUsage;
					three_option.series[0].data[0].name = '内存使用率';
					main_three.setOption(three_option, true);
				}
			});
		}, 30000);

	});

})

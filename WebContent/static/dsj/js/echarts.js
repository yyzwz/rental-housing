//获取url地址根目录  当前taomcat下路径
function getRootPath()
{
   var pathName = window.location.pathname.substring(1);
   var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
   return window.location.protocol + '//' + window.location.host + '/'+ webName;
}


$(function () {

	getTotalTenantNum();
	getTotalHouseNum();
	getAddTenantNum();
	getAddHouseNum();
	showData();
	//创建一个Show函数
	function Show(){
	    //在这里可以用其他jquery的动画
	    getTotalTenantNum();
		getTotalHouseNum();
		getAddTenantNum();
		getAddHouseNum();

	}
	//创建一个showTime函数
	function showData(){
	    //定时器
	     timer = setInterval(function(){
	        //调用一个Show()函数
	        Show();
	        i++;
	        //当图片是最后一张的后面时，设置图片为第一张
	        if(i==5){ i=0;}
	    },100000);
	}

    echarts_1();
    echarts_2();
    //map();
    echarts_3();
   // echarts_4();
    echarts_5();
    //echarts_6();

    function getTotalTenantNum() {
    	$.ajax({
            url: getRootPath()+'/com/houseOwner/getTotalTenantNum',
            method: 'GET',
            async: false
        }).success(function(result){
        	$('#totalTenantNum').text(result);
        });
    }
    function getTotalHouseNum() {
    	$.ajax({
            url: getRootPath()+'/com/houseOwner/getTotalHouseNum',
            method: 'GET',
            async: false
        }).success(function(result){
        	$('#totalHouseNum').text(result);
        });
    }
    function getAddTenantNum() {
    	$.ajax({
            url: getRootPath()+'/com/houseOwner/getAddTenantNum',
            method: 'GET',
            async: false
        }).success(function(result){
        	$('#addTenantNum').text(result);
        });
    }
    function getAddHouseNum() {
    	$.ajax({
            url: getRootPath()+'/com/houseOwner/getAddHouseNum',
            method: 'GET',
            async: false
        }).success(function(result){
        	$('#addHouseNum').text(result);
        });
    }
    function echarts_1() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_1'));

        var ageData = [];
        $.ajax({
            url: getRootPath()+'/com/houseOwner/getAgeData',
            method: 'GET',
            async: false
        }).success(function(result){
        	ageData=eval('(' + result + ')');
        });
        var age=[];
        for(i=0;i<ageData.length;i++){
        	age.push(ageData[i].name);
        }
        //var age=['20以下','20-30', '30-40', '40-50','50以上'];

        option = {
            backgroundColor: 'rgba(0,0,0,0)',
            tooltip: {
                trigger: 'item',
                formatter: "{b}: <br/>{c} ({d}%)"
            },
            color: [ '#20b9cf', '#2089cf', '#205bcf'],
            legend: { //图例组件，颜色和名字
                x: '70%',
                y: 'center',
                orient: 'vertical',
                itemGap: 12, //图例每项之间的间隔
                itemWidth: 10,
                itemHeight: 10,
                icon: 'rect',
                data: age,
                textStyle: {
                    color: [],
                    fontStyle: 'normal',
                    fontFamily: '微软雅黑',
                    fontSize: 12,
                }
            },
            series: [{
                name: '行业占比',
                type: 'pie',
                clockwise: false, //饼图的扇区是否是顺时针排布
                minAngle: 20, //最小的扇区角度（0 ~ 360）
                center: ['35%', '50%'], //饼图的中心（圆心）坐标
                radius: [40, 60], //饼图的半径
              //  avoidLabelOverlap: true, ////是否启用防止标签重叠
                itemStyle: { //图形样式
                    normal: {
                        borderColor: 'transparent',
                        borderWidth: 2,
                    },
                },
                label: { //标签的位置
                    normal: {
                        show: true,
                        position: 'inside', //标签的位置
                        formatter: "{d}%",
                        textStyle: {
                            color: '#fff',
                        }
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontWeight: 'bold'
                        }
                    }
                },
                data: ageData
            }, {
                name: '',
                type: 'pie',
                clockwise: false,
                silent: true,
                minAngle: 20, //最小的扇区角度（0 ~ 360）
                center: ['35%', '50%'], //饼图的中心（圆心）坐标
                radius: [0, 40], //饼图的半径
                itemStyle: { //图形样式
                    normal: {
                        borderColor: '#1e2239',
                        borderWidth: 1.5,
                        opacity: 0.21,
                    }
                },
                label: { //标签的位置
                    normal: {
                        show: false,
                    }
                },
                data: ageData
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        window.addEventListener("resize",function(){
            myChart.resize();
        });
    }
    function echarts_2() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_2'));
        var provinceTop5Data=[];
        
        $.ajax({
            url: getRootPath()+'/com/houseOwner/getProvinceTop5Data',
            method: 'GET',
            async: false
        }).success(function(result){
        	provinceTop5Data=eval('(' + result + ')');
        	var count=provinceTop5Data.length;
        	var temp={
                    value: 0,
                    name: "",
                    label: {
                        show: false
                    },
                    labelLine: {
                        show: false
                    }
                };
        	for(j=0;j<count;j++)
        		provinceTop5Data.push(temp);
        });
        var provinceTop5=[];//['江西省', '湖南省', '河南省', '河北省', '江苏省'];
        for(i=0;i<provinceTop5Data.length/2;i++){
        	provinceTop5.push(provinceTop5Data[i].name);
        }
        option = {
            backgroundColor: 'rgba(0,0,0,0)',
            tooltip: {
                trigger: 'item',
                formatter: "{b}  <br/>{c}"
            },
            legend: {
                x: 'center',
                y: '2%',
                data: provinceTop5,
                icon: 'circle',
                textStyle: {
                    color: '#fff',
                }
            },
            calculable: true,
            series: [{
                name: '学历',
                type: 'pie',
                //起始角度，支持范围[0, 360]
                startAngle: 0,
                //饼图的半径，数组的第一项是内半径，第二项是外半径
                radius: [51, 80],
                //支持设置成百分比，设置成百分比时第一项是相对于容器宽度，第二项是相对于容器高度
                center: ['50%', '20%'],
                //是否展示成南丁格尔图，通过半径区分数据大小。可选择两种模式：
                // 'radius' 面积展现数据的百分比，半径展现数据的大小。
                //  'area' 所有扇区面积相同，仅通过半径展现数据大小
                roseType: 'area',
                //是否启用防止标签重叠策略，默认开启，圆环图这个例子中需要强制所有标签放在中心位置，可以将该值设为 false。
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: true,
                        formatter: '{c}'
                    },
                    emphasis: {
                        show: true
                    }
                },
                labelLine: {
                    normal: {
                        show: true,
                        length2: 1,
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: provinceTop5Data
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        window.addEventListener("resize",function(){
            myChart.resize();
        });
    }
    
    function echarts_3() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_3'));
        var tenantNum=[];//[580, 490, 700,450, 550, 660, 540, 700,450, 550, 660, 540];
        var period=[];//['1月份','2月份 ','3月份 ','4月份','5月份','6月份','7月份','8月份','9月份','10月份','11月份','12月份'];
        $.ajax({
            url: getRootPath()+'/com/houseOwner/getTenantNumInPeriod',
            method: 'GET',
            async: false
        }).success(function(result){
        	tenantNumInPeriod=eval('(' + result + ')');
        	tenantNum=tenantNumInPeriod.tenantNum;
        	period=tenantNumInPeriod.period;
        });
        option = {

            tooltip : {
                trigger: 'axis'
            },
          
            grid: {
                left: '1%',
                right: '5%',
                top:'8%',
                bottom: '5%',
                containLabel: true
            },
            color:['#a4d8cc','#25f3e6'],
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },

            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    axisTick:{show:false},
                    boundaryGap : false,
                    axisLabel: {
                        textStyle:{
                            color: 'rgba(255,255,255,.6)',
                            fontSize:'12'
                        },
                        lineStyle:{
                            color:'rgba(255,255,255,.1)',
                        },
                        interval: {default: 0},
                     //   rotate:50,
                        formatter : function(params){
                            var newParamsName = "";// 最终拼接成的字符串
                            var paramsNameNumber = params.length;// 实际标签的个数
                            var provideNumber = 4;// 每行能显示的字的个数
                            var rowNumber = Math.ceil(paramsNameNumber / provideNumber);// 换行的话，需要显示几行，向上取整
                            /**
                             * 判断标签的个数是否大于规定的个数， 如果大于，则进行换行处理 如果不大于，即等于或小于，就返回原标签
                             */
                            // 条件等同于rowNumber>1
                            if (paramsNameNumber > provideNumber) {
                                /** 循环每一行,p表示行 */
                                var tempStr = "";
                                tempStr=params.substring(0,4);
                                newParamsName = tempStr+"...";// 最终拼成的字符串
                            } else {
                                // 将旧标签的值赋给新标签
                                newParamsName = params;
                            }
                            //将最终的字符串返回
                            return newParamsName
                        }

                    },
                    data: period
                }
            ],
            yAxis : {
				min:0,
                type : 'value',
                axisLabel: {
                    textStyle: {
                        color: '#ccc',
                        fontSize:'12',
                    }
                },
                axisLine: {
                    lineStyle:{
                        color:'rgba(160,160,160,0.2)',
                    }
                },
                splitLine: {
                    lineStyle:{
                        color:'rgba(160,160,160,0.2)',
                    }
                },

            },
			
            series : [
                {
                    // name:'简易程序案件数',
					 lineStyle:{
                        color:'#72b0f9',
                    },
					
                    type:'line',
                    areaStyle: {

                        normal: {type: 'default',
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 0.8, [{
                                offset: 0,
                                color: 'rgba(129,197,255,.6)'
                            }, {
                                offset: 1,
                                color: 'rgba(129,197,255,.0)'
                            }], false)
                        }
                    },
                    smooth:true,
                    itemStyle: {
                        normal: {areaStyle: {type: 'default'}}
                    },
                    data:tenantNum
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        window.addEventListener("resize",function(){
            myChart.resize();
        });
    }
    
    function echarts_5() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_5'));

      
        var houseNum=[];//[23, 22, 20, 30, 22,23, 22, 20, 30, 22, 30, 22];
        var period=[];//['1月份','2月份 ','3月份 ','4月份','5月份','6月份','7月份','8月份','9月份','10月份','11月份','12月份'];
        $.ajax({
            url: getRootPath()+'/com/houseOwner/getHouseNumInPeriod',
            method: 'GET',
            async: false
        }).success(function(result){
        	houseNumInPeriod=eval('(' + result + ')');
        	houseNum=houseNumInPeriod.houseNum;
        	period=houseNumInPeriod.period;
        });
        option = {
            // backgroundColor: "#141f56",

            tooltip: {
                show: "true",
                trigger: 'item',
                backgroundColor: 'rgba(0,0,0,0.4)', // 背景
                padding: [8, 10], //内边距
                // extraCssText: 'box-shadow: 0 0 3px rgba(255, 255, 255, 0.4);', //添加阴影
                formatter: function(params) {
                    if (params.seriesName != "") {
                        return params.name + ' ：  ' + params.value ;
                    }
                },

            },
            grid: {
                borderWidth: 0,
                top: 20,
                bottom: 35,
                left:40,
                right:10,
                textStyle: {
                    color: "#fff"
                }
            },
            xAxis: [{
                type: 'category',

                axisTick: {
                    show: false
                },
				 
                axisLine: {
                    show: true,
                    lineStyle: {
                         color:'rgba(255,255,255,0.2)',
                    }
                },
                axisLabel: {
                    inside: false,
                    textStyle: {
                        color: '#bac0c0',
                        fontWeight: 'normal',
                        fontSize: '12',
                    },
                    // formatter:function(val){
                    //     return val.split("").join("\n")
                    // },
                },
                data: period,
            }, {
                type: 'category',
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: false
                },
                splitArea: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                data: period,
            }],
            yAxis: {
				min:0,
                type: 'value',
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: 'rgba(255,255,255,0.2)',
                    }
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: 'rgba(255,255,255,0.1)',
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: '#bac0c0',
                        fontWeight: 'normal',
                        fontSize: '12',
                    },
                    formatter: '{value}',
                },
            },
            series: [{
                type: 'bar',
                itemStyle: {
                    normal: {
                        show: true,
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#00c0e9'
                        }, {
                            offset: 1,
                            color: '#3b73cf'
                        }]),
                        barBorderRadius: 50,
                        borderWidth: 0,
                    },
                    emphasis: {
                        shadowBlur: 15,
                        shadowColor: 'rgba(105,123, 214, 0.7)'
                    }
                },
                zlevel: 2,
                barWidth: '20%',
                data: houseNum,
            },
                {
                    name: '',
                    type: 'bar',
                    xAxisIndex: 1,
                    zlevel: 1,
                    itemStyle: {
                        normal: {
                            color: 'transparent',
                            borderWidth: 0,
                            shadowBlur: {
                                shadowColor: 'rgba(255,255,255,0.31)',
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowOffsetY: 2,
                            },
                        }
                    },
                    barWidth: '20%'
                }
            ]
        }


        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        window.addEventListener("resize",function(){
            myChart.resize();
        });
    }
    






})


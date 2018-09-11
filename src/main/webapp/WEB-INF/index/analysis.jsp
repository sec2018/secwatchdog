<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>包虫病防控犬驱虫远程管理系统 - 统计分析</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- bootstrap -->
    <link href="../css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="../css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="../css/compiled/layout.css" />
    <link rel="stylesheet" type="text/css" href="../css/compiled/elements.css" />
    <link rel="stylesheet" type="text/css" href="../css/compiled/icons.css" />

    <!-- libraries -->
    <link href="../css/lib/font-awesome.css" type="text/css" rel="stylesheet" />

    <!-- this page specific styles -->
    <link rel="stylesheet" href="../css/compiled/index.css" type="text/css" media="screen" />
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <script src="../js/jquery-latest.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("currentUser")==null){
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		return;
	}
%>
<script  type="text/javascript">
//	var data = <%=request.getAttribute("model")%>;
</script>
</head>
<body>
<div class="container">
</div>
 <!-- navbar -->
    <header class="navbar navbar-inverse" role="banner">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" id="menu-toggler">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <div class="logo">
                <h3 style="color: white;">&nbsp;&nbsp;包虫病防控犬驱虫远程管理系统	</h3>
            </div>
        </div>
    </header>
    <!-- end navbar -->
    <!-- sidebar -->
    <div id="sidebar-nav">
        <ul id="dashboard-menu">

            <li class="active">
                <div class="pointer">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <a style="cursor: pointer" id="a_areasee" href="#">
                    <i class="icon-home"></i>
                    <span id="span_leftscan">全国总览</span>
                </a>
            </li>
            <li>
                <a style="cursor: pointer" id="a_managepage">
                    <i class="icon-wrench"></i>
                    <span>管理页面</span>
                </a>
            </li>
            <li>
                <a style="cursor:pointer">
                    <i class="icon-cogs"></i>
                    <span>统计分析</span>
                </a>
            </li>
            <li>
                <a style="cursor: pointer" id="a_personalpage">
                    <i class="icon-user"></i>
                    <span>个人信息</span>
                </a>
            </li>
            <!--<li>
                <a style="cursor:pointer" id="pagereflash">
                    <i class="icon-repeat"></i>
                    <span>刷新</span>
                </a>
            </li>-->
            <li>
                <a href="../user/logout.do" style="cursor:pointer">
                    <i class="icon-off"></i>
                    <span>退出</span>
                </a>
            </li>
        </ul>
    </div>
    <!-- end sidebar -->
    <!-- main container -->
    <div class="content">
    	<div class="content pb0">
			<!-- Info boxes -->
			<!-- /.row -->
			<div class="row">
				<div class="col-md-12">
					<div class="box">
						<div class="box-header with-border">
							<h3 class="box-title">月度报告</h3>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<div class="row">
								<div class="col-md-8">
									<div class="chart">
										<div id="myChart" style="height:230px;width:99%"></div>
										<div id="illChart" style="height:230px;width:99%"></div>
										<script src="http://echarts.baidu.com/dist/echarts.common.min.js"></script>
										<script>
											var data = [];
											data.data1 = [143, 152, 173, 194, 205, 236, 247,268,289,310,321, 332, 343, 344,351, 361, 378,418,429,460,491, 522, 553, 594, 635, 686, 727,788,829,960];
											data.data2 = [122, 112, 103, 94, 83, 73, 64,57,51,46,42, 38, 35, 31,28, 26, 24,22,19,17,15, 12, 9, 7, 4, 3, 2,1,0,0];
											data.data3 = [821, 823, 846, 850, 856, 862, 867,868,889,910,921, 932, 943, 944,951, 952, 952,958,960,960,971, 972, 983, 994, 994, 996, 1017,1018,1019,1019];
											data.data4 = [];
											var dogtotal = 0;
											var illtotal = 0;
											for(i=0;i<30;i++){
												data.data4.push((data.data2[i]/data.data3[i]).toFixed(2));
												dogtotal += data.data3[i];
												illtotal += data.data2[i];
											}
											
											var option = {
											    title : {
											        text: '2018-9'
											    },
											    tooltip : {
											        trigger: 'axis'
											    },
											    legend: {
											        data:['佩圈数量','发病牧犬数','牧犬数量']
											    },
											    toolbox: {
											        show : true,
											        feature : {
											            mark : {show: true},
											            dataView : {show: true, readOnly: false},
											            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
											            restore : {show: true},
											            saveAsImage : {show: true}
											        }
											    },
											    calculable : true,
											    grid : {
											    	top: '49',
											    	right: '20',
											    	bottom: '30'
											    },
											    xAxis : [
											        {
											            type : 'category',
											            boundaryGap : false,
											            data:[1, 2, 3, 4, 5, 6, 7,8,9,10,11, 12, 13, 14,15, 16, 17,18,19,20,21, 22, 23, 24, 25, 26, 27,28,29,30]
											        }
											    ],
											    yAxis : [
											        {
											            type : 'value'
											        }
											    ],
											    series : [
											        {
											            name:'佩圈数量',
											            type:'line',
											            smooth:true,
											            itemStyle: {normal: {areaStyle: {type: 'default'}}},
											            data:data.data1
											        },
											        {
											            name:'发病牧犬数',
											            type:'line',
											            smooth:true,
											            itemStyle: {normal: {areaStyle: {type: 'default'}}},
											            data:data.data2
											        },
											        {
											            name:'牧犬数量',
											            type:'line',
											            smooth:true,
											            itemStyle: {normal: {areaStyle: {type: 'default'}}},
											            data:data.data3
											        }
											     ]
											};
											
											
											var illoption = {
												    title : {
												        text: '2018-9'
												    },
												    tooltip : {
												        trigger: 'axis'
												    },
												    legend: {
												        data:['发病率']
												    },
												    toolbox: {
												        show : true,
												        feature : {
												            mark : {show: true},
												            dataView : {show: true, readOnly: false},
												            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
												            restore : {show: true},
												            saveAsImage : {show: true}
												        }
												    },
												    calculable : true,
												    grid : {
												    	top: '49',
												    	right: '20',
												    	bottom: '30'
												    },
												    xAxis : [
												        {
												            type : 'category',
												            boundaryGap : false,
												            data:[1, 2, 3, 4, 5, 6, 7,8,9,10,11, 12, 13, 14,15, 16, 17,18,19,20,21, 22, 23, 24, 25, 26, 27,28,29,30]
												        }
												    ],
												    yAxis : [
												        {
												            type : 'value'
												        }
												    ],
												    series : [
												        {
												            name:'发病率',
												            type:'line',
												            smooth:true,
												            itemStyle: {normal: {areaStyle: {type: 'default'}}},
												            data:data.data4
												        }
												     ]
												};
											// 基于准备好的dom，初始化echarts实例
									        var myChart = echarts.init(document.getElementById('myChart'));
									        myChart.setOption(option);
									        
									        var illChart = echarts.init(document.getElementById('illChart'));
									        illChart.setOption(illoption);
										</script>
									</div>
									<!-- /.chart-responsive -->
								</div>
								<!-- /.col -->
								<div class="col-md-4">
									<p class="text-center">
										<strong>完成目标</strong>
									</p>
									<div class="progress-group">
										<span class="progress-text">牧犬佩圈完成情况</span>
										<span class="progress-number"><b>960</b>/1019</span>
										<div class="progress sm">
											<div class="progress-bar progress-bar-aqua" style="width: 80%"></div>
										</div>
									</div>
									<!-- /.progress-group -->
									<div class="progress-group">
										<span class="progress-text">累计发病数量占比</span>
										<span class="progress-number"><b>1140</b>/28048</span>
										<div class="progress sm">
											<div class="progress-bar progress-bar-red" style="width: 80%"></div>
										</div>
									</div>
									<!-- /.progress-group -->
									<div class="progress-group">
										<span class="progress-text">发病救治占比</span>
										<span class="progress-number"><b>997</b>/1140</span>
										<div class="progress sm">
											<div class="progress-bar progress-bar-yellow" style="width: 80%"></div>
										</div>
									</div>
									<!-- /.progress-group -->
								</div>
								<!-- /.col -->
							</div>
							<!-- /.row -->
						</div>
						<!-- ./box-body -->
						<div class="box-footer">
							<div class="row">
								<div class="col-sm-3 col-xs-6">
									<div class="description-block border-right">
										<span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 94.21%</span>
										<h5 class="description-header">960</h5>
										<span class="description-text">佩戴项圈总数</span>
									</div>
									<!-- /.description-block -->
								</div>
								<!-- /.col -->
								<div class="col-sm-3 col-xs-6">
									<div class="description-block border-right">
										<span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 4.06%</span>
										<h5 class="description-header">1140</h5>
										<span class="description-text">月累计发病率</span>
									</div>
									<!-- /.description-block -->
								</div>
								<!-- /.col -->
								<div class="col-sm-3 col-xs-6">
									<div class="description-block">
										<span class="description-percentage text-red"><i class="fa fa-caret-down"></i> 95.86%</span>
										<h5 class="description-header">997</h5>
										<span class="description-text">发病救治率</span>
									</div>
									<!-- /.description-block -->
								</div>
							</div>
							<!-- /.row -->
						</div>
						<!-- /.box-footer -->
					</div>
					<!-- /.box -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
    </div>

        

	<script>
		$(window).resize(function(){
			var footerHeight  = $('.main-footer').outerHeight() || 0;
			var windowHeight  = $(window).height();
			$('.content').css('min-height', windowHeight - footerHeight)
		}).resize();
	</script>
    <!-- scripts -->
    <script src="../js/bootstrap.min.js"></script>
    <!--echarts2-->
    <script src="../js/other.js"></script>
    <script src="../js/echarts.js"></script>
    <!-- flot charts -->
    <script src="../js/theme.js"></script>
</body>
</html>
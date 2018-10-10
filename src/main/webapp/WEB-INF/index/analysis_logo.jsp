<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>包虫病防控犬驱虫远程管理系统 - 统计分析</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">

    <!-- bootstrap -->
    <link href="../css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="../css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="../css/compiled/layout.css" />
    <link rel="stylesheet" type="text/css" href="../css/compiled/elements.css" />
    <link rel="stylesheet" type="text/css" href="../css/compiled/icons.css" />
    <link rel="stylesheet" type="text/css" href="../layui/css/layui.css" />

    <!-- libraries -->
    <link href="../css/lib/font-awesome.css" type="text/css" rel="stylesheet" />
    

    <!-- this page specific styles -->
    <link rel="stylesheet" href="../css/compiled/index.css" type="text/css" media="screen" />
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
	<style>
		.cc-content{
			padding:20px;
		}
		.cc-content .box-body{
			padding-left:10px;
			margin:15px 0;
		}
		.cc-content .mt25{
			margin-top:25px;
		}
		.cc-content .mb15{
			margin-bottom:15px;
		}
		.cc-content .box-color{
			position:relative;
			text-align:center;
			color:#fff;
			box-shadow: 0 2px 5px 0 #4c89b2;
			padding:10px 0 20px 0;
			background:#74b9e6;
			border-radius:4px;
		}
		.cc-content .cff{
			color:#fff;
			
		}
		.cc-content .cff-prencent{
			color:#fff;
			font-weight:blod;
			font-size:20px;
		}
		.cc-content .cc-progress-bar{
			position:absolute;
			bottom:0;
			left:0;
			width:80%;
			height:6px;
			font-size:12px;
			text-align:center;
			color:#fff;
			background:#eabe77;
			border-radius:4px;
		}
		.cc-content .box-color1{
			box-shadow: 0 2px 5px 0 #518c69;
			background:#8dd1a9;
		}
		.cc-content .box-color2{
			box-shadow: 0 2px 5px 0 #6da79a;
			background:#97d3c5;
		}
		.layui-table tbody tr:first-child td:nth-of-type(1){
			color:red !important;
			text-decoration: underline;
		}
		.layui-table tbody tr:first-child td:nth-of-type(1) div:before{
			content:'> ';
		}
	</style>
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
    	<div class="pb0 cc-content">
			<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
			  <ul class="layui-tab-title">
			    <li class="layui-this">概况</li><
			    <li>喂药提醒率</li>
			    <li>硬件驱虫比</li>
			    <li>报警率</li>
			    <li>视频上传率</li>
			    <li>犬粪阳性率</li>
			  </ul>
			  <div class="layui-tab-content" style="height: 350px;">
			  	<div class="layui-tab-item layui-show">
					<div class="row">
						<div class="col-md-12">
							<div class="box">
								<!-- <div class="box-header with-border">
									<h3 class="box-title">月度报告</h3>
								</div>  -->
								<!-- /.box-header -->
								<div class="box-body">
									<div class="row">
										<div class="col-md-12">
											<div class="chart">
												<div id="myChart"  style="height:230px;width:99%"></div>
												<div id="illChart" class="mt25" style="height:230px;width:99%"></div>
												<script src="../js/echarts.common.min.js"></script>
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
										<!-- /.col
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
											</div>-->
											<!-- /.progress-group 
											<div class="progress-group">
												<span class="progress-text">累计发病数量占比</span>
												<span class="progress-number"><b>1140</b>/28048</span>
												<div class="progress sm">
													<div class="progress-bar progress-bar-red" style="width: 80%"></div>
												</div>
											</div>-->
											<!-- /.progress-group
											<div class="progress-group">
												<span class="progress-text">发病救治占比</span>
												<span class="progress-number"><b>997</b>/1140</span>
												<div class="progress sm">
													<div class="progress-bar progress-bar-yellow" style="width: 80%"></div>
												</div>
											</div>-->
											<!-- /.progress-group -->
										</div>
										<!-- /.col -->
									</div>
									<!-- /.row -->
								</div>
								<!-- ./box-body -->
								<div class="box-footer">
									<div class="row">
										<div class="col-sm-12 col-md-4 mb15" >
											<div class="description-block border-right box-color box-color1">
												<!--<span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 94.21%</span> -->
												<div class="cff" >960/1099</div>
												<div class="cff-prencent" >94.21%</div>
												<span class="description-text">佩戴项圈总数</span>
												<div class="cc-progress-bar">
												</div>
											</div>
											<!-- /.description-block -->
										</div>
										<!-- /.col -->
										<div class="col-sm-12 col-md-4 mb15">
											<div class="description-block border-right box-color">
												<div class="cff" >1140/28048</div>
												<div class="cff-prencent" >4.06%</div>
												<span class="description-text">月累计发病率</span>
												<div class="cc-progress-bar" style="width:4.06%;">
												</div>
											</div>
											<!-- /.description-block -->
										</div>
										<!-- /.col -->
										<div class="col-sm-12 col-md-4 mb15">
											<div class="description-block box-color box-color2">
												<div class="cff" >997/1140</div>
												<div class="cff-prencent" >95.86%</div>
												<span class="description-text">发病救治率</span>
												<div class="cc-progress-bar" style="width:95.86%;">
												</div>
											</div>
											<!-- /.description-block -->
										</div>
									</div>
									<!-- /.row -->
								</div>
								<!-- /.box-footer -->
							</div>
					</div>
				</div>
			    <div class="layui-tab-item">
		    		<table class="layui-hide" id="table1" lay-filter="table1"></table>
			    	<!-- <table class="layui-table" lay-data="{height:310, url:'../analysis/anawakelv.do', page:true, id:'test2', skin: 'row', even: true}">
					  <thead>
					    <tr>
					      <th lay-data="{field:'govname'}">行政名称</th>
					      <th lay-data="{field:'neckletnum', sort: true}">项圈数量</th>
					      <th lay-data="{field:'nswaketimes',sort: true}">项圈应提醒总次数</th>
					      <th lay-data="{field:'nfwaketimes'}">项圈实际提醒总次数</th>
					      <th lay-data="{field:'nwakelv'}">项圈喂药提醒率</th>
					      <th lay-data="{field:'appnum', sort: true, style:'background-color: #eee;'}">喂饲器数量</th>
					      <th lay-data="{field:'aswaketimes', sort: true}">喂饲器应提醒次数</th>
					      <th lay-data="{field:'afwaketimes', style:'background-color: #009688; color: #fff;'}">喂饲器实际提醒次数</th>
					      <th lay-data="{field:'awakelv', sort: true}">喂饲器喂饲提醒率</th>
					      <th lay-data="{field:'nanum', sort: true}">使用项圈喂饲器总数</th>
					      <th lay-data="{field:'naswaketimes', sort: true}">总应提醒总数</th>
					      <th lay-data="{field:'nafwaketimes', sort: true}">实际总提醒次数</th>
					      <th lay-data="{field:'nawakelv', sort: true}">总提醒率</th>
					    </tr>
					  </thead>
					</table> -->
			    </div>
			    <div class="layui-tab-item">
					<table class="layui-hide" id="table2" lay-filter="table2"></table>
				</div>
			    <div class="layui-tab-item">
					<table class="layui-hide" id="table3" lay-filter="table3"></table>
				</div>
			    <div class="layui-tab-item">内容4</div>
			    <div class="layui-tab-item">内容5</div>
			  </div>
			</div> 
		</div>
    </div>

        
	<script src="../layui/layui.js"  charset="utf-8"></script>
	<script>
	var data1,data2;
	var flag1;
	$.ajax({
		url: '../analysis/anawakelv.do',
		method: "POST",
		success:function(data){
			data1 = data;
			flag1 = data1.data[0].govname;
			
			layui.use('table', function(){
			  var table = layui.table;
			  table.render({
			    elem: '#table1',
			    response:{  
			    	statusName: 'code' ,// 对应 code
			    	statusCode: 200,
			    	msgName: 'success'  , // 对应 msg  
			    	countName: 'total' , // 对应 count  
			    	dataName: 'data'  // 对应 data
		    	},
				cols: [[
			    	{field:'govname',title: '行政名称',fixed: true, event: 'gov', style:'color:blue;cursor:pointer;'},
			    	{field:'neckletnum', sort: true,title: '项圈数量'},
			    	{field:'nswaketimes',sort: true,title: '项圈应提醒总次数'},
			    	{field:'nfwaketimes',title: '项圈实际提醒总次数'},
			    	{field:'nwakelv',title: '项圈喂药提醒率'},
			    	{field:'appnum', sort: true, style:'background-color: #eee;',title: '喂饲器数量'},
			    	{field:'aswaketimes', sort: true,title: '喂饲器应提醒次数'},
			    	{field:'afwaketimes', style:'background-color: #009688; color: #fff;',title: '喂饲器实际提醒次数'},
			    	{field:'awakelv', sort: true,title: '喂饲器喂饲提醒率'},
			    	{field:'nanum', sort: true,title: '使用项圈喂饲器总数'},
			    	{field:'naswaketimes', sort: true,title: '总应提醒总数'},
			    	{field:'nafwaketimes', sort: true,title: '实际总提醒次数'},
			    	{field:'nawakelv', sort: true,title: '总提醒率'}
			    ]],
			    data: data1.data,
		    	id: 'testReload1',
			    page:false, 
				skin: 'row', 
				even: true,
				height: 500
			  });
			  //监听单元格事件
			  table.on('tool(table1)', function(obj){
			    var data = obj.data;
			    if(obj.event === 'gov'){
			      alert(data.govname);
			      if(data.govname == "全国" || data.govname.indexOf("村") != -1){
			    	  return;
			      }
				  
				  if(flag1 != data.govname){
					//ajax
					  $.ajax({
						url: '../analysis/anawakelv.do?flag=lower&districode='+data.govname,
						method: "POST",
						success:function(data){
							data2 = data;
							flag1 = data2.data[0].govname;
							//执行重载
							table.reload('testReload1', {
								data:data2.data
							});
						}
					  })
				  }else{
					  $.ajax({
						url: '../analysis/anawakelv.do?flag=upper&districode='+flag1,
						method: "POST",
						success:function(data){
							data2 = data;
							flag1 = data2.data[0].govname;
							//执行重载
							table.reload('testReload1', {
								data:data2.data
							});
						}
					  })
				  }
				}
			  });
			});
		}
	})
	
	var data3,data4;
	var flag2;
	$.ajax({
		url: '../analysis/anadevicedlv.do',
		method: "POST",
		success:function(data){
			data3 = data;
			flag2 = data.data[0].govname;
			
			layui.use('table', function(){
			  var table = layui.table;
			  table.render({
			    elem: '#table2',
			    response:{  
			    	statusName: 'code' ,// 对应 code
			    	statusCode: 200,
			    	msgName: 'success'  , // 对应 msg  
			    	countName: 'total' , // 对应 count  
			    	dataName: 'data'  // 对应 data
		    	},
				cols: [[
			    	{field:'govname',title: '行政名称',fixed: true, event: 'gov', style:'color:blue;cursor:pointer;'},
			    	{field:'dognum', sort: true,title: '犬数量'},
			    	{field:'neckletdognum',sort: true,title: '戴项圈数量'},
			    	{field:'npilltimes',title: '项圈喂药次数'},
			    	{field:'appdognum',title: '喂饲器数量'},
			    	{field:'apilltimes', sort: true, style:'background-color: #eee;',title: '喂饲器喂药次数'},
			    	{field:'manpilldog', sort: true,title: '人工喂药只数'},
			    	{field:'manpilltimes', style:'background-color: #009688; color: #fff;',title: '人工喂药次数'},
			    	{field:'allpilldognum', sort: true,title: '喂药犬总只数'},
			    	{field:'allpilltimes', sort: true,title: '喂药总次数'},
			    	{field:'devicedlv', sort: true,title: '使用硬件驱虫犬的比例（%）'}
			    ]],
			    data: data3.data,
		    	id: 'testReload2',
			    page:false, 
				skin: 'row', 
				even: true,
				height: 500
			  });
			  //监听单元格事件
			  table.on('tool(table2)', function(obj){
			    var data = obj.data;
			    if(obj.event === 'gov'){
			      alert(data.govname);
			      if(data.govname == "全国" || data.govname.indexOf("村") != -1){
			    	  return;
			      }
				  
				  if(flag2 != data.govname){
					//ajax
					  $.ajax({
						url: '../analysis/anadevicedlv.do?flag=lower&districode='+data.govname,
						method: "POST",
						success:function(data){
							data4 = data;
							flag2 = data4.data[0].govname;
							//执行重载
							table.reload('testReload2', {
								data:data4.data
							});
						}
					  })
				  }else{
					  $.ajax({
						url: '../analysis/anadevicedlv.do?flag=upper&districode='+flag2,
						method: "POST",
						success:function(data){
							data4 = data;
							flag2 = data4.data[0].govname;
							//执行重载
							table.reload('testReload2', {
								data:data4.data
							});
						}
					  })
				  }
				}
			  });
			});
		}
	})
	
	
	var data5,data6;
	var flag3;
	$.ajax({
		url: '../analysis/anawarnlv.do',
		method: "POST",
		success:function(data){
			data5 = data;
			flag3 = data.data[0].govname;
			
			layui.use('table', function(){
			  var table = layui.table;
			  table.render({
			    elem: '#table3',
			    response:{  
			    	statusName: 'code' ,// 对应 code
			    	statusCode: 200,
			    	msgName: 'success'  , // 对应 msg  
			    	countName: 'total' , // 对应 count  
			    	dataName: 'data'  // 对应 data
		    	},
				cols: [[
			    	{field:'govname',title: '行政名称',fixed: true, event: 'gov', style:'color:blue;cursor:pointer;'},
			    	{field:'neckletnum', sort: true,title: '项圈数量'},
			    	{field:'nbugtimes',sort: true,title: '项圈故障次数'},
			    	{field:'nwarntimes',title: '项圈报警次数'},
			    	{field:'nwarnlv',title: '项圈故障报警率'},
			    	{field:'appnum', sort: true, style:'background-color: #eee;',title: '喂饲器数量'},
			    	{field:'abugtimes', sort: true,title: '喂饲器故障次数'},
			    	{field:'awarntimes', style:'background-color: #009688; color: #fff;',title: '喂饲器报警次数'},
			    	{field:'awarnlv', sort: true,title: '喂饲器报警率'},
			    	{field:'devicenum', sort: true,title: '使用项圈喂饲器总数'},
			    	{field:'dbugtimes', sort: true,title: '总故障总数'},
			    	{field:'dwarntimes', sort: true,title: '实际报警次数'},
			    	{field:'dlv', sort: true,title: '总报警率'}
			    ]],
			    data: data5.data,
		    	id: 'testReload3',
			    page:false, 
				skin: 'row', 
				even: true,
				height: 500
			  });
			  //监听单元格事件
			  table.on('tool(table3)', function(obj){
			    var data = obj.data;
			    if(obj.event === 'gov'){
			      alert(data.govname);
			      if(data.govname == "全国" || data.govname.indexOf("村") != -1){
			    	  return;
			      }
				  
				  if(flag3 != data.govname){
					//ajax
					  $.ajax({
						url: '../analysis/anawarnlv.do?flag=lower&districode='+data.govname,
						method: "POST",
						success:function(data){
							data6 = data;
							flag3 = data6.data[0].govname;
							//执行重载
							table.reload('testReload3', {
								data:data6.data
							});
						}
					  })
				  }else{
					  $.ajax({
						url: '../analysis/anawarnlv.do?flag=upper&districode='+flag3,
						method: "POST",
						success:function(data){
							data6 = data;
							flag3 = data6.data[0].govname;
							//执行重载
							table.reload('testReload3', {
								data:data6.data
							});
						}
					  })
				  }
				}
			  });
			});
		}
	})
	
	$(window).resize(function(){
		var footerHeight  = $('.main-footer').outerHeight() || 0;
		var windowHeight  = $(window).height();
		$('.content').css('min-height', windowHeight - footerHeight)
	}).resize();
	
	layui.use('element', function(){
		  var $ = layui.jquery,
		  element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块 
	});
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
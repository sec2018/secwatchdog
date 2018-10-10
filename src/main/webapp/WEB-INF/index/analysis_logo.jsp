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
		.layui-table tbody tr:first-child td:nth-of-type(2){
			color:red !important;
			text-decoration: underline;
		}
		.layui-table tbody tr:first-child td:nth-of-type(2) div:before{
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
			    <li class="layui-this">喂药提醒率</li>
			    <li>硬件驱虫比</li>
			    <li>报警率</li>
			    <li>视频上传率</li>
			    <li>犬粪阳性率</li>
			  </ul>
			  <div class="layui-tab-content" style="height: 100px;">
			    <div class="layui-tab-item layui-show">
			    	<table class="layui-hide" id="test" id="table1" lay-filter="table1"></table>
			    
			    	内容不一样是要有，因为你可以监听tab事件（阅读下文档就是了）
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
			    <div class="layui-tab-item">内容2</div>
			    <div class="layui-tab-item">内容3</div>
			    <div class="layui-tab-item">内容4</div>
			    <div class="layui-tab-item">内容5</div>
			  </div>
			</div> 
		</div>
    </div>

        
	<script src="../layui/layui.js"  charset="utf-8"></script>
	<script>
	var data1,data2;
	var flag;
	$.ajax({
		url: '../analysis/anawakelv.do',
		method: "POST",
		success:function(data){
			data1 = data;
			flag = data1.data[0].id;
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
	layui.use('table', function(){
	  var table = layui.table;
	  table.render({
	    elem: '#test',
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
    	id: 'testReload',
	    page:true, 
		skin: 'row', 
		even: true,
		height: 315
	  });
	  //监听单元格事件
	  table.on('tool(table1)', function(obj){
	    var data = obj.data;
	    if(obj.event === 'gov'){
	      alert(data.id);
		  
		  if(flag != data.id){
			//ajax
			  $.ajax({
				url: '../analysis/anawakelv.do'+data.id,
				method: "POST",
				success:function(data){
					data2 = data;
					flag = data2.data[0].id;
					//执行重载
					table.reload('testReload', {
						data:data2.data
					});
				}
			  })
		  }else{
		  
		  }
		}
	  });
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
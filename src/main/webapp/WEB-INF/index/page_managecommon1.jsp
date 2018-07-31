<!DOCTYPE html>
<html>
<head>
<title>包虫病防控犬驱虫远程管理系统 - 用户列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- bootstrap -->
<link href="../css/bootstrap/bootstrap.css" rel="stylesheet" />
<link href="../css/bootstrap/bootstrap-overrides.css"
	type="text/css" rel="stylesheet" />

<!-- global styles -->
<link rel="stylesheet" type="text/css"
	href="../css/compiled/layout.css" />
<link rel="stylesheet" type="text/css"
	href="../css/compiled/elements.css" />
<link rel="stylesheet" type="text/css"
	href="../css/compiled/icons.css" />

<!-- libraries -->
<link href="../css/lib/font-awesome.css" type="text/css"
	rel="stylesheet" />

<!-- this page specific styles -->
<link rel="stylesheet" href="../css/compiled/user-list.css"
	type="text/css" media="screen" />

<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("currentUser") == null) {
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		return;
	}
%>
<script type="text/javascript">
	var data =
<%=request.getAttribute("model")%>
	;
</script>
</head>
<body>

	<!-- navbar -->
	<header class="navbar navbar-inverse" role="banner">
		<div class="navbar-header">
			<button class="navbar-toggle" type="button" data-toggle="collapse"
				id="menu-toggler">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<div class="logo">
				<h3 style="color: white;">&nbsp;&nbsp;包虫病防控犬驱虫远程管理系统</h3>
			</div>
		</div>
	</header>
	<!-- end navbar -->
	<!-- sidebar -->
	<div id="sidebar-nav">
		<ul id="dashboard-menu">

			<li id="li_countrysee" style="cursor: pointer"><a> <i
					class="icon-home"></i> <span id="span_leftscan">全国总览</span>
			</a></li>
			<li class="active">
				<div class="pointer">
					<div class="arrow"></div>
					<div class="arrow_border"></div>
				</div> <a href="#"> <i class="icon-wrench"></i> <span>管理页面</span>
			</a>
			</li>
			<li><a style="cursor: pointer"> <i class="icon-cogs"></i> <span>统计分析</span>
			</a></li>
			<li><a href="../personal/pagePersonal.do" style="cursor: pointer"> <i
					class="icon-user"></i> <span>个人信息</span>
			</a></li>
			<!--<li>
                <a style="cursor:pointer" id="pagereflash">
                    <i class="icon-repeat"></i>
                    <span>刷新</span>
                </a>
            </li>-->
			<li><a href="../user/logout.do"
				style="cursor: pointer"> <i class="icon-off"></i> <span>退出</span>
			</a></li>

		</ul>
	</div>
	<!-- end sidebar -->
	<!-- main container -->
	<div class="content">

		<div id="pad-wrapper" class="users-list">
			<h3 id="h3_adminname" style="margin-top: 30px">国家管理员</h3>
			<div class="row header">

				<div class="col-md-10 col-sm-12 col-xs-12 pull-left"
					style="margin-top: 30px">
					<input id="input_managername" type="text" class="col-md-5 search"
						placeholder="输入用户姓名、区域或号码的一种">

					<!-- custom popup filter -->
					<!-- styles are located in css/elements.css -->
					<!-- script that enables this dropdown is located in js/theme.js -->
					<a id="div_usersearch" class="btn-flat default"
						style='margin-left: 5px'> 点击搜索 </a>
				    <a id="div_newuserpage"
						class="btn-flat success pull-right  style='margin:5px'"> <span>&#43;</span>
						添加管理员
					</a>
				</div>
			</div>

			<!-- Users table -->
			<div class="row">
				<div class="col-md-12">
					<table class="table table-hover table_hide">
						<thead>
							<tr>
								<th class="col-md-1">姓名</th>
								<th class="col-md-1"><span class="line"></span>注册时间</th>
								<th class="col-md-1"><span class="line"></span>管理区域</th>
								<th class="col-md-1"><span class="line"></span>工作单位</th>
								<th class="col-md-1"><span class="line"></span>犬只总数</th>
								<th class="col-md-1"><span class="line"></span>佩圈犬只总数</th>
								<th class="col-md-1"><span class="line"></span>喂饲器犬只数</th>
								<th class="col-md-1"><span class="line"></span>办公电话</th>
								<th class="col-md-1"><span class="line"></span>手机号码</th>
							</tr>
						</thead>
						<tbody id="tbody_pagemanagecommon"></tbody>
					</table>
				</div>
			</div>
			<div id="barcon" class="row barcon">
				<div id="barcon1" class="barcon1"></div>
				<div id="barcon2" class="barcon2">
					<ul>
						<li><a href="###" id="firstPage">首页</a></li>
						<li><a href="###" id="prePage">上一页</a></li>
						<li><a href="###" id="nextPage">下一页</a></li>
						<li><a href="###" id="lastPage">尾页</a></li>
						<li><select id="jumpWhere"></select></li>
						<li><a href="###" id="jumpPage">跳转</a></li>
					</ul>
				</div>
			</div>
			<!-- end users table -->
		</div>
	</div>
	<!-- end main container -->
	<!-- scripts -->
	<script src="../js/jquery-latest.js"></script>
	<!--  <script src="../js/Jquery_ajax_extention.js"></script>  -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/theme.js"></script>
	<script src="../js/pages/pagemanagecommon1.js"
		type="text/javascript"></script>
</body>
</html>

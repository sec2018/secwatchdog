<!DOCTYPE html>
<html>
<head>
    <title>包虫病防控犬驱虫远程管理系统 - 用户列表</title>
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
    <link rel="stylesheet" href="../css/compiled/userprofile.css" type="text/css" media="screen" />

    <!-- open sans font -->

    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%
	if(session.getAttribute("currentUser")==null){
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		return;
	}
%>
<script  type="text/javascript">
	var data = <%=request.getAttribute("model")%>;
</script>
</head>
<body>

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
                <h3 style="color:white;">&nbsp;&nbsp;包虫病防控犬驱虫远程管理系统 </h3>
            </div>
        </div>
    </header>
    <!-- end navbar -->
    <!-- sidebar -->
    <div id="sidebar-nav">
        <ul id="dashboard-menu">

            <li >
                
                <a style="cursor:pointer" id="a_areasee" href="#">
                    <i class="icon-home"></i>
                    <span id="span_leftscan">地区总览</span>
                </a>
            </li>
            <li class="active">
            <div class="pointer">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <a style="cursor:pointer" id="a_managepage">
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
                <a href="../personal/pagePersonal.do" style="cursor:pointer">
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
        <div id="pad-wrapper" class="user-profile">
            <!-- header -->
            <div class="row header">
                <div class="col-md-8">
                    <h3 class="name" id="h3_managername">黄大明</h3>
                 <!--    <span class="area" id="span_adminlevel">某级管理员</span> -->
                </div>
                <a href="javascript:history.go(-1)" class="btn-flat icon large pull-right" style="margin-left:5px">
                    返回上一页
                </a>
                <a id="a_rebackpwd" class="btn-flat icon large pull-right edit" style="margin-left:5px">
                    密码重置
                </a>
                <a id="a_activeadmin" class="btn-flat icon large pull-right edit">
                    账户激活
                </a>
            </div>
            <h3>个人信息</h3>
            <div class="row">
                <div class="col-md-6">
                    <table class="table table-hover">
                        <tbody>
                            <!-- row -->
                            <tr>
                                <td>
                                    区域
                                </td>
                                <td id="td_area">
                                    属于某某区域
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    单位
                                </td>
                                <td id="td_job">
                                    村支书
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    注册时间
                                </td>
                                <td id="td_registertime">
                                    2017.09.26 08:45:52
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    联系地址
                                </td>
                                <td id="td_address">
                                    内蒙古赤峰
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    用户状态
                                </td>
                                <td id="td_adminstatus">
                                    未激活
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-6">
                    <table class="table table-hover">
                        <tbody>
                            <!-- row -->
                            <tr>
                                <td>
                                    犬只总数
                                </td>
                                <td id="td_dogtotal">
                                    21000
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    已佩戴项圈犬只总数
                                </td>
                                <td id="td_neckletedtotal">
                                    14500
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    办公电话
                                </td>
                                <td id="td_officecall">
                                    02112345678
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    手机号码
                                </td>
                                <td id="td_telphone">
                                    18888888888
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
            </div>
            <!-- end main container -->
            <!-- scripts -->
            <!--<script src="http://code.jquery.com/jquery-latest.js"></script>-->
            <script src="../js/jquery-latest.js"></script>
  
            <script src="../js/bootstrap.min.js"></script>
            <script src="../js/theme.js"></script>
            <script src="../js/pages/userprofile.js" type="text/javascript"></script>
        </div>
    </div>
</body>
</html>

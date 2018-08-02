
<!DOCTYPE html>
<html>
<head>
    <title>包虫病防控犬驱虫远程管理系统 - 添加管理员</title>
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
    <link rel="stylesheet" type="text/css" href="../css/lib/font-awesome.css" />

    <!-- this page specific styles -->
    <link rel="stylesheet" href="../css/compiled/newuser.css" type="text/css" media="screen" />

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
                <h3 style="color:white;">&nbsp;&nbsp;包虫病防控犬驱虫远程管理系统	</h3>
            </div>
        </div>
    </header>
    <!-- end navbar -->
    <!-- sidebar -->
    <div id="sidebar-nav">
        <ul id="dashboard-menu">

            <li>
                <a id="li_countrysee" style="cursor:pointer">
                    <i class="icon-home"></i>
                    <span id="span_leftscan">总览</span>
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
                <a  style="cursor:pointer" id="a_personalpage">
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
            <li>
                <a style="cursor:pointer" id="goback" href="#" onclick="history.back(-1);return false;">
                    <i class="icon-arrow-left"></i>
                    <span>返回</span>
                </a>
            </li>

        </ul>
    </div>
    <!-- end sidebar -->
    <!-- main container -->
    <div class="content">

        <div id="pad-wrapper" class="new-user">
            <div class="row header">
                <div class="col-md-12">
                    <h3 id="h3_adminname" style="margin-top:30px">添加新的管理员</h3>
                </div>
            </div>

            <div class="row form-wrapper">
                <!-- left column -->
                <div class="col-md-9">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">姓名(*)</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="input_managername">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">家庭住址</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="input_address">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPhone1" class="col-md-2 control-label">所属区域(*)</label>
                            <div class="col-md-8">
                                <select id="select_area"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">身份证号</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="input_identity">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">办公电话</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="input_officecall">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">手机号码</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="input_tel">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputAddress1" class="col-md-2 control-label">用户名(*)</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="input_username">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputAddress1" class="col-md-2 control-label">密码(*)</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="input_password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputAddress1" class="col-md-2 control-label">确认密码(*)</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="input_confirmpassword">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <button id="btn_adduser" type="button" class="btn-glow primary">确认添加</button>
                            </div>
                        </div>
                       
                    </form>
                </div>

            </div>
        </div>
    </div>
    <!-- end main container -->
    <!-- scripts -->
    <script src="../js/jquery-latest.js"></script>
 
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/theme.js"></script>
     <script src="../js/pages/newuser.js" type="text/javascript"> 

    </script>
</body>
</html>
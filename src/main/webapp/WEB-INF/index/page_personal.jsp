
<!DOCTYPE html>
<html>
<head>
    <title>Detail Admin - My Info</title>
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
    <link rel="stylesheet" href="../css/compiled/personal-info.css" type="text/css" media="screen" />

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
            <!--<li id="li_countrysee">
                <a style="cursor:pointer">
                    <i class="icon-home"></i>
                    <span id="span_leftscan">地区分布</span>
                </a>
            </li>
            <li id="li_farmdetail">
                <a style="cursor:pointer" id="a_farmdetail">
                    <i class="icon-picture"></i>
                    <span>牧区详情</span>
                </a>
            </li>-->
            <li>
                <div class="pointer">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <a style="cursor:pointer" id="a_areasee" href="#">
                    <i class="icon-home"></i>
                    <span id="span_leftscan">全国总览</span>
                </a>
            </li>
            <li>
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
            <li class="active">
                <div class="pointer">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
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
    <!-- main container .wide-content is used for this layout without sidebar :)  -->
    <div class="content">
        <div class="settings-wrapper" id="pad-wrapper">

            <div class="row">

                <!-- edit form column -->
                <div class="col-md-7 personal-info">

                    <a id="a_activelist" data-toggle="modal" href="#activelistDiv" class="btn-flat success pull-right" style="margin-right:10px">
                        查看待激活列表
                    </a>

                    <h5 class="personal-title" style="font-style:normal">个人信息</h5>


                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">姓名:</label>
                            <div class="col-lg-8">
                                <input class="form-control" type="text" id="input_managername" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">区域:</label>
                            <div class="col-lg-8">
                                <input class="form-control" readonly="readonly" type="text" id="input_managerarea" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">单位:</label>
                            <div class="col-lg-8">
                                <input class="form-control" type="text" id="input_managerjob" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">身份证:</label>
                            <div class="col-lg-8">
                                <input class="form-control" type="text" id="input_manageridentity" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">办公电话:</label>
                            <div class="col-lg-8">
                                <input class="form-control" type="text" id="input_officecall" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">手机号码:</label>
                            <div class="col-lg-8">
                                <input class="form-control" id="input_managertel" type="text" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">联系地址:</label>
                            <div class="col-lg-8">
                                <input class="form-control" type="text" id="input_manageraddress" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">邮箱:</label>
                            <div class="col-lg-8">
                                <input class="form-control" type="text" id="input_email" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">用户名:</label>
                            <div class="col-lg-8">
                                <input class="form-control" readonly="readonly" type="text" id="input_username" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">密码:</label>
                            <div class="col-lg-8">
                                <input class="form-control" type="password" id="input_password" value="" />
                                <br />
                            </div>
                        </div>
                        <div class="actions">
                            <input type="button" id="btn_modify" class="btn-glow primary" value="编辑修改">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- end main container -->

    <div class="modal fade" id="activelistDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">待激活管理员列表</h4>
                </div>
                <div class="modal-body">
                    <!-- Users table -->
                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-hover table_hide">
                                <thead>
                                    <tr>
                                        <th class="col-md-1">
                                                                                                                                                         用户名
                                        </th>
                                        <th class="col-md-1">
                                            <span class="line"></span>姓名
                                        </th>
                                        <th class="col-md-1">
                                            <span class="line"></span>注册时间
                                        </th>
                                        <th class="col-md-3">
                                            <span class="line"></span>管理地址
                                        </th>
                                        <th class="col-md-2">
                                            <span class="line"></span>手机号码
                                        </th>
                                        <th class="col-md-1">
                                            <span class="line"></span>操作
                                        </th>
                                    </tr>
                                </thead>
                                <tbody id="tbody_pagemanagecommon"></tbody>
                            </table>
                            <div class="col-md-offset-2 col-md-8">
                                <button id="modal_confirmactive" class="btn-flat success pull-right" data-dismiss="modal">确认激活</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!-- scripts -->
    <script src="../js/jquery-latest.js"></script>
 
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/theme.js"></script>
    <script src="../js/pages/pagepersonal.js" type="text/javascript"></script>
</body>
</html>

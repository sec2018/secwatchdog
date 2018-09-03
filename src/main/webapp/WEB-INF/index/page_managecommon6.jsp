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
    <link rel="stylesheet" href="../css/compiled/user-list.css" type="text/css" media="screen" />

    <!-- open sans font -->
 
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
    <style type="text/css">
        .padding5 {
            padding: 5px;
        }
    </style>
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
            <!--<li>
                <a style="cursor:pointer" id="a_farmdetail">
                    <i class="icon-picture"></i>
                    <span>牧区详情</span>
                </a>
            </li>-->
            <li id="li_areasee">
                <a id="a_areasee" style="cursor:pointer">
                    <i class="icon-home"></i>
                    <span id="span_leftscan">地区总览</span>
                </a>
            </li>
            <li class="active">
                <div class="pointer">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <a href="#">
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

        <div id="pad-wrapper" class="users-list">
            <h3 id="h3_adminname" style="margin-top:30px">牧犬管理员</h3>

            <div class="row header">
                <div class="col-md-10 col-sm-12 col-xs-12 pull-left" style="margin-top:30px">
                    <div class="col-xs-5 col-md-2 padding5"><input id="input_managername" type="text" class="col-md-5 search" placeholder="输入设备编号..." style="width:100%; margin-bottom:0;"></div>
                    <div class="col-xs-3 col-md-1 padding5">
                        <a id="div_usersearch" class="btn-flat default text-center" style="width:100%; padding:7px 0;">
                            搜索
                        </a>
                    </div>
      <!--               @*<div class="col-xs-4 col-md-1 padding5">
                        <a id="a_goback" class="btn-flat success text-center" style="width:100%; padding:7px 0;">
                            返回上一级
                        </a>
                    </div>*@ -->
                    <div class="col-xs-4 col-md-2 col-md-offset-1 padding5">
                        <a id="a_dogadd" data-toggle="modal" href="#dogDiv" class="btn-flat success pull-right text-center" style="width:100%; padding:7px 0;">
                            <span>&#43;</span>
                            添加牧犬
                        </a>
                    </div>
                    <div class="col-xs-4 col-md-2 padding5">
                        <a id="a_neckletadd" data-toggle="modal" href="#neckletDiv" class="btn-flat success pull-right text-center" style="width:100%; padding:7px 0;">
                            <span>&#43;</span>
                            添加项圈
                        </a>
                    </div>
                    <div class="col-xs-4 col-md-2 padding5">
                        <a id="a_feederadd" data-toggle="modal" href="#feederDiv" class="btn-flat success pull-right text-center" style="width:100%; padding:7px 0;">
                            <span>&#43;</span>
                            添加喂饲器
                        </a>
                    </div>
                    <div class="col-xs-4 col-md-2 padding5">
                        <a id="a_owneradd" data-toggle="modal" href="#ownerDiv" class="btn-flat success pull-right text-center" style="width:100%; padding:7px 0;">
                            <span>&#43;</span>
                            添加主人
                        </a>
                    </div>
                </div>
            </div>

            <!-- Users table -->
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-hover table_hide2">
                        <thead>
                            <tr>
                                <th class="col-md-1">
                                    设备编号
                                </th>
                                <th class="col-md-1">
                                    <span class="line"></span>犬只名称
                                </th>
                                <th class="col-md-1">
                                    <span class="line"></span>首次投药时间
                                </th>
                                <th class="col-md-1">
                                    <span class="line"></span>上次投药时间
                                </th>
                                <th class="col-md-1">
                                    <span class="line"></span>已投次数
                                </th>
                                <th class="col-md-1">
                                    <span class="line"></span>下次投药时间
                                </th>
                            </tr>
                        </thead>
                        <tbody id="tbody_userprofilefarm"></tbody>
                    </table>
                </div>
            </div>
            <div id="barcon" class="barcon">
                <div id="barcon1" class="barcon1"></div>
                <div id="barcon2" class="barcon2">
                    <ul>
                        <li><a href="###" id="firstPage">首页</a></li>
                        <li><a href="###" id="prePage">上一页</a></li>
                        <li><a href="###" id="nextPage">下一页</a></li>
                        <li><a href="###" id="lastPage">尾页</a></li>
                        <li>
                            <select id="jumpWhere"></select>
                        </li>
                        <li><a href="###" id="jumpPage" onclick="jumpPage()">跳转</a></li>
                    </ul>
                </div>
            </div>
            <!-- end users table -->
        </div>
    </div>
    <!-- end main container -->
    <div class="modal fade" id="dogDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">添加牧犬</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group" style="display:none">
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_dogid" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">犬只名称</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_dogname" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">犬只性别</label>
                            <div class="col-md-8">
                                <select id="modalselect_dogsex">
                                    <option>公狗</option>
                                    <option>母狗</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">所属村</label>
                            <div class="col-md-8">
                                <select id="modalselect_dogbelonghamlet"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">犬只主人</label>
                            <div class="col-md-8">
                                <select id="modalselect_dogownername"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">重量</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_dogweight" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">毛色</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_dogcolor" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">年龄</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_dogage" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">项圈编号</label>
                            <div class="col-md-8">
                                <select id="modalselect_dogneckletid"></select>
                                <a id="a_neckletbind" data-toggle="modal" class="btn-flat success pull-right" style="margin-right:10px">
                                    绑定项圈
                                </a>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">喂饲器编号</label>
                            <div class="col-md-8">
                                <select id="modalselect_dogfeederid"></select>
                                <a id="a_feederbind" data-toggle="modal" class="btn-flat success pull-right" style="margin-right:10px">
                                    绑定喂饲器
                                </a>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <button id="modal_dogadd" class="btn btn-default" data-dismiss="modal">确认添加</button>
                                &nbsp;&nbsp;(注：<span style="color:red">点击绑定项圈或喂饲器会自动添加该牧犬</span>）
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="ownerDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">添加主人</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group" style="display:none">
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_ownerid" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">姓名</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_ownername" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">身份证号</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_owneridentity" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">性别</label>
                            <div class="col-md-8">
                                <select id="modalselect_ownersex">
                                    <option>男</option>
                                    <option>女</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">所属村</label>
                            <div class="col-md-8">
                                <select id="modalselect_ownerhamlet"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">年龄</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_ownerage" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">职业</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_ownerjob" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">家庭住址</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_homeaddress" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">联系方式</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_telphone" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <button id="modal_owneradd" class="btn btn-default" data-dismiss="modal">确认添加</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="neckletDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">添加项圈</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group" style="display:none">
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_ownerid" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">项圈编号</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="neck_modalinput_neckletid" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">预定驱虫数</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="neck_modalinput_medtotal" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">产品系列</label>
                            <div class="col-md-8">
                                <input type="text" readonly="readonly" class="form-control" id="neck_modalinput_category" value="MASHANGHAI01" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <button id="modal_neckletadd" class="btn btn-default" data-dismiss="modal">确认添加</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="feederDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">添加喂饲器</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group" style="display:none">
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="feeder_modalinput_id" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">喂饲器编号</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="feeder_modalinput_feederid" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">预定驱虫数</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="feeder_modalinput_medtotal" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">产品系列</label>
                            <div class="col-md-8">
                                <input type="text" readonly="readonly" class="form-control" id="feeder_modalinput_category" value="一级" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <button id="modal_feederadd" class="btn btn-default" data-dismiss="modal">确认添加</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!-- scripts -->
    <script src="../js/jquery-latest.js"></script>

    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/theme.js"></script>
    <script src="../js/pages/pagemanagecommon6.js" type="text/javascript"></script>
</body>
</html>

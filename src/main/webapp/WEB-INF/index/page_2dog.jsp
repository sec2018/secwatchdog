
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
    <!--<link rel="stylesheet" href="css/compiled/new-user.css" type="text/css" media="screen" />-->
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
    <style type="text/css">
        #VideolistDiv .nopadding {
            padding: 5px;
            text-align: center;
        }

        #VideolistDiv .fblod {
            font-weight: bold;
        }

        #VideolistDiv .mbottom {
            border-bottom: 1px solid #ccc;
        }

        #VideolistDiv .videocom {
            width: 15px;
            height: 15px;
            margin: 0 auto;
            background: url('/Views/img/checkbox-empty.png') no-repeat center;
            -webkit-background-size: cover;
            background-size: cover;
        }

        #VideolistDiv #tbody_videolist .videochiosed {
            background: url('/Views/img/checkbox.png') no-repeat center;
            -webkit-background-size: cover;
            background-size: cover;
        }

        #VideolistDiv #tbody_videolist .row:last-child {
            border: none;
        }

        .close {
            opacity: 5;
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
            <li id="li_countrysee">
                <a style="cursor:pointer">
                    <i class="icon-home"></i>
                    <span id="span_leftscan">地区总览</span>
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

        <div id="pad-wrapper" class="new-user">
            <div class="row header">
                <div class="col-md-12">
                    <h3>牧犬信息</h3>
                </div>
                <a id="a_seevideo" data-toggle="modal" href="#VideolistDiv" class="btn-flat success pull-right" style="margin-right:10px;margin-top:8px;">
                    喂饲器视频
                </a>
            </div>

            <div class="row form-wrapper">
                <!-- left column -->
                <div class="col-md-4 with-sidebar">
                    <form class="form-horizontal" role="form">


                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">犬只名称</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_dogname" value=''>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">犬只性别</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_dogsex" value=''>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">所属村</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_dogbelonghamlet" value=''>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPhone1" class="col-md-2 control-label">犬只主人</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_dogownername" value='' />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">重量</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_dogweight" value=''>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">毛色</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_dogcolor" value=''>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">年龄</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_dogage" value=''>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputAddress1" class="col-md-2 control-label">项圈编号</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_dogneckletid" value='----'>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputAddress1" class="col-md-2 control-label">喂饲器编号</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_dogfeederid" value='----'>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="adminusername" class="col-md-2 control-label">管理员名称</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_adminname" value='' />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">管理员电话</label>
                            <div class="col-md-8" id="input_adminphone" style="padding-top:7px;">

                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <a id="a_dogmodify" data-toggle="modal" href="#dogDiv" class="btn-flat success">
                                    修改牧犬信息
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-4 with-sidebar">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputName1" id="label_id" class="col-md-2 control-label">项圈编号</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_neckletid" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">投药类型</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2type" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">上次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2grantgmt" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">经度</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2log">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">维度</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2lat">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">温度</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2temperature" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">电池电压</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2voltage" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">投药状态</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2status" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">项圈版本</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2swver" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">IP</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2ip" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">Port</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2port" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">投药信息上传周期</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2infoupdatecycle" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">心跳包上传周期</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2tickcycle" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">LED使能</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2ledenable" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">临时投药标志</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2temporaryflag" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">临时投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2temporarygmt" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <a id="a_neckletmodify" data-toggle="modal" href="#collarDiv" class="btn-flat success">
                                    修改项圈信息
                                </a>
                                <a id="a_feedermodify" data-toggle="modal" href="#feederDiv" class="btn-flat success">
                                    修改喂饲器信息
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
                
                <div class="col-md-4 with-sidebar">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第1次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2one" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第2次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2two" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第3次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2three" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第4次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2four" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第5次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2five" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第6次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2six" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第7次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2seven" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第8次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2eight" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第9次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2nine" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第10次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2ten" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第11次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2eleven" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第12次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_2twelve" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <a id="a_necklettimemodify" data-toggle="modal" href="#collartimeDiv" class="btn-flat success">
                                    修改项圈信息
                                </a>
                                <a id="a_feedertimemodify" data-toggle="modal" href="#feedertimeDiv" class="btn-flat success">
                                    修改喂饲器信息
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-4">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">姓名</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_ownername" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">主人身份证（选）</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_owneridentity" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">性别</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_ownersex" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">年龄</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_ownerage" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">职业</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_ownerjob" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">家庭住址(村)</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" readonly="readonly" id="input_homeaddress">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">联系方式</label>
                            <div class="col-md-8" id="input_telphone" style="padding-top:7px;">
                               
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <a id="a_ownermodify" data-toggle="modal" href="#dogownerDiv" class="btn-flat success">
                                    修改主人信息
                                </a>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
    <!-- end main container -->
    <!-- Modal -->
    <div class="modal fade" id="dogDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">修改牧犬信息</h4>
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
                                <!--<input type="text" readonly="readonly" class="form-control" id="modalinput_dogneckletid" />-->
                                <select id="modalselect_dogneckletid">
                                     <option></option> 
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">喂饲器编号</label>
                            <div class="col-md-8">
                                <!--<input type="text" readonly="readonly" class="form-control" id="modalinput_dogneckletid" />-->
                                <select id="modalselect_dogfeederid">
                                     <option></option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <button id="modal_dogmodify" class="btn btn-default" data-dismiss="modal">保存修改</button>
                            <!--     @*&nbsp;&nbsp;(注：<span style="color:red">点击绑定项圈或喂饲器会自动修改该牧犬</span>）*@ -->
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="collarDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">修改项圈信息</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">项圈编号</label>
                            <div class="col-md-8">
                                <input type="text" readonly="readonly" class="form-control" id="modalinput_neckletid" />
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">投药类型</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2type" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">投药信息上传周期</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2infoupdatecycle" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">心跳包上传周期</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2tickcycle" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">LED使能</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2ledenable" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">临时投药标志</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2temporaryflag" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">临时投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2temporarygmt" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <button id="modal_neckletmodify" class="btn btn-default" data-dismiss="modal">保存修改</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="collartimeDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">设定投药时间</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第1次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2one" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第2次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2two" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第3次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2three" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第4次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2four" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第5次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2five" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第6次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2six" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第7次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2seven" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第8次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2eight" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第9次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2nine" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第10次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2ten" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第11次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2eleven" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">第12次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_2twelve" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <button id="modal_necklettimemodify" class="btn btn-default" data-dismiss="modal">保存修改</button>
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
                    <h4 class="modal-title">修改喂饲器信息</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputName1" class="col-md-2 control-label">喂饲器编号</label>
                            <div class="col-md-8">
                                <input type="text" readonly="readonly" class="form-control" id="modalinput_feederid" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail1" class="col-md-2 control-label">电量信息</label>
                            <div class="col-md-8">
                                <input type="text" readonly="readonly" class="form-control" id="modalinput_feederpower" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">剩余药量</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_feedermedleft" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">总驱虫次数</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_feedermedtotal" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">位置反馈周期</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_feederareacycle" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">投药反馈周期</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_feederexhibitcycle" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputWebsite1" class="col-md-2 control-label">初次投药时间</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="modalinput_feederfirstmedtime" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <button id="modal_feedermodify" class="btn btn-default" data-dismiss="modal">保存修改</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="dogownerDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">修改主人信息</h4>
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
                                <button id="modal_ownermodify" class="btn btn-default" data-dismiss="modal">确认修改</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="VideolistDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">喂饲器视频列表</h4>
                </div>
                <div class="modal-body">
                    <!-- Users table -->
                    <div class="row mbottom">
                        <div class="col-xs-3 col-md-3 nopadding fblod">喂饲器编号</div>
                        <div class="col-xs-4 col-md-3 nopadding fblod">视频名称</div>
                        <div class="col-xs-3 col-md-3 nopadding fblod">时间</div>
                        <div class="col-xs-2 col-md-3 nopadding fblod">选择</div>
                    </div>
                    <div class="row" style="height:200px;overflow:auto">
                        <div id="tbody_videolist">
                        </div>
                    </div>
                    <div class="row" style="margin-top:10px;">
                        <div class="col-md-offset-2 col-md-8">
                            <a id="modal_downloadvideo" style="display:none" class="btn-flat success" data-toggle="modal" href="#DownLoadVideoDiv" data-dismiss="modal">下载</a>
                            <a id="modal_seevideo" class="btn-flat success pull-right" data-toggle="modal" href="#SeeVideoDiv" data-dismiss="modal">放映</a>
                        </div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="SeeVideoDiv" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-header">
                <button type="button" class="close" id="close_video" data-dismiss="modal" aria-hidden="true" style="color:white">&times;</button>
                <!--<button type="button" class="close"  aria-hidden="true">&times;</button>-->
                <h5 class="modal-title" style="color:white">视频详情</h5>
            </div>
            <div class="modal-body">
                <div class="modal-content" style="overflow:hidden;">
                    <!--<video id="video" src="" style="left:0;width:100%" webkit-playsinline="true" controls="controls"></video>-->
                    <video id="video" src="" style="position: relative; left: 0; top: 0; width: 100%; display: block" controls="controls"></video>
                </div><!-- /.modal-content -->
            </div>
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!-- scripts -->
    <script src="../js/jquery-latest.js"></script>
 
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/theme.js"></script>
    <script src="../js/html5media.min.js"></script>
    <script src="../js/pages/page2dog.js" type="text/javascript"></script>
</body>
</html>

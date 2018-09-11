<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>包虫病防控犬驱虫远程管理系统 - 登录</title>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- libraries -->
    <link href="css/lib/font-awesome.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/compiled/layout.css" />
    <link rel="stylesheet" type="text/css" href="css/compiled/elements.css" />
    <link rel="stylesheet" type="text/css" href="css/compiled/icons.css" />

    <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/index.css" type="text/css" media="screen" />

    <style>
        .testClass {
            margin-top: 1px !important;
            margin-bottom: 1px !important;
        }

        #mapChart {
            z-index: 1;
        }

            #mapChart .amap-geolocation-con {
                margin-bottom: 35px;
            }

        #basic_info ul, #basic_info li, #bottom_panel ul, #bottom_panel li {
            list-style: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            zoom: 1;
        }

        #basic_info li {
            text-align: center;
            width: 33.33%;
            float: left;
        }

        #bottom_panel li {
            width: 50%;
            float: left;
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
                    <span id="span_leftscan">地区总览</span>
                </a>
            </li>
            <!--<li>
                <a style="cursor:pointer" id="pagereflash">
                    <i class="icon-repeat"></i>
                    <span>刷新</span>
                </a>
            </li>-->
            <li>
                <a href="user/logout.do" style="cursor:pointer">
                    <i class="icon-off"></i>
                    <span>退出</span>
                </a>
            </li>

        </ul>
    </div>
    <!-- end sidebar -->
    <!-- main container -->

    <div class="content">

        <!-- upper main stats -->
        <!-- end upper main stats -->

        <div id="basic_info" style="background-color: white; position: fixed; width: 100%; left: 0px; top: 50px; z-index: 9999; visibility: hidden ">

            <ul id="all_bts">
                <li><a id="route_bt" class="btn btn-default btn-primary active">到这里去</a></li>
                <li><a id="owner_bt" class="btn btn-default btn-primary" data-toggle="modal" href="#contact_div">联系犬主人</a></li>
                <li><a id="manager_bt" class="btn btn-default btn-primary" data-toggle="modal" href="#contact_div">联系犬管理员</a></li>
            </ul>


            <div class="modal fade" id="contact_div" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel"></h4>
                        </div>
                        <div class="modal-body" id="myModelContent">

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-12" id="bottom_panel" style="background-color: white; width: 100%; position: fixed; bottom: 0px; z-index: 9999; visibility: hidden; ">
            <ul>
                <li><a href="#walk_panel" class="btn btn-primary btn-large" data-toggle="collapse" onclick="walkClick()" style="margin-right: 5px; display: block;">步行路线</a></li>
                <li><a href="#drive_panel" class="btn btn-default btn-success" data-toggle="collapse" onclick="driveClick()" style="margin-left: 5px; display: block;">驾车路线</a></li>
            </ul>

            <div id="walk_panel" class="collapse" style="height:0px;">
                <p>展示步行路线的详细信息</p>
            </div>


            <div id="drive_panel" class="collapse" style="height:0px;">
                <p>展示驾车路线的详细信息</p>
            </div>
        </div>
        <div id="mapChart" style='height: 500px;'></div>
    </div>



    <!-- scripts -->
    <script src="js/jquery-latest.js"></script>
    <script src="js/jQuery.fontFlex.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <!--echarts2-->
    <script src="js/other.js"></script>
    <!-- flot charts -->
    <script src="js/theme.js"></script>
    <link rel="stylesheet" href="https://cache.amap.com/lbs/static/main1119.css" />
    
    <link rel="stylesheet" href="css/MyWalkingRender.css" />
    <link rel="stylesheet" href="css/MyDrivingRender.css" />
    <script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.0&key=39f6a5bd38f3167dc032e09b8e6d2a12"></script>

    <script type="text/javascript" src="js/MyWalkingRender.js"></script>
    <script type="text/javascript" src="js/MyDrivingRender.js"></script>

    <script src="https://webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>
    <script src="https://a.amap.com/jsapi_demos/static/remogeo/remogeo.js"></script>
    <script>
        // 自适应调整字体的大小
        $(function () {
            // All elements
            $('body').fontFlex(10, 20, 70);

            $("#pagereflash").click(function () {
                window.location.reload();
            });
        });

        //====================准备工作：第一步，获取各个控件，并初始化各个变量=========================================//
        var pop_panel = document.getElementById("basic_info");
        var bts = document.getElementById("all_bts");
        var route_panel = document.getElementById("route_panel");
        var walk_panel = document.getElementById("walk_panel");
        var drive_panel = document.getElementById("drive_panel");
        var bottom_panel = document.getElementById("bottom_panel");


        var route_bt = document.getElementById("route_bt");
        var owner_bt = document.getElementById("owner_bt");
        var manager_bt = document.getElementById("manager_bt");


        var selectedDogLng = 0;
        var selectedDogLat = 0;


        var selectedDogOwner = "";
        var selectedOwnerPhone = "";
        var selectedDogManger = "";
        var selectedManagerPhone = "";

        var markers = [];

        //============================准备工作：第二步，为各个按钮添加响应，并设置各个控件的高度=======================================================//

        route_bt.addEventListener("click", RouteBTClick);
        owner_bt.addEventListener("click", OwnerBTClick);
        manager_bt.addEventListener("click", ManagerBTClick);

        bottom_panel.style.visibility = "hidden";
        pop_panel.style.visibility = "hidden";


        var panel_width = $('html').width();
        var panel_height = $('html').height();

        document.getElementById("mapChart").style.height = (panel_height - 50) + "px";

        walk_panel.style.maxHeight = (panel_height - 50 - 100 - 60) + "px";
        drive_panel.style.maxHeight = (panel_height - 50 - 100 - 60) + "px";

        walk_panel.style.overflowY = "scroll";
        drive_panel.style.overflowY = "scroll";


        //================准备工作：第三步，引入必要库，包括地图、驾车导航、步行导航================================//

        var map = new AMap.Map("mapChart", {
            resizeEnable: true,
            zoom: 12 //地图显示的缩放级别
        });


        AMapUI.loadUI(['control/BasicControl'], function (BasicControl) {


            var zoomCtrl2 = new BasicControl.Zoom({
                position: 'br',
                showZoomNum: true
            });

            //map.addControl(zoomCtrl1);

            map.addControl(zoomCtrl2);
        });


        var walking = null;
        var walk_render = new Lib.AMap.WalkingRender();


        AMap.plugin('AMap.Walking', function () {

            walking = new AMap.Walking();
            map.plugin(walking);

        });

        var driving = null;
        var drive_render = new Lib.AMap.DrivingRender();

        AMap.plugin('AMap.Driving', function () {

            driving = new AMap.Driving();
            map.plugin(driving);

        });


        var geocoder = null;

        AMap.plugin('AMap.Geocoder', function () {
            geocoder = new AMap.Geocoder({
                //radius: 1000,
                extensions: "all"
            });

            map.plugin(geocoder);
        });

        //===============准备工作：第四步，定义一些全局变量，比如当前的定位位置，查询到的当前位置附近的项圈、喂食器的信息等=============================//


        var current_lng = 0; //当前移动的位置
        var current_lat = 0;

        var now_lng = 0;
        var now_lat = 0;

        var all_necklet_dogs_num = 0;
        var all_feeder_dogs_num = 0;

        var p_necklet_ids = [];
        var p_necklet_Xs = [];
        var p_necklet_Ys = [];
        var p_necklet_titles = [];
        var p_necklet_dognames = [];
        var p_necklet_nextmedtimes = [];
        var p_necklet_mangers = [];
        var p_necklet_managerphones = [];
        var p_necklet_owners = [];
        var p_necklet_ownerphones = [];

        var p_feeder_ids = [];
        var p_feeder_Xs = [];
        var p_feeder_Ys = [];
        var p_feeder_titles = [];
        var p_feeder_dognames = [];
        var p_feeder_times = [];
        var p_feeder_mangers = [];
        var p_feeder_managerphones = [];
        var p_feeder_owners = [];
        var p_feeder_ownerphones = [];
        var p_necklet_healthy = [];

        //===================准备工作：第五步，实现自定义方法的内容======================================//

        //点击“步行路线”按钮的响应
        function walkClick() {

            if (($("#drive_panel").is(":hidden")) && ($("#walk_panel").is(":hidden"))) {
                $("#walk_panel").collapse('toggle');
            } else {
                if (($("#drive_panel").is(":visible")) && ($("#walk_panel").is(":hidden"))) {
                    $("#walk_panel").collapse('toggle');
                    $("#drive_panel").collapse('toggle');
                } else {
                    if (($("#drive_panel").is(":hidden")) && ($("#walk_panel").is(":visible"))) {
                        $("#walk_panel").collapse('toggle');
                    }

                }

            }

        }

        //点击“驾车路线”按钮的响应
        function driveClick() {

            if (($("#drive_panel").is(":hidden")) && ($("#walk_panel").is(":hidden"))) {

                $("#drive_panel").collapse('toggle');

            } else {

                if (($("#drive_panel").is(":visible")) && ($("#walk_panel").is(":hidden"))) {

                    $("#drive_panel").collapse('toggle');


                } else {

                    if (($("#drive_panel").is(":hidden")) && ($("#walk_panel").is(":visible"))) {

                        $("#drive_panel").collapse('toggle');
                        $("#walk_panel").collapse('toggle');

                    }

                }

            }

        }

        //在当前位置添加标识
        function addNowMarker() {

            //alert(current_lng);
            var current_marker = new AMap.Marker({
                position: [now_lng, now_lat]

            });

            current_marker.setMap(map);

        }

        //在地图上添加项圈标记
        function addNeckletMarker(point, dogid, owner, ownerphone, manager, managerphone,
                                neckletid, dogname, nextmedtimes, healthy) {
            var healthyflag = "img/necklet_3.ico";
            if (healthy == 0) {
                healthyflag = "img/bugnecklet.gif";
            }
            marker = new AMap.Marker({
                
                icon: healthyflag,
                //position: [116.41, 39.91]
                position: point,
            });


            var content = "<b>牧犬信息：" + dogid + "</b>";
            marker.content = content;
            //marker.on('click', markerClick);


            marker.on('click', function (e) {

                pop_panel.style.visibility = "visible";
                selectedDogLng = e.target.getPosition().lng;
                selectedDogLat = e.target.getPosition().lat;


                selectedDogOwner = owner;
                selectedOwnerPhone = ownerphone;
                selectedDogManger = manager;
                selectedManagerPhone = managerphone;


            });


            marker.setMap(map);

            return marker;
        }


        //在地图上添加喂食器标记
        function addFeederMarker(point, dogid, owner, ownerphone, manager, managerphone,
                                neckletid, dogname, nextmedtimes) {
            //function addNeckletMarker(point, content) {
            marker = new AMap.Marker({

                icon: "img/feeder_2.ico",
                //position: [116.41, 39.91]
                position: point,
            });

            var content = "<b>牧犬信息：" + dogid + "</b>";
            marker.content = content;
            //marker.on('click', markerClick);

            marker.on('click', function (e) {

                pop_panel.style.visibility = "visible";
                selectedDogLng = e.target.getPosition().lng;
                selectedDogLat = e.target.getPosition().lat;

                selectedDogOwner = owner;
                selectedOwnerPhone = ownerphone;
                selectedDogManger = manager;
                selectedManagerPhone = managerphone;

            });



            marker.setMap(map);

            return marker;
        }

        //===============开始执行：第一步，定位当前位置=========================//

        map.plugin('AMap.Geolocation', function () {
            geolocation = new AMap.Geolocation({
                enableHighAccuracy: true,//是否使用高精度定位，默认:true
                noIpLocate: 0,
                noGeoLocation: 0,
                userNative: true,
                timeout: 15000,          //超过10秒后停止定位，默认：无穷大
                buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                zoomToAccuracy: true,      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
                buttonPosition: 'LB'
            });
            map.addControl(geolocation);
            //alert("开始定位");
            geolocation.getCurrentPosition();
            AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
            AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
        });
        //解析定位结果
        function onComplete(data) {
            var str = ['定位成功'];
            //alert("定位成功");
            current_lng = data.position.getLng();
            current_lat = data.position.getLat();

            now_lng = data.position.getLng();
            now_lat = data.position.getLat();


            str.push('经度：' + current_lng);
            str.push('纬度：' + current_lat);

            addNowMarker();
            GetDevices();

        }

        //解析定位错误信息
        function onError(data) {
            alert(data.message);
        }

        //===============开始执行：第二步，将当前位置发送给后台，取回项圈和喂食器位置，并显示=============================//

        var count = 0;
        var XMLHttpReq;
        var t1; // 用来作超时处理
        function createXMLHttpRequest() {
            try {
                XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");//IE高版本创建XMLHTTP
            }
            catch (E) {
                try {
                    XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");//IE低版本创建XMLHTTP
                }
                catch (E) {
                    XMLHttpReq = new XMLHttpRequest();//兼容非IE浏览器，直接创建XMLHTTP对象
                }
            }
        }

        function connecttoFail() {
            if (XMLHttpReq)
                XMLHttpReq.abort();
            // alert ('Time out');
        }

        function sendAjaxRequest(url) {
            createXMLHttpRequest();                                //创建XMLHttpRequest对象
            XMLHttpReq.open("post", url, true);
            XMLHttpReq.onreadystatechange = processResponse; //指定响应函数
            XMLHttpReq.send(null);
            t1 = setTimeout(connecttoFail, 60000);
        }
        //回调函数
        function processResponse() {
            if (XMLHttpReq.readyState == 4) {
                if (XMLHttpReq.status == 200) {
                    var data = XMLHttpReq.responseText;
                    /**
                        *实现回调
                        */
                    //alert("success");
                    if (t1) {
                        clearTimeout(t1);
                    }
                    data = eval("(" + data + ")");
                    map.remove(markers);
                    walk_render.clearOverlays();
                    drive_render.clearOverlays();
                    //==================查询到需要显示的结果，显示各个Marker，并添加导航的响应函数============================//
                    all_necklet_dogs_num = data.data1.length;
                    all_feeder_dogs_num = data.data2.length;


                    if (all_necklet_dogs_num == 0) {
                        alert("附近没有发现佩戴项圈的牧犬");
                    }
                    if (all_feeder_dogs_num == 0) {
                        alert("附近没有发现喂饲器");
                    }
                    if ((all_necklet_dogs_num != 0) || (all_feeder_dogs_num != 0)) {

                        $.each(data.data1, function (i, n) {
                            //alert("进入data");
                            //alert(n["lng"]);
                            p_necklet_Xs[i] = n["lng"];
                            //p_Xs[i] = data.data3[n].Lng;
                            //alert("X=" + p_Xs[i]);
                            p_necklet_Ys[i] = n["lat"];
                            //p_Ys[i] = data.data3[n].Lat;


                            p_necklet_ids[i] = n["neckletid"];
                            p_necklet_titles[i] = n["dogid"];
                            p_necklet_dognames[i] = n["dogname"];
                            //p_figs[i] = n["fig"];
                            p_necklet_nextmedtimes[i] = n["nextmedtime"];

                            p_necklet_owners[i] = n["ownername"];
                            p_necklet_ownerphones[i] = n["ownerphone"];
                            p_necklet_mangers[i] = n["managername"];
                            p_necklet_managerphones[i] = n["managerphone"];
                            p_necklet_healthy[i] = n["healthy"];

                            markers.push(addNeckletMarker([p_necklet_Xs[i], p_necklet_Ys[i]], p_necklet_titles[i], p_necklet_owners[i], p_necklet_ownerphones[i],
                                p_necklet_mangers[i], p_necklet_managerphones[i], p_necklet_ids[i], p_necklet_dognames[i], p_necklet_nextmedtimes[i], p_necklet_healthy[i]));


                        });
                        $.each(data.data2, function (i, n) {
                            //alert("进入data");
                            //alert(n["lng"]);
                            p_feeder_Xs[i] = n["lng"];
                            //p_Xs[i] = data.data3[n].Lng;
                            //alert("X=" + p_Xs[i]);
                            p_feeder_Ys[i] = n["lat"];
                            //p_Ys[i] = data.data3[n].Lat;


                            p_feeder_ids[i] = n["feederid"];
                            p_feeder_titles[i] = n["dogid"];
                            p_feeder_dognames[i] = n["dogname"];
                            //p_figs[i] = n["fig"];
                            p_feeder_times[i] = n["nextmedtime"];


                            p_feeder_owners[i] = n["ownername"];
                            p_feeder_ownerphones[i] = n["ownerphone"];
                            p_feeder_mangers[i] = n["managername"];
                            p_feeder_managerphones[i] = n["managerphone"];

                            markers.push(addFeederMarker([p_feeder_Xs[i], p_feeder_Ys[i]], p_feeder_titles[i], p_feeder_owners[i], p_feeder_ownerphones[i],
                                p_feeder_mangers[i], p_feeder_managerphones[i], p_feeder_ids[i], p_feeder_dognames[i], p_feeder_times[i]));


                        });

                        map.setFitView();
                        //map.setZoom(12);
                    }
                }
            }
        }



        function GetDevices() {

            var current_position = {};
            current_position.x = current_lng;
            current_position.y = current_lat;

            sendAjaxRequest("guest/api?lng=" + current_lng + "&lat=" + current_lat);
        };


        //===============开始执行：第三步，前端点击牧犬的响应函数，包括以下功能：获取牧犬基本信息，步行路线规划，获取步行距离和时间，弹出框显示=============================//


        function OwnerBTClick() {
            //alert("点击主人按钮");

            //alert(selectedDogOwner);
            //alert(selectedManagerPhone);
            document.getElementById("myModalLabel").innerHTML = "联系牧犬主人";
            document.getElementById("myModelContent").innerHTML = "<h5>牧犬主人：" + selectedDogOwner + "</h5><br><h5>联系方式：<a href=\"tel:" + selectedOwnerPhone + "\">" + selectedOwnerPhone + "</a></h5>";

        }


        function ManagerBTClick() {

            document.getElementById("myModalLabel").innerHTML = "联系牧犬管理员";
            document.getElementById("myModelContent").innerHTML = "<h5>牧犬管理员：" + selectedDogManger + "</h5><br><h5>联系方式：<a href=\"tel:" + selectedManagerPhone + "\">" + selectedManagerPhone + "</a></h5>";

        }


        function RouteBTClick() {
            //alert(e.target.content);


            //alert(selectedDogLat);

            pop_panel.style.visibility = "visible";

            bottom_panel.style.visibility = "visible";

            var distance = 0;
            var spendtime = 0;
            var one_content = "";


            //在panel位置显示路线信息
            //document.getElementById("panel").innerHTML = "";

            walk_panel.innerHTML = "";


            //walking.search([current_lng, current_lat], [e.target.getPosition().lng, e.target.getPosition().lat]);

            walking.search([current_lng, current_lat], [selectedDogLng, selectedDogLat], function (status, result) {

                if (status === 'complete') {

                    walk_render.autoRender({
                        data: result,
                        map: map,
                        //panel: "walk_panel",
                        hideMarkers: true
                    });


                    distance = result.routes[0].distance;
                    spendtime = Math.round(result.routes[0].time / 60);

                    var walk_path = "";

                    walk_path = "<b>距离：" + distance + "米，耗时约" + spendtime + "分钟</b><hr  class=\"testClass\">";

                    var walk_routes = result.routes[0];
                    for (var t = 0; t < walk_routes.steps.length; t++) {

                        //alert(walk_routes.steps[t].instruction);
                        walk_path += walk_routes.steps[t].instruction + "<hr  class=\"testClass\">";

                    }
                    walk_panel.innerHTML = walk_path;

                }
            });





            drive_panel.innerHTML = "";


            driving.search([current_lng, current_lat], [selectedDogLng, selectedDogLat], function (status, result) {

                if (status === 'complete') {

                    drive_render.autoRender({
                        data: result,
                        map: map,
                        //panel: "drive_panel",
                        hideMarkers: true
                    });



                    distance = result.routes[0].distance;
                    spendtime = Math.round(result.routes[0].time / 60);

                    var drive_path = "";
                    drive_path = "<b>距离：" + distance + "米，耗时约" + spendtime + "分钟</b><hr  class=\"testClass\">";

                    var drive_routes = result.routes[0];
                    for (var t = 0; t < drive_routes.steps.length; t++) {

                        //alert(walk_routes.steps[t].instruction);
                        drive_path += drive_routes.steps[t].instruction + "<hr  class=\"testClass\">";

                    }
                    drive_panel.innerHTML = drive_path;

                }
            });
        }



        var clickEventListener = map.on('click', function (e) {

            pop_panel.style.visibility = "hidden";
            bottom_panel.style.visibility = "hidden";


        });
    </script>
</body>
</html>
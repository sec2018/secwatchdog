var num = data.total;
$(function(){
    if (data.data1.privilegelevel == 6) {
        $("#a_areasee").click(function () {
            //window.location.href = "page_farmlevel6.html";
            window.location.href = "../user/index.do";
        });

        var modalselect_dogbelonghamlet = document.getElementById("modalselect_dogbelonghamlet");
        var modalselect_ownerhamlet = document.getElementById("modalselect_ownerhamlet");

      //  for (var i = 0; i < data.data3.length; i++) {
            //遍历后台传回的结果，一项项往select中添加option
            modalselect_dogbelonghamlet.options.add(new Option(data.data1.hamlet, data.data1.districtcode));
            modalselect_ownerhamlet.options.add(new Option(data.data1.hamlet, data.data1.districtcode));
       // }
        var modalselect_dogownername = document.getElementById("modalselect_dogownername");
        data.data4 = objToArray(data.data4);
        for (var i = 0; i < data.data4.length; i++) {
            //遍历后台传回的结果，一项项往select中添加option
            modalselect_dogownername.options.add(new Option(data.data4[i].ownername, data.data4[i].ownerid));
        }
        var modalselect_dogneckletid = document.getElementById("modalselect_dogneckletid");
        data.data7 = objToArray(data.data7);
        for (var i = 0; i < data.data7.length; i++) {
            //遍历后台传回的结果，一项项往select中添加option
            modalselect_dogneckletid.options.add(new Option(data.data7[i].neckletid, data.data7[i].necid));
        }
        var modalselect_dogfeederid = document.getElementById("modalselect_dogfeederid");
        data.data9 = objToArray(data.data9);
        for (var i = 0; i < data.data9.length; i++) {
            //遍历后台传回的结果，一项项往select中添加option
            modalselect_dogfeederid.options.add(new Option(data.data9[i].feederid, data.data9[i].id));
        }
    } 
//        else if (data.data1.privilegelevel == 5) {
//        $("#span_leftscan").html(data.data1.village + "总览");
//        $("#a_areasee").click(function () {
//            window.location.href =  "../user/index.do";
//        });
//    } else if (data.data1.privilegelevel == 4) {
//        $("#span_leftscan").html(data.data1.county + "总览");
//        $("#a_areasee").click(function () {
//            window.location.href =  "../user/index.do";
//        });
//    } else if (data.data1.privilegelevel == 3) {
//        $("#span_leftscan").html(data.data1.city + "总览");
//        $("#a_areasee").click(function () {
//            window.location.href =  "../user/index.do";
//        });
//    } else if (data.data1.privilegelevel == 2) {
//        $("#span_leftscan").html(data.data1.province + "总览");
//        $("#a_areasee").click(function () {
//            window.location.href =  "../user/index.do";
//        });
//    } else if (data.data1.privilegelevel == 1) {
//        $("#span_leftscan").html("全国总览");
//        $("#a_areasee").click(function () {
//            window.location.href =  "../user/index.do";
//        });
//    } else {
//
//    }

    $("#h3_adminname").html("牧犬管理员(" + data.data1.hamlet + ")");

    var html = "";
    var firstrealtime = "";
    var lastrealtime = "";
    var nextrealtime = "";
    data.data2 = objToArray(data.data2);
    for (var i = 0; i < data.data2.length; i++) {
        firstrealtime = ChangeTimeFormat(data.data2[i].firstmedtime).split(" ")[0];
        lastrealtime = ChangeTimeFormat(data.data2[i].lastmed).split(" ")[0];
        nextrealtime = ChangeTimeFormat(data.data2[i].nextmed).split(" ")[0];
        html += "<tr><td><a class=\"neckletid\" style=\"cursor:pointer;\" id=\"" + data.data2[i].dogid + "\">" + data.data2[i].neckletid + "</a></td><td>" + data.data2[i].dogname + "</td><td>" + firstrealtime + "</td><td>" + lastrealtime + "</td><td>" + data.data2[i].timemed + "</td><td>" + nextrealtime + "</td></tr>";
    }
 
    $("#tbody_userprofilefarm").append(html);


    $("#div_usersearch").click(function () {
        var neckletid = $("#input_managername").val();
        var searchhtml = "";
        if ($.trim(neckletid) == "") {
     
        	goPage(1,8);
        } else {
        	
        	$.ajax({
        	    url: "../hamlet/CombineNeckletAndFeederDog.do",
        	    type: "POST",
        	    data: {"neckletId": neckletid},
        	   // data: JSON.stringify({"page":page}),
        	   // contentType: "application/json",
        	    success: function (data) {
        	        if (data == "") {
        	            window.location.href = "../login.jsp";
        	            return;
        	        } else {
        	            data = eval("(" + data + ")");

        	            $("#tbody_userprofilefarm").empty();
        	            //document.getElementById("barcon").innerHTML = pagecode;

        	            $(".barcon").empty();
        	            
        	            var html = "";
        	            var firstrealtime = "";
        	            var lastrealtime = "";
        	            var nextrealtime = "";

        	            data = objToArray(data);
        	            for (var i = 0; i < data.length; i++) {
        	                firstrealtime = ChangeTimeFormat(data[0].firstmedtime).split(" ")[0];
        	                lastrealtime = ChangeTimeFormat(data[0].lastmed).split(" ")[0];
        	                nextrealtime = ChangeTimeFormat(data[0].nextmed).split(" ")[0];
        	                searchhtml += "<tr><td><a class=\"neckletid\" style=\"cursor:pointer;\" id=\"" + data[0].dogid + "\">" + data[0].neckletid + "</a></td><td>" + data[0].dogname + "</td><td>" + firstrealtime + "</td><td>" + lastrealtime + "</td><td>" + data[0].timemed + "</td><td>" + nextrealtime + "</td></tr>";
        	            }
        	            
        	            $("#tbody_userprofilefarm").append(searchhtml);


                        $(".neckletid").click(function () {
                            GetDogPage(this.id);
                        });
                        }
        	    }
        	})

        }
    });

    Page(1, 8);
    var tempOption = "";
    for (var i = 1; i <= totalPage; i++) {
        tempOption += '<option value=' + i + '>' + i + '</option>'
    }
    $("#jumpWhere").html(tempOption);

    $(".neckletid").click(function () {
        GetDogPage(this.id);
    });

    $(".dogname").click(function () {
        //setCookie("user_profile_dogid", this.id, "s6000");
        window.location.href = "/Index/PageDog6?dogid=" + this.id;
    });

}

);
function objToArray(array) {
    var arr = []
    for (var i in array) {
        arr.push(array[i]); 
    }
    return arr;
}
 


/*function nameOnClick(id, privilegelevel) {
    switch (privilegelevel) {
        case 6:
            //alert(id);
            //setCookie("page_farmusername_6", id, "s6000");
            window.location.href = "/Index/UserProfile?viewuser=" + id;
            break;
        default:
            window.location.href = "/Index/UserProfile?viewuser=" + id;
    }
}*/

function GetDogPage(id) {
    //setCookie("user_profile_dogid", id, "s6000");
    window.location.href = "../pageManageCommon/pagedog?dogid="+id;
}

function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

function ChangeTimeFormat(logintime) {
    if (logintime == "") {
        return "----";
    }
    //	20170926084552 ---> 2017.09.26 08:45:52
    var year = logintime.substring(0, 4);
    var month = logintime.substring(4, 6);
    var day = logintime.substring(6, 8);
    var hour = logintime.substring(8, 10);
    var min = logintime.substring(10, 12);
    var sec = logintime.substring(12);
    return year + "." + month + "." + day + " " + hour + ":" + min + ":" + sec;
}

//如果需要设定自定义过期时间
//那么把上面的setCookie　函数换成下面两个函数就ok;
//程序代码
function setCookie(name, value, time) {
    var strsec = getsec(time);
    var exp = new Date();
    exp.setTime(exp.getTime() + strsec * 1);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
function getsec(str) {
    var str1 = str.substring(1, str.length) * 1;
    var str2 = str.substring(0, 1);
    if (str2 == "s") {
        return str1 * 1000;
    }
    else if (str2 == "h") {
        return str1 * 60 * 60 * 1000;
    }
    else if (str2 == "d") {
        return str1 * 24 * 60 * 60 * 1000;
    }
}


$(function () {
    $("#modal_dogadd").click(function () {
        var clicktype = "dogadd";
        var dogname = $("#modalinput_dogname").val();
        var dogsex = $("#modalselect_dogsex").find("option:selected").text();
        var dogbelonghamlet = $("#modalselect_dogbelonghamlet").find("option:selected").text();
        var dogownerid = $("#modalselect_dogownername").val();
        var dogweight = $("#modalinput_dogweight").val();
        var dogcolor = $("#modalinput_dogcolor").val();
        var dogage = $("#modalinput_dogage").val();
        var senddata = {};
        senddata.clicktype = clicktype;
        senddata.username = data.data1.username;
        senddata.dogname = dogname;
        senddata.dogsex = dogsex;
        senddata.dogbelonghamlet = dogbelonghamlet;
        senddata.ownerhamletcode = data.data1.districtcode;
        senddata.dogownerid = dogownerid;
        senddata.dogweight = dogweight;
        senddata.dogcolor = dogcolor;
        senddata.dogage = dogage;
        $.ajax({
            url:  "../pageManageCommon/pagedogapi.do",
            type: "POST",
            data:  JSON.stringify(senddata),
            contentType: "application/json",
            success: function (data) {
            	if (data == "") {
    	            window.location.href = "../login.jsp";
    	            return;
    	        }
                alert(data);
                window.location.reload();
            }
        })
    });

    $("#a_neckletbind").click(function () {
        var clicktype = "bindnecklet";
        var dogname = $("#modalinput_dogname").val();
        var dogsex = $("#modalselect_dogsex").find("option:selected").text();
        var dogbelonghamlet = $("#modalselect_dogbelonghamlet").find("option:selected").text();
        var dogownerid = $("#modalselect_dogownername").val();
        var dogweight = $("#modalinput_dogweight").val();
        var dogcolor = $("#modalinput_dogcolor").val();
        var dogage = $("#modalinput_dogage").val();
        var dogneckletid = $("#modalselect_dogneckletid").find("option:selected").text();
        var senddata = {};
        senddata.clicktype = clicktype;
        senddata.username = data.data1.username;
        senddata.dogname = dogname;
        senddata.dogsex = dogsex;
        senddata.dogbelonghamlet = dogbelonghamlet;
        senddata.ownerhamletcode = data.data1.districtcode;
        senddata.dogownerid = dogownerid;
        senddata.dogweight = dogweight;
        senddata.dogcolor = dogcolor;
        senddata.dogage = dogage;
        senddata.dogneckletid = dogneckletid;
        $.ajax({
            url:  "../pageManageCommon/pagedogapi.do",
            type: "POST",
            data: JSON.stringify(senddata),
            contentType: "application/json",
            success: function (data) {
            	if (data == "") {
    	            window.location.href = "../login.jsp";
    	            return;
            	}
                if (data == "success") {
                    alert("绑定成功！");
                    $("#dogDiv").modal('hide');
                    window.location.reload();
                } else {
                    alert("绑定失败！");
                }
            }
        })
    });

    $("#a_feederbind").click(function () {
        var clicktype = "bindfeeder";
        var dogname = $("#modalinput_dogname").val();
        var dogsex = $("#modalselect_dogsex").find("option:selected").text();
        var dogbelonghamlet = $("#modalselect_dogbelonghamlet").find("option:selected").text();
        var dogownerid = $("#modalselect_dogownername").val();
        var dogweight = $("#modalinput_dogweight").val();
        var dogcolor = $("#modalinput_dogcolor").val();
        var dogage = $("#modalinput_dogage").val();
        var dogfeederid = $("#modalselect_dogfeederid").find("option:selected").text();
        var senddata = {};
        senddata.clicktype = clicktype;
        senddata.username = data.data1.username;
        senddata.dogname = dogname;
        senddata.dogsex = dogsex;
        senddata.dogbelonghamlet = dogbelonghamlet;
        senddata.ownerhamletcode = data.data1.districtcode;
        senddata.dogownerid = dogownerid;
        senddata.dogweight = dogweight;
        senddata.dogcolor = dogcolor;
        senddata.dogage = dogage;
        senddata.dogfeederid = dogfeederid;
        $.ajax({
            url: "../pageManageCommon/pagedogapi.do",
            type: "POST",
            data: JSON.stringify(senddata),
            contentType: "application/json",
            success: function (data) {
            	if (data == "") {
    	            window.location.href = "../login.jsp";
    	            return;
            	}
                if (data == "success") {
                    alert("绑定成功！");
                    $("#dogDiv").modal('hide');
                    window.location.reload();
                } else {
                    alert("绑定失败！");
                }
            }
        })
    });

    $("#modal_owneradd").click(function () {
        var clicktype = "owneradd";
        var ownername = $("#modalinput_ownername").val();
        var owneridentity = $("#modalinput_owneridentity").val();
        var ownersex = $("#modalselect_ownersex").find("option:selected").text();
        var ownerhamlet = $("#modalselect_ownerhamlet").find("option:selected").text();
        var ownerage = $("#modalinput_ownerage").val();
        var ownerjob = $("#modalinput_ownerjob").val();
        var homeaddress = $("#modalinput_homeaddress").val();
        var telphone = $("#modalinput_telphone").val();
        var senddata = {};
        senddata.clicktype = clicktype;
        senddata.ownername = ownername;
        senddata.owneridentity = owneridentity;
        senddata.ownersex = ownersex;
        senddata.ownerhamlet = ownerhamlet;
        senddata.ownerhamletcode = data.data1.districtcode;
        senddata.ownerage = ownerage;
        senddata.ownerjob = ownerjob;
        senddata.homeaddress = homeaddress;
        senddata.telphone = telphone;
        $.ajax({
            url: "../pageManageCommon/pagedogapi.do",
            type: "POST",
            data: JSON.stringify(senddata),
            contentType: "application/json",
            success: function (data) {
            	if (data == "") {
    	            window.location.href = "../login.jsp";
    	            return;
            	}
                alert(data);
                window.location.reload();
            }
        })
    });

    $("#modal_neckletadd").click(function () {
        var clicktype = "neckletadd";
        var neckletid = $("#neck_modalinput_neckletid").val();
        var medtotal = $("#neck_modalinput_medtotal").val();
        var category = $("#neck_modalinput_category").val();
        var senddata = {};
        senddata.clicktype = clicktype;
        senddata.neckletid = neckletid;
        senddata.medtotal = medtotal;
        senddata.category = category;
        senddata.username = data.data1.username;
        $.ajax({
            url:  "../pageManageCommon/pagedogapi.do",
            type: "POST",
            data: JSON.stringify(senddata),
            contentType: "application/json",
            success: function (data) {
            	if (data == "") {
    	            window.location.href = "../login.jsp";
    	            return;
            	}
                alert(data);
                window.location.reload();
            }
        })
    });

    $("#modal_feederadd").click(function () {
        var clicktype = "feederadd";
        var feederid = $("#feeder_modalinput_feederid").val();
        var medtotal = $("#feeder_modalinput_medtotal").val();
        var category = $("#feeder_modalinput_category").val();
        var senddata = {};
        senddata.clicktype = clicktype;
        senddata.feederid = feederid;
        senddata.medtotal = medtotal;
        senddata.category = category;
        senddata.username = data.data1.username;
        $.ajax({
            url: "../pageManageCommon/pagedogapi.do",
            type: "POST",
            data: JSON.stringify(senddata),
            contentType: "application/json",
            success: function (data) {
            	if (data == "") {
    	            window.location.href = "../login.jsp";
    	            return;
            	}
                alert(data);
                window.location.reload();
            }
        })
    });

})

/**
 * 分页函数 pno--页数 psize--每页显示记录数 分页部分是从真实数据行开始，因而存在加减某个常数，以确定真正的记录数
 * 纯js分页实质是数据行全部加载，通过是否显示属性完成分页功能
 */

var pageSize = 0;// 每页显示行数
var currentPage_ = 1;// 当前页全局变量，用于跳转时判断是否在相同页，在就不跳，否则跳转。
var totalPage;// 总页数
function Page(pno, psize) {

    pageSize = psize;// 每页显示行数
    // 总共分几页
    if (num / pageSize > parseInt(num / pageSize)) {
        totalPage = parseInt(num / pageSize) + 1;
    } else {
        totalPage = parseInt(num / pageSize);
    }
    var currentPage = pno;// 当前页数
    currentPage_ = currentPage; 
    
    var tempStr = "共" + num + "条记录 分" + totalPage + "页 当前第" + currentPage + "页";
    document.getElementById("barcon1").innerHTML = tempStr;

    if (currentPage > 1) {
        $("#firstPage").on("click", function () {
        	 goPage(1, psize);

        }).removeClass("ban");
        $("#prePage").on("click", function () {
        	 goPage(currentPage_-1, psize);
        }).removeClass("ban");
    } else {
        $("#firstPage").off("click").addClass("ban");
        $("#prePage").off("click").addClass("ban");
    }

    if (currentPage < totalPage) {
        $("#nextPage").on("click", function () {
        	goPage(currentPage_+1 , psize);
        }).removeClass("ban")
        $("#lastPage").on("click", function () {
        	goPage(totalPage, psize);
        }).removeClass("ban")
    } else {
        $("#nextPage").off("click").addClass("ban");
        $("#lastPage").off("click").addClass("ban");
    }
    $("#jumpPage").on("click", function(){
    	jumpPage();
    })
   
 
}

function jumpPage() {
    var toPage = parseInt($("#jumpWhere").val());
    if (toPage != currentPage_) {
    	goPage(toPage, pageSize);
    }
}

function goPage(startItem, psize){

    $.ajax({
        url: "../pageManageCommon/hamletManagerApi.do",
        type: "POST",
        data: JSON.stringify({'startItem': startItem, 'pageSize': psize}),                    
        contentType: "application/json",
        success: function (data) {
        	if (data == "") {
	            window.location.href = "../login.jsp";
	            return;
        	}
            data = eval("(" + data + ")");
            data.data2 = objToArray(data.data2);
            var html = "";
            var firstrealtime = "";
            var lastrealtime = "";
            var nextrealtime = "";
            for (var i = 0; i < data.data2.length; i++) {
                firstrealtime = ChangeTimeFormat(data.data2[i].firstmedtime).split(" ")[0];;
                lastrealtime = ChangeTimeFormat(data.data2[i].lastmed).split(" ")[0];;
                nextrealtime = ChangeTimeFormat(data.data2[i].nextmed).split(" ")[0];;
                html += "<tr><td>" + data.data2[i].neckletid + "</td><td><a class=\"neckletid\" style=\"cursor:pointer;\" id=\"" + data.data2[i].dogid + "\">" + data.data2[i].dogname + "</a></td><td>" + firstrealtime + "</td><td>" + lastrealtime + "</td><td>" + data.data2[i].timemed + "</td><td>" + nextrealtime + "</td></tr>";

            }
            $("#tbody_userprofilefarm").html(html);
            num = data.total;
            Page(startItem, psize);
            var tempOption = "";
            for (var i = 1; i <= totalPage; i++) {
                tempOption += '<option value=' + i + '>' + i + '</option>'
            }
            $("#jumpWhere").html(tempOption);
            document.getElementById("jumpWhere").options[startItem-1].selected = true;
        }
    });
} 

$(function () {
    $("#pagereflash").click(function () {
        window.location.reload();
    });
})
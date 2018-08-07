var num = data.total;
var username_userprofile;
$(function(){
	username_userprofile = data.data1.username;
	if (data.data3.privilegelevel == 1) {
        $("#span_leftscan").html("全国总览");
        $("#li_countrysee").click(function () {
            window.location.href = "../user/index.do";
        });
    }
    else if (data.data3.privilegelevel == 2) {
        $("#span_leftscan").html(data.data3.province + "总览");
        $("#li_countrysee").click(function () {
            window.location.href = "../province/province.do?province=" + data.data3.province;
             });
    } else if (data.data3.privilegelevel == 3) {
        $("#span_leftscan").html(data.data3.city + "总览");
        $("#li_countrysee").click(function () {
        	 window.location.href = "../city/city.do?city=" + data.data3.city + "&province=" + data.data3.province ;
              });
    } else if (data.data3.privilegelevel == 4) {
        $("#span_leftscan").html(data.data3.county + "总览");
        $("#li_countrysee").click(function () {
            window.location.href = "../county/county.do?county=" + data.data3.county + "&city=" + data.data3.city + "&province=" + data.data3.province;
              });
    } else if (data.data3.privilegelevel == 5) {
        $("#span_leftscan").html(data.data3.village + "总览");
        $("#li_countrysee").click(function () {
            window.location.href = "../village/village.do?village=" + data.data3.village + "&county=" + data.data3.county + "&city=" + data.data3.city + "&province=" + data.data3.province;
              });
    } else {
        $("#span_leftscan").html("地区总览");
        $("#li_countrysee").click(function () {
         //   window.location.href = "../user/index.do";
        });
    }


           
    $("#h3_managername").html(data.data1.managername);
    $("#span_adminlevel").text("村级管理员");
    $("#td_hamlet").text(data.data1.hamlet);
    $("#td_job").text(data.data1.job);
    $("#td_chargehamlet").text(data.data1.chargehamlet);
    $("#td_address").text(data.data1.address);
    $("#td_officecall").text(data.data1.officecall);
    $("#td_dogtotal").text(data.data4.dogtotalnum);
    $("#td_neckleted").text(data.data4.neckletedtotal);
    //$("#td_telphone").text(data.data1.telphone);
    $("#td_telphone").html("<a href=\"tel:" + data.data1.telphone + "\">" + data.data1.telphone + "</a>");
    $("#td_adminstatus").text(data.data1.adminstatus);
    if (data.data1.adminstatus == "已激活") {
        $("#a_activeadmin").text("账户冻结");
    } else {
        $("#a_activeadmin").text("账户激活");
    }


    var html = "";
    var firstrealtime = "";
    var lastrealtime = "";
    var nextrealtime = "";
    data.data2 = objToArray(data.data2)
    for (var i = 0; i < data.data2.length; i++) {
        firstrealtime = ChangeTimeFormat(data.data2[i].firstmedtime).split(" ")[0];;
        lastrealtime = ChangeTimeFormat(data.data2[i].lastmed).split(" ")[0];;
        nextrealtime = ChangeTimeFormat(data.data2[i].nextmed).split(" ")[0];;
        html += "<tr><td>" + data.data2[i].neckletid + "</td><td><a class=\"neckletid\" style=\"cursor:pointer;\" id=\"" + data.data2[i].dogid + "\">" + data.data2[i].dogname + "</a></td><td>" + firstrealtime + "</td><td>" + lastrealtime + "</td><td>" + data.data2[i].timemed + "</td><td>" + nextrealtime + "</td></tr>";

    }
/*    for (var i = 0; i < data.data5.length; i++) {
        firstrealtime = ChangeTimeFormat(data.data5[i].firstmedtime).split(" ")[0];
        lastrealtime = ChangeTimeFormat(data.data5[i].lastmed).split(" ")[0];
        nextrealtime = ChangeTimeFormat(data.data5[i].nextmed).split(" ")[0];
        html += "<tr><td>" + data.data5[i].feederid + "</td><td><a class=\"neckletid\" style=\"cursor:pointer;\" id=\"" + data.data5[i].dogid + "\">" + data.data5[i].dogname + "</a></td><td>" + firstrealtime + "</td><td>" + lastrealtime + "</td><td>" + data.data5[i].timemed + "</td><td>" + nextrealtime + "</td></tr>";
    }*/
    $("#tbody_userprofilefarm").append(html);

    Page(1, 8);
    var tempOption = "";
    for (var i = 1; i <= totalPage; i++) {
        tempOption += '<option value=' + i + '>' + i + '</option>'
    }
    $("#jumpWhere").html(tempOption);

    $(".neckletid").click(function () {
        window.location.href = "/Index/PageDog6?dogid=" + this.id;
    });

    $("#a_farmdetail").click(function () {
        window.location.href = "/Index?UserName=" + username + "&Ticket=" + Ticket;
    });

	
})

function ChangeTimeFormat(logintime) {
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
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}
$(function () {
    $("#pagereflash").click(function () {
        window.location.reload();
    });

    $("#a_rebackpwd").click(function () {
/*        var clicktype = "rebackpwd";
        var senddata = {};
        senddata.clicktype = clicktype;
        senddata.username = username;
        senddata.rebackusername = personinfo;
        $.ajax({
            url: "/api/userprofileapi",
            type: "POST",
            data: senddata,
            success: function (data) {
                alert(data);
                window.location.reload();
            }
        })*/
        
         var clicktype = "rebackpwd";
      //  var username = getCookie("watchdogusername");
        var senddata = {};
        senddata.clicktype = clicktype;
        //senddata.username = username_userprofile;
        senddata.rebackusername = username_userprofile;
        $.ajax({
            url: "../userProfile/userProfileApi.do",
            type: "POST",
            data: JSON.stringify(senddata),
            contentType:"application/Json",
            success: function (data) {
                alert(data);
                window.location.reload();
            }
        })
    });
});


/**
* 分页函数
* pno--页数
* psize--每页显示记录数
* 分页部分是从真实数据行开始，因而存在加减某个常数，以确定真正的记录数
* 纯js分页实质是数据行全部加载，通过是否显示属性完成分页功能
**/
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

function goPage(pno, psize){

    $.ajax({
        url: "../userProfile/userProfileFarmPageApi.do",
        type: "POST",
        data: JSON.stringify({'username': data.data1.username,'startItem': pno, 'pageSize': psize}),                    
        contentType: "application/json",
        success: function (data) {
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
            Page(pno, psize);
            var tempOption = "";
            for (var i = 1; i <= totalPage; i++) {
                tempOption += '<option value=' + i + '>' + i + '</option>'
            }
            $("#jumpWhere").html(tempOption);
            document.getElementById("jumpWhere").options[pno-1].selected = true;
        }
    });
} 

$("#a_activeadmin").click(function () {
/*    var clicktype = "activeadmin";
    if ($("#a_activeadmin").text() == "账户冻结") {
        clicktype = "freezeadmin";
    }
    var senddata = {};
    senddata.clicktype = clicktype;
    senddata.username = username;
    senddata.activeusername = manageruserinfo;
    $.ajax({
        url: "/api/userprofileapi",
        type: "POST",
        data: senddata,
        success: function (data) {
            alert(data);
            window.location.reload();
        }
    })
    */
     var clicktype = "activeadmin";
        if ($("#a_activeadmin").text() == "账户冻结") {
            clicktype = "freezeadmin";
        }
        var senddata = {};
        senddata.clicktype = clicktype;
        //senddata.username = username;
        senddata.activeusername = username_userprofile;
        $.ajax({
            url: "../userProfile/userProfileApi.do",
            type: "POST",
            data: JSON.stringify(senddata),
            contentType:"application/Json",
            success: function (data) {
                alert(data);
                window.location.reload();
            }
        })
});

function objToArray(array) {
    var arr = []
    for (var i in array) {
        arr.push(array[i]); 
    }
    return arr;
}
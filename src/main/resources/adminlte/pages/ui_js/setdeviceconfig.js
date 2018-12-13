var neckletid = "";
$(function(){
	$("#search_neckletid").click(function(){
		$.ajax({
        	url: "/sec/api/getdeviceconfigbynecid.do",
            method: "POST",
            data: "mid="+$("#in_necketid").val(),   //方法1
            success: function (data) {
            	if (data.data == null) {
            		alert(data.msg);
    	            return;
            	}else{
            		neckletid = data.data.mid;
            		$("#input_status").val(changestatus(data.data.status));
            		$("#input_sim").val(data.data.simccid);
            		$("#input_swver").val(data.data.swver);
            		$("#input_ip").val(data.data.ip);
            		$("#input_port").val(data.data.port);
            		$("#input_ledenable").val(data.data.ledenable);
            		$("#input_infoupdatecycle").val(data.data.infoupdatecycle);
            		$("#input_tickcycle").val(data.data.tickcycle);
            		$("#input_tempflag").val(data.data.temporaryflag);
            		$("#input_tempgmt").val((timetrans(data.data.temporarygmt)));
            		$("#input_lastupdatetime").val((timetrans(data.data.updatetime)));
            		$("#input_clearerr").val(data.data.clearerr);
            		$("#input_factory").val(data.data.factory);
            		
            		if(data.data.uimodifyflag == 1 && data.data.hardmodifyflag == 0){
            			$("#lable_status").text("硬件设备尚未接收到更改通知！");
            		}else if(data.data.uimodifyflag == 0 && data.data.hardmodifyflag == 0){
            			$("#lable_status").text("硬件设备已完成配置更改！");
            		}
            	}
            }
        })
	});
	
	$("#savedeviceconf").click(function(){
		if(neckletid == ""){
			alert("请先查询获取项圈投药时间配置！");
			return;
		}
		var mid = "mid="+neckletid+"&";
		//var status = "status=" +$("#input_status").val()+"&";
		var simccid = "simccid=" +$("#input_sim").val()+"&";
		var swver ="swver=" + $("#input_swver").val()+"&";
		var ip ="ip=" + $("#input_ip").val()+"&";
		var port ="port=" + $("#input_port").val()+"&";
		var ledenable ="ledenable=" + $("#input_ledenable").val()+"&";
		var infoupdatecycle ="infoupdatecycle=" + $("#input_infoupdatecycle").val()+"&";
		var tickcycle ="tickcycle=" + $("#input_tickcycle").val()+"&";
		var tempflag ="tempflag=" + $("#input_tempflag").val()+"&";
		var tempgmt ="tempgmt=" +  $("#input_tempgmt").val()+":00&";
		var clearerr = "clearerr=" +$("#input_clearerr").val()+"&";
		var factory = "factory=" +$("#input_factory").val();
		var senddata = mid+simccid+swver+ip+port+ledenable+infoupdatecycle+tickcycle+tempflag+tempgmt+clearerr+factory;
		$.ajax({
	    	url: "/sec/api/setdeviceconfigbynecid.do",
	        method: "POST",
	        data: senddata, 
	        success: function (data) {
        		alert(data.msg);
        		if(data.code == 200){
        			$("#lable_status").text("硬件设备尚未接收到更改通知！");
        		}
	        }
	    })
	});
	
	$("#getdevicemodifiedstatus").click(function(){
		if(neckletid == ""){
			alert("请先查询获取项圈投药时间配置！");
			return;
		}
		var mid = "mid="+neckletid;
		$.ajax({
	    	url: "/sec/api/getdevicemodifiedstatus.do",
	        method: "POST",
	        data: senddata, 
	        success: function (data) {
        		alert(data.msg);
	        }
	    })
	});
	
})

function timetrans(date){
//    var date = new Date(date*1000);//如果date为13位不需要乘1000
	var date = new Date(date);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + 'T';
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
    return Y+M+D+h+m+s;
}

function changestatus(int_status){
	var status = int_status.toString(2)+"";
	status = status.split("").reverse().join("");
	while(status.length<12){
		status = status+"0";
	}
	status = status.substring(0,4)+"-"+status.substring(4,8)+"-"+status.substring(8,12);
	return status;
}




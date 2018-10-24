var neckletid = "";
$(function(){
	$("#search_neckletid").click(function(){
		$.ajax({
        	url: "/sec/api/getdeviceconfigbynecid.do",
            method: "POST",
            data: "mid="+$("#in_necketid").val(),   //方法1
            success: function (data) {
            	if (data.data == null) {
            		alert("该项圈不存在！");
    	            return;
            	}else{
            		neckletid = data.data.mid;
            		$("#input_status").val(data.data.status);
            		$("#input_swver").val(data.data.swver);
            		$("#input_ip").val(data.data.ip);
            		$("#input_port").val(data.data.port);
            		$("#input_ledenable").val(data.data.ledenable);
            		$("#input_infoupdatecycle").val(data.data.infoupdatecycle);
            		$("#input_tickcycle").val(data.data.tickcycle);
            		$("#input_tempflag").val(data.data.temporaryflag);
            		$("#input_tempgmt").val((timetrans(data.data.temporarygmt)));
            		$("#input_lastupdatetime").val((timetrans(data.data.updatetime)));
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
		var status = "status=" +$("#input_status").val()+"&";
		var swver ="swver=" + $("#input_swver").val()+"&";
		var ip ="ip=" + $("#input_ip").val()+"&";
		var port ="port=" + $("#input_port").val()+"&";
		var ledenable ="ledenable=" + $("#input_ledenable").val()+"&";
		var infoupdatecycle ="infoupdatecycle=" + $("#input_infoupdatecycle").val()+"&";
		var tickcycle ="tickcycle=" + $("#input_tickcycle").val()+"&";
		var tempflag ="tempflag=" + $("#input_tempflag").val()+"&";
		var tempgmt ="tempgmt=" +  $("#input_tempgmt").val()+":00";
		var senddata = mid+status+swver+ip+port+ledenable+infoupdatecycle+tickcycle+tempflag+tempgmt;
		$.ajax({
	    	url: "/sec/api/setdeviceconfigbynecid.do",
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
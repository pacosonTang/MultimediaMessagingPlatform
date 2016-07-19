<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 编码格式为jsp文件的存储格式 -->
<%@ page contentType="text/html;charset=UTF-8"%>
<!--  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="zh-CN">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
	<title>聊天室</title>
	
	<link href="<c:url value="/"/>bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<c:url value="/"/>bootstrap/jquery/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<c:url value="/"/>bootstrap/js/bootstrap.min.js"></script>
    <script src="<c:url value="/resources/sockjs-1.1.1.js" />"></script>
    <script src="<c:url value="/resources/stomp.js" />"></script>
    
	<script type="text/javascript">
		var checkoutUserlistIsAccess = false;

		/* alert("nihao" + "<c:out value='${curuser}' />");
		if("<c:out value='${curuser}' />" != "null") {
			checkoutUserlistIsAccess = true;	
		} */
		 
		$(document).ready(function(){
			$("#first").hide();
			if(checkoutUserlistIsAccess==false) {
				checkoutUserlist(); // checkout user list.
			}
			
			$("input[id='send']").keydown(function(event){ // trigger event enterkey typed.
				if(event.which == "13") {					
					sendTextByStomp();
				}
			});
			
			/* bind event to input with id searched */
			/* $("#search").bind("click", function() {
				$("#second").hide();
				$("div[id^='first']").hide();
			});
			
			$("#search").bind("blur", function() {
				$("#second").show();
				$("div[id^='first']").hide();
			}); */
			
			connectWithStomp();
			
		});
		
		//ajax 访问函数
		var member;
		function checkoutUserlist(){
			var userid=1;
			// alert("request for user list by ajax.");
			var url = "<c:url value='/chat/userlist' />"; //请求的地址 
			$.post(url,{
					keyword:userid //[逗号 连接 ]
				},
				function(data){ // 回调函数 .
					member = data;					
					for(var i=0; i<data[0].length; i++) {						
						appendAIntoDiv("second_userlist", data[0][i], data[1][i]);
					}
					checkoutUserlistIsAccess = true;
				},"json"); 
		} // ajax 访问函数 over.
		
		// append <a> into a div.
		function appendAIntoDiv(objId, isAvailable, value) {
			var imgurl = "<c:url value='/image/status" + isAvailable + ".gif'/>";
			$("#"+objId).append("<a href='<c:url value='/chat/single?touser=" 
					+ value + "'/>' class='list-group-item'>&nbsp;&nbsp;<img src='" + imgurl + "' />"+ "&nbsp;" + value +"</a>");
		}
		
		// create chat with another user by ajax . also potential usage of ajax.
		/* function createChatSingle(url) {
			var userid=1;
			$.post(url,{
				keyword:userid //[逗号 连接 ]
			},
			function(data){ // 回调函数 .
				member = data;
				for(var i=0; i<data.length; i++) {
					appendAIntoDiv("second_userlist", data[i]);
				}
			},"json"); 
		} */
		
		// send text to other users.
		function sendText() {
			var userid=1;
			var text = $("input[id='send']").val();
			// alert(text);
			var myurl = "<c:url value='/chat/sendtext' />";
			$.ajax({ 
	            type:"POST", 
	            url:myurl, 
	            dataType:"json",      
	            contentType:"application/json",               
	            data:JSON.stringify({"sendtext":text}), 
	            success:function(data){ 
	            	// alert("send text successfully.");
	            	$("div[id='panel_chat']").append(
	            			"<span class='label label-info' style='float: right;'>"+ text + "</span><br/><br/>");
	            	$("input[id='send']").val("");
	            } 
	         }); 
		} // send text to other users.
		
		function sendTextByStomp() {
			alert("js func sendTextByStomp.");
			var msg = $("input[id='send']").val();
	        stompClient.send("/app/sendtext", {}, JSON.stringify({ 'text': msg })); 
		}
		
		// connect with stomp server.
		function connectWithStomp() {  
            var socket = new SockJS("<c:url value='/chatlog'/>");  
            stompClient = Stomp.over(socket);  
            stompClient.connect({}, function(frame) {  
                console.log('Connected: ' + frame);  
                
                stompClient.subscribe('/user/queue/textmsg',function(msg){
                	var receivetext = JSON.parse(msg.body).text;
                    alert(receivetext);
                    $("div[id='panel_chat']").append(
                			"<span class='label label-info' style='float: right;'>"+ receivetext + "</span><br/><br/>");
                	$("input[id='send']").val("");
                }); 
            });  
        } // connect and subscribe over.
		
	</script>
</head>

<body>	
	<div align="center"><table>
		<tr valign="top">
			<td>
				<div class="panel panel-success" style="width: 550px;">
				  <div class="panel-heading">
				    <h3 class="panel-title">
				    	<c:if test="${touser != null}">
				    		与&nbsp;${touser }&nbsp;对话中
			    		</c:if>
			    	</h3> 
				  </div>
				  <div id="panel_chat" class="panel-footer" style="height: 450px;overflow: scroll;">
						<span class="label label-info" style="float: right;">mine info</span><br/>
						<span class="label label-info" style="float: left;">the other info</span><br/>
				  </div>
				  
				  <div class="panel-body">
				    <div class="input-group">
					  <input id="send" type="text" class="form-control" aria-describedby="basic-addon2" 
					  		 style="width:530px;height: 80px;padding: 5px;">
					</div>
						<button type="button" class="btn btn-default" style="float: left" onclick="javascript:sendPicture();">图片</button>
						<button type="button" class="btn btn-default" style="float: left" onclick="javascript:sendFile();">文件</button>
						<button type="button" class="btn btn-default" style="float: right" onclick="javascript:sendTextByStomp();">发送</button>
				  </div>
				</div>
			</td>
			<td>
				<div class="panel panel-success" style="width: 251px;height: 550px;">
					  
					  <div class="panel-heading">
					  	<div class="input-group">
						  <input id="search" type="text" class="form-control" placeholder="查找联系人或群" aria-describedby="basic-addon2">
						  <span class="input-group-btn">
					        <button class="btn btn-default" type="button">搜索</button>
					      </span>
						</div>
					  </div>
					  
					  <div class="panel-body">
					    <!-- first or second displayed -->
					    <div id="first0" class="btn-group btn-group-justified" role="group" aria-label="Justified button group">
							<a href="#" class="btn btn-default" role="button">联系人</a> 
							<a href="#" class="btn btn-default" role="button">群</a> 
						</div>
						<div id="first1">
							联系人列表 or 群组列表
						</div>
						
						<div id="second">
							<div class="list-group" id="second_userlist">
							  <a href="#" class="list-group-item active">用户列表</a>
							</div>
						</div>
						
					  </div>
				</div>
			</td>
		</tr>
	</table></div>
	
</body>
</html>
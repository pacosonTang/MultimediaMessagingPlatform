<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 编码格式为jsp文件的存储格式 -->
<%@ page contentType="text/html;charset=UTF-8"%>
<!--  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<html lang="zh-CN">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
	<title>聊天室</title>
	
	<link href="<s:url value="/"/>bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<s:url value="/"/>bootstrap/js/bootstrap.min.js"></script>
    
	<script type="text/javascript">
		
		var checkoutUserlistIsAccess = false;
		$(document).ready(function(){	
			$("#first").hide();
			if(checkoutUserlistIsAccess==false) {
				checkoutUserlist(); // checkout user list.
			}
			
			/* bind event to input with id searched */
			/* $("#search").bind("click", function() {
				$("#second").hide();
				$("div[id^='first']").hide();
			});
			
			$("#search").bind("blur", function() {
				$("#second").show();
				$("div[id^='first']").hide();
			}); */
			
		});
		
		//ajax 访问函数
		var member;
		function checkoutUserlist(){
			checkoutUserlistIsAccess = true;
			var userid=1;
			// alert("request for user list by ajax.");
			var url = "<c:url value='/chat/userlist' />"; //请求的地址 
			$.post(url,{
					keyword:userid //[逗号 连接 ]
				},
				function(data){ // 回调函数 .
					member = data;
					for(var i=0; i<data.length; i++) {
						appendAIntoDiv("second_userlist", data[i]);
					}
				},"json"); 
		}
		
		// append <a> into a div.
		function appendAIntoDiv(objId, value) {
			$("#"+objId).append("<a href='<c:url value='/chat/single?touser=" 
					+ value + "'/>' class='list-group-item'>&nbsp;&nbsp;&nbsp;&nbsp;" + value +"</a>");
		}
		
		// create chat with another user by ajax .
		function createChatSingle(url) {
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
		}
		
	</script>
</head>

<body>	
	<div align="center"><table>
		<tr valign="top">
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
			<td>
				<div class="panel panel-success" style="width: 550px;">
				  <div class="panel-heading">
				    <h3 class="panel-title">与&nbsp;${chat_obj }&nbsp;对话中</h3>
				  </div>
				  <div class="panel-footer" style="height: 450px;">chat history.</div>
				  
				  <div class="panel-body">
				    <div class="input-group">
					  <input type="text" class="form-control" aria-describedby="basic-addon2" style="width:530px;">
					</div>
					
						<button type="button" class="btn btn-default" style="float: left">图片</button>
						<button type="button" class="btn btn-default" style="float: left">文件</button>
						<button type="button" class="btn btn-default" style="float: right">发送</button>
						
				  </div>
				</div>
			</td>
		</tr>
	</table></div>
	
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> <!-- 编码格式为jsp文件的存储格式 -->
<%@ page contentType="text/html;charset=UTF-8"%> <!--  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html charset=UTF-8"> <!-- 浏览器解析jsp的编码 -->
	</head>
	
	<body>
		
		<c:if test="${username == null}">
			<a href="<c:url value="/chat/loginform" />">登录</a> <br/>
		</c:if>
		<c:if test="${username != null}">
			<a href="<c:url value="/chat/chatroom" />">进入聊天室</a> <br/>
		</c:if>
		<a href="<c:url value="/websocket/sendtext" />">WebSocket client</a> <br/>
		<a href="<c:url value="/websocket/sendtext" />">WebSocket client</a> <br/>
		<a href="<c:url value="/websockjs/sendtext" />">WebSockJS client</a> <br/>
		<a href="<c:url value="/smack" />">smack communication client</a> <br/>
		<a href="<c:url value="/spittle/list" />">Spittle List</a> <br/>
		<a href="<c:url value="/spitter/list" />">Spitter List</a> <br/>
		<a href="<c:url value="/spitter/register" />">Register</a> <br/>
		<a href="<c:url value="/spitter/transmit" />">transmit parameters</a> <br/>
	</body>
</html>
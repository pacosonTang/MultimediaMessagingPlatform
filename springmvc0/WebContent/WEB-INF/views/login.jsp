<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html charset=UTF-8">
</head>
<body>	
	<form action="<c:url value='/chat/login'/>" method="POST" >		
		<p><label>用户名: </label>
		<input type="text" name="username" placeholder="pacoson"/></p>  		
		<p><label>密码: </label> 
		<input type="password" name="password" placeholder="pacoson"/></p>
		  
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<button type="submit" class="btn">登录</button>
	</form>
</body>
</html>
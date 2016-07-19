<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<html>
<body><div align="center">
	<table>
		<tr>
			<td>
				<a href="<s:url value="/"/>">
					<img alt="spittr home" src="<s:url value="/"/>/resources/images/asiainfo.jpg" >
				</a>
			</td>
			<td style="vertical-align: bottom;">
				<h5>
					<c:if test="${curuser != null}">
						dear&nbsp;${curuser },&nbsp;&nbsp;
					</c:if>
					welcome to AsiaInfo Smack Communication!
				<a href="<c:url value="/chat/logout" />">退出|重新登录</a> <br/>
				</h5>
							
			</td>
		</tr>
	</table>
	<hr/>
</div></body>
</html>

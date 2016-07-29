<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>plugin demo page.</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="pageID" content="sample-service"/> <!-- 这个是必须的，同 plugin.xml 中引用的标识符必须一样. -->
  </head>
  
  <body>
    <h3>hello world. <a href="<c:url value="/plugins/plugindemo/myservlet"/>">plugin demo servlet</a></h3>
    <div class="jive-contentBoxHeader">jive-contentBoxHeader</div>
    <div class="jive-contentBox">jive-contentBox</div>
    
    <div class="jive-table">
        <table cellpadding="0" cellspacing="0" border="0" width="100%">
            <thead>
                <tr>
                    <th>name</th>
                    <th>age</th>
                    <th>stuid</th>
                </tr>
            </thead>
            <tbody>
                <tr class="jive-odd">
                    <td align="center">zhangsan</td>
                    <td align="center">35</td>
                    <td align="center">2014210541</td>
                </tr>
                <tr class="jive-even">
                       <td align="center">lisi</td>
                    <td align="center">67</td>
                    <td align="center">2014110321</td>
                </tr>
                <tr class="jive-odd">
                       <td align="center">wangwu</td>
                    <td align="center">12</td>
                    <td align="center">2015117891</td>
                </tr>
             </tbody>
        </table>
    </div>
  </body>
</html>
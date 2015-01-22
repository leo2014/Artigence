<%@ page contentType="text/html;charset=UTF-8" %>
<%
	String path = request.getContextPath();								
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta http-equiv="refresh" content="0.1;url=user">
</head>
<body>
<%-- <jsp:forward page="user"></jsp:forward> --%>
</body>
</html>
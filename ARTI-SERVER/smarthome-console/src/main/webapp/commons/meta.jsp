<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();								
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<base href="<%=basePath%>">
	<!-- bootstrap -->
	<link href="<c:url value="styles/bootstrap/css/bootstrap.css"/>" type="text/css" rel="stylesheet">
	<link href="<c:url value="styles/bootstrap/css/bootstrap-overrides.css"/>" type="text/css" rel="stylesheet">
	<!-- global styles -->
	<link href="<c:url value="styles/custom/layout.css"/>" type="text/css" rel="stylesheet">
	<link href="<c:url value="styles/custom/elements.css"/>" type="text/css" rel="stylesheet">
	<link href="<c:url value="styles/custom/icons.css"/>" type="text/css" rel="stylesheet">
	<link href="<c:url value="styles/fontAwesome/css/font-awesome.css"/>" type="text/css" rel="stylesheet">
	
	

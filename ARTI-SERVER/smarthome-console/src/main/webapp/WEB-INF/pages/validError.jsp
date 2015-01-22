<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="org.apache.commons.logging.LogFactory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>Error Page</title>
</head>

<body>

<div id="content">

	<h3>Parameter valid Error: <br><c:out value="${validResult.fieldErrorCount}"/>
	</h3>
	<br>

</div>
</body>
</html>
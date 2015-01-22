<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title><decorator:title/></title>
    <%@ include file="/commons/meta.jsp"%>
	<decorator:head/>
    <jsp:include page="/commons/footer.jsp"/>
</head>		
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.onload" writeEntireProperty="true"/>
>
	<!-- navbar -->
    <header class="navbar navbar-inverse" role="banner">
        <jsp:include page="/commons/navbar.jsp"/>
    </header>
    <!-- end navbar -->
    <!-- sidebar -->
    <div id="sidebar-nav">
        <jsp:include page="/commons/adminSidebar.jsp"/>
    </div>
    <!-- end sidebar -->
    <!-- main container -->
    <div class="content">
        <decorator:body/>
    </div>
    <!-- end main container -->
</body>
</html>

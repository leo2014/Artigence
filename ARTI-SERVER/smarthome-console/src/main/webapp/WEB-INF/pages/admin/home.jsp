<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>Admin Console</title>
</head>

<body>

<h1>Welcome!</h1>


<script type="text/javascript">
$(document).ready(function(){
    $("#home-li").addClass('active').append("<div class='pointer'><div class='arrow'></div><div class='arrow_border'></div></div>");
});
</script>
</body>
</html>

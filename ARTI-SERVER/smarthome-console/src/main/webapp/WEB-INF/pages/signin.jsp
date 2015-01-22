<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%
    String path = request.getContextPath();                             
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html>
<html lang="zh-CN" class="login-bg">
<head>
	<title>Detail Admin - Sign in</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<base href="<%=basePath%>">
    <!-- bootstrap -->
    <link href="styles/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="styles/custom/layout.css">
    <link rel="stylesheet" type="text/css" href="styles/custom/elements.css">
    <link rel="stylesheet" type="text/css" href="styles/custom/icons.css">
    
    <!-- this page specific styles -->
    <link rel="stylesheet" href="styles/custom/signin.css" type="text/css" media="screen" />

</head>
<body>


    <!-- background switcher -->
    <div class="bg-switch visible-desktop">
        <div class="bgs">
            <a href="#" data-img="landscape.jpg" class="bg active">
                <img src="img/bgs/landscape.jpg" />
            </a>
            <a href="#" data-img="blueish.jpg" class="bg">
                <img src="img/bgs/blueish.jpg" />
            </a>            
            <a href="#" data-img="7.jpg" class="bg">
                <img src="img/bgs/7.jpg" />
            </a>
            <a href="#" data-img="8.jpg" class="bg">
                <img src="img/bgs/8.jpg" />
            </a>
            <a href="#" data-img="9.jpg" class="bg">
                <img src="img/bgs/9.jpg" />
            </a>
            <a href="#" data-img="10.jpg" class="bg">
                <img src="img/bgs/10.jpg" />
            </a>
            <a href="#" data-img="11.jpg" class="bg">
                <img src="img/bgs/11.jpg" />
            </a>
        </div>
    </div>


    <div class="login-wrapper">
        <a href="index.html">
            <img class="logo" src="img/logo.png">
        </a>

        <div class="box">
            <div class="content-wrap">
                <h6>登 录</h6>
                <form id="signin" class="form-horizontal" action="j_spring_security_check" method="post">
                    <div class="control-group">
                        <div class="controls">
                            <input class="form-control" type="text" placeholder="用户名"
                                required
                                data-validation-required-message="用户名不能为空"
                                name="j_username" />
                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input id="enpassword" class="form-control" type="password" placeholder="密码"
                                required
                                data-validation-required-message="密码不能为空"
                                name="j_password" />
                            <p class="help-block"></p>
                        </div>
                    </div>
                    <a href="#" class="forgot">忘记密码?</a>
                    <div class="remember">
                        <input id="remember-me" name="_spring_security_remember_me" type="checkbox">
                        <label for="remember-me">记住账户</label>
                    </div>
                    <div class="control-group">                        
                        <p class="help-block">
                            <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                                用户名或密码错误！！
                            </c:if>
                        </p>
                    </div>
                    <div class="form-actions action">
                        <button id="submit_bt" type="button" class="btn-glow primary login">登 录</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="no-account">
            <p>你还没有账户?</p>
            <a href="register">注 册</a>
        </div>
    </div>

	<!-- scripts -->
    <script src="<c:url value="scripts/jquery/jquery.js"/>" type="text/javascript"></script>
    <script src="<c:url value="scripts/bootstrap/bootstrap.js"/>" type="text/javascript"></script>
    <script src="<c:url value="scripts/custom/theme.js"/>" type="text/javascript"></script>
    <script src="<c:url value="scripts/validation/jqBootstrapValidation.js"/>" type="text/javascript"></script>
    <script src="<c:url value="scripts/security/jQuery.md5.js"/>" type="text/javascript"></script>
    <script src="<c:url value="scripts/security/BigInt.js"/>" type="text/javascript"></script>
    <script src="<c:url value="scripts/security/RSA.js"/>" type="text/javascript"></script>
    <script src="<c:url value="scripts/security/Barrett.js"/>" type="text/javascript"></script>
    <!-- pre load bg imgs -->
    <script type="text/javascript">
        $(function () {
            $("input").not("[type=submit]").jqBootstrapValidation(); 
            // bg switcher
            var $btns = $(".bg-switch .bg");
            $btns.click(function (e) {
                e.preventDefault();
                $btns.removeClass("active");
                $(this).addClass("active");
                var bg = $(this).data("img");

                $("html").css("background-image", "url('img/bgs/" + bg + "')");
            });

             $("#submit_bt").click(function(){
                if($("#enpassword").val().length!=32){
             	var pwdMD5Twice = $.md5($.md5($("#enpassword").val()));
             	$("#enpassword").val(pwdMD5Twice);
                }
                $("#signin").submit();
             });
        });
    </script>
</body>
</html>
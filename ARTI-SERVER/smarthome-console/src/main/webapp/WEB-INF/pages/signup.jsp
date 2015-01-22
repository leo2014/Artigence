<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%
    String path = request.getContextPath();                             
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html lang="zh-CN" class="login-bg">
<head>
	<title>Detail Admin - Sign up</title>
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
    <link rel="stylesheet" href="styles/custom/signup.css" type="text/css" media="screen" />

    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
    <div class="header">
        <a href="index.html">
            <img src="img/logo.png" class="logo" />
        </a>
    </div>
    <div class="login-wrapper">
        <div class="box">
            <div class="content-wrap">
                <h6>注 册</h6>
                
                <form id="signup" class="form-horizontal" action="register" method="post">
                    <div class="control-group">
                        <div class="controls">
                            <input class="form-control" type="text" placeholder="用户名"
                                maxlength="10"
                                minlength="4"
                                required
                                data-validation-ajax-ajax="register/ajaxValid.json"
                                data-validation-maxlength-message="用户名字符在4到10之间"
                                data-validation-minlength-message="用户名字符在4到10之间"
                                data-validation-required-message="用户名不能为空"
                                data-validation-ajax-message="该用户名已存在"
                                name="username" />
                            <p class="help-block"></p>
                        </div> 
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input id="password" class="form-control" type="password" placeholder="密码"
                                minlength="6"
                                required
                                data-validation-minlength-message="密码字符在6到20之间"
                                data-validation-required-message="密码不能为空"
                                name="enpassword" />
                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input id="repassword" class="form-control" type="password" placeholder="确认密码"
                                data-validation-match-match="enpassword"
                                data-validation-match-message="密码不一致" />
                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <c:forEach items="${validResult.allErrors}" var="error">
                            <p class="help-block">${error.field}格式输入错误 ${error.defaultMessage}</p>
                        </c:forEach>
                        <c:if test='${validResultEx.code=="emailExist"}'>
                                <spring:message code="errors.required" arguments="用户名或密码" argumentSeparator=","/>
                        </c:if>
                    </div>
                    <div class="form-actions action">
                        <button id="submit_bt" type="button" class="btn-glow primary signup">注 册</button>
                    </div>
                </form>  
                             
            </div>
        </div>

        <div class="already">
            <p>我已拥有一个账户?</p>
            <a href="login">登录</a>
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
    <script>
        $(function () { 
            $("input").not("[type=submit]").jqBootstrapValidation(); 
            //RSA加密
            $("#submit_bt").click(function(){
                $.ajax({
                    type:"get",
                    url:"encrypt.json",
                    async:false,
                    success:function(data){
                        setMaxDigits(131);
                        console.info("exponent:"+data.exponent+" modulus:"+data.modulus);
                        var key = new RSAKeyPair(data.exponent, '', data.modulus);
                        //if($("#password").val().length!=32){
                        var pwdMD5Twice = $.md5($.md5($("#password").val()));
                        //console.info(pwdMD5Twice);
                        var pwdRtn = encryptedString(key, pwdMD5Twice);
                        //console.info(pwdRtn);
                        $("#password").val(pwdRtn);
                        $("#repassword").val(pwdRtn);
                        $("#signup").submit();
                
                    }

                })
            })
           
        } );
    </script>
</body>
</html>
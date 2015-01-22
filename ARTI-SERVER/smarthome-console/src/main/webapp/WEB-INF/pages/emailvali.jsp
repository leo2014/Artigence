
<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%
    String path = request.getContextPath();                             
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>Detail Admin - New User Form</title>
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
                <h6>邮箱验证</h6>
                <form id="signin" class="form-horizontal" action="user/emailValid" method="post">
                    <div class="control-group">
                        <div class="controls">
                            <input id="email" class="form-control" type="email" placeholder="邮箱"
                                required
                                data-validation-ajax-ajax="user/isEmailExist.json"
                                data-validation-required-message="邮箱不能为空"
                                data-validation-email-message="邮箱格式不对"
                                data-validation-ajax-message="该邮箱已被使用"
                                name="email"/>
                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input id="emailCode" class="form-control" type="number" placeholder="验证码"
                                required
                                data-validation-required-message="验证码不能为空"
                                name="emailCode"/>                            
                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div>
                        <button id="sendbt" type="button" class="btn-glow" >发送验证码</button>
                        <p id="timeout"></p>
                    </div>
                    <div class="form-actions action">
                        <button id="submit_bt" type="submit" class="btn-glow primary login">验 证</button>
                    </div>
                </form>                
            </div>
        </div>
    </div>

    <!-- scripts -->
    <script src="<c:url value="scripts/jquery/jquery.js"/>" type="text/javascript"></script>
    <script src="<c:url value="scripts/bootstrap/bootstrap.js"/>" type="text/javascript"></script>
    <script src="<c:url value="scripts/custom/theme.js"/>" type="text/javascript"></script>
    <script src="<c:url value="scripts/validation/jqBootstrapValidation.js"/>" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            $("input").not("[type=submit]").jqBootstrapValidation();
            var time = 60;
            function afterSend(){
                if(time > 1){
                    time = time-1;
                    $("#timeout").html("如没收到，"+time+"秒后重发");
                    console.info(time);
                    setTimeout(afterSend, 1000);
                   
                }else{
                    time = 60;
                    $("#sendbt").attr("disabled",false);
                    $("#timeout").html("");
                }
            };
            $("#sendbt").click(function(){
                $.ajax({
                    type:"get",
                    url:"user/"+$("#email").val()+"/sendEmailValid.json",
                    success:function(){
                        afterSend();
                    }
                });
                $("#sendbt").attr("disabled",true);
            });

        })
    </script>
</body>
</html>
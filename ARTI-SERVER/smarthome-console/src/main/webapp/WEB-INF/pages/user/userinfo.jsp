<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>用户资料</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- this page specific styles -->
    <link rel="stylesheet" href="styles/custom/personal-info.css" type="text/css" media="screen" />

    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>


	<!-- main container .wide-content is used for this layout without sidebar :)  -->
        <div class="settings-wrapper" id="pad-wrapper">
            <div class="row">
                <!-- avatar column -->
<!--                 <div class="col-md-3 col-md-offset-1 avatar-box">
                    <div class="personal-image">
                        <img src="img/personal-info.png" class="avatar img-circle">
                        <p>上传头像</p>
                        
                        <input type="file" />
                    </div>
                </div>
 -->
                <!-- edit form column -->
                <div class="col-md-7 personal-info">
                    <h5 class="personal-title">个人资料</h5>

                    <form action="user/update" class="form-horizontal" role="form" method="post">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">用户名:</label>
                            <div class="col-lg-8">
                                <input name="name" class="form-control" type="text" value="${userVo.name}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">Email:</label>
                            <div class="col-lg-8">
                                <input name="email" class="form-control" type="email" value="${userVo.email}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">年龄:</label>
                            <div class="col-lg-8">
                                <input name="age" class="form-control" type="number" value="${userVo.age}" />
                            </div>
                        </div>
                       
                        <div class="form-group">
                            <label class="col-lg-2 control-label">手机:</label>
                            <div class="col-lg-8">
                                <input name="cellphone" class="form-control" type="text" value="${userVo.cellphone}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">地址:</label>
                            <div class="col-lg-8">
                                <input name="address" class="form-control" type="text" value="${userVo.address}" />
                            </div>
                        </div>
                        <div class="actions">
                            <input type="submit" class="btn-glow primary" value="保存修改">
                            <span>OR</span>
                            <input type="reset" value="Cancel" class="reset">
                        </div>
                    </form>
                </div>
            </div>            
        </div>

    <!-- end main container -->
  <script type="text/javascript">
    $(document).ready(function(){
        $("#user-li").addClass('active').append("<div class='pointer'><div class='arrow'></div><div class='arrow_border'></div></div>");
        $("#userinfo-li > a").addClass('active');$("#userinfo-li").parent("ul.submenu").addClass('active');      
    });
</script>
</body>
</html>
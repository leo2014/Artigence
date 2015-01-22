<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>角色管理</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link href="styles/datatable/css/jquery.dataTables.css" type="text/css" rel="stylesheet" />
</head>
<body>


    <!-- main container .wide-content is used for this layout without sidebar :)  -->
 <div id="pad-wrapper" class="datatables-page">
       <div class="row">
           <div class="col-xs-8">
                    <table cellpadding="0" cellspacing="0" border="0" class="" id="rolestable">
                        <thead>
                            <tr role="row">
                                <th tabindex="0" rowspan="1" colspan="1">序号
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">名称
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">描述
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">是否启用
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">权限设置
                                </th>
                            </tr>
                        </thead>
                        
                        <tfoot>
                            <tr>
                             <th tabindex="0" rowspan="1" colspan="1">序号
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">名称
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">描述
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">是否启用
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">权限设置
                                </th>
                            </tr>
                        </tfoot>
                        
                        <tbody>
                        <%int i = 0; %>
                         <c:forEach items="${roleVos }" var="roleVo">
                            <tr>
                                <td><%=++i %></td>
                                <td>${roleVo.name}</td>
                                <td>${roleVo.descr}</td>
                                
                                <td>                                               
                                    <c:if test="${roleVo.enable==true}">
                                        <span data-on-text="ON" data-off-text="OFF" class="slider-button on">
                                        ${roleVo.isEnableString}
                                        </span>
                                    </c:if>
                                    <c:if test="${roleVo.enable==false}">
                                        <span data-on-text="ON" data-off-text="OFF" class="slider-button">
                                        ${roleVo.isEnableString}
                                        </span>
                                    </c:if>
                                </td>
                                <td><button class="btn btn-primary">权限修改</button></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
           </div>
           <div class="col-xs-4">
                <table cellpadding="0" cellspacing="0" border="0" class="" id="authoritiestable">
                        <thead>
                            <tr role="row">
                                <th tabindex="0" rowspan="1" colspan="1">序号
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">权限名称
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">选择
                                </th>
                            </tr>
                        </thead>
                        <%int j = 0; %>
                        <tbody>
                         <c:forEach items="${authorityVos }" var="authorityVo">

                            <tr>
                                <td><%=++j %></td>
                                <td>${authorityVo.name}</td>
                                <td><input name="authorityId" type="checkbox" class="check" value="${authorityVo.id}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
           </div>
       </div>
</div>
    <!-- end main container -->
    <script src="scripts/datatable/jquery.dataTables.js"></script>
  <script src="scripts/datatable/datatableInit.js"></script>
  <script type="text/javascript">
    $(document).ready(function(){
        $("#security-li").addClass('active').append("<div class='pointer'><div class='arrow'></div><div class='arrow_border'></div></div>");
        $("#roles-li > a").addClass('active');$("#roles-li").parent("ul.submenu").addClass('active');      
    });
</script>
</body>
</html>
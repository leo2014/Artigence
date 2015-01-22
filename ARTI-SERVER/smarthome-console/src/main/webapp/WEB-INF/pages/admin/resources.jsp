<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>系统访问资源</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  
  <link href="styles/datatable/css/jquery.dataTables.css" type="text/css" rel="stylesheet" />
</head>
<body>


	<!-- main container .wide-content is used for this layout without sidebar :)  -->
 <div id="pad-wrapper" class="datatables-page">
        <div class="row">
           <div class="col-xs-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="" id="resourcestable">
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
                            </tr>
                        </tfoot>
                        <%int i = 0; %>
                        <tbody>
                         <c:forEach items="${resourceVos }" var="resourceVo">
                            <tr>
                                <td><%=++i %></td>
                                <td>${resourceVo.name}</td>
                                <td>${resourceVo.descr}</td>
                                
                                <td>                                               
                                    <c:if test="${resourceVo.enable==true}">
                                        <span data-on-text="ON" data-off-text="OFF" class="slider-button on">

                                        </span>
                                    </c:if>
                                    <c:if test="${resourceVo.enable==false}">
                                        <span data-on-text="ON" data-off-text="OFF" class="slider-button">

                                        </span>
                                    </c:if>
                                </td>
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
        $("#resource-li > a").addClass('active');$("#resource-li").parent("ul.submenu").addClass('active');  
        
    });
</script>
</body>
</html>
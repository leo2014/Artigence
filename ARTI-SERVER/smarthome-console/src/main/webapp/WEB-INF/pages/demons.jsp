<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>Detail Demon Status - DataTables</title>

  <link href="styles/datatable/css/jquery.dataTables.css" type="text/css" rel="stylesheet" />
</head>
<body>
        <!-- settings changer -->
        <div class="skins-nav">
            <a id="deleteall" href="/index/delete/-1" class="skin first_nav selected">
                <span class="icon"></span><span class="text">清空记录</span>
            </a>
        </div>
        <div id="pad-wrapper" class="datatables-page">
            
            <div class="row">
                <div class="col-md-12">

                    <table cellpadding="0" cellspacing="0" border="0" class="" id="example">
                        <thead>
                            <tr role="row">
                                <th tabindex="0" rowspan="1" colspan="1">序号
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">MAC地址
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">开关时间
                                </th>
                                <th tabindex="0" rowspan="1" colspan="1">状态
                                </th>
                            </tr>
                        </thead>
                        
                        <tfoot>
                        	
                            <tr>
                                <th rowspan="1" colspan="1">序号</th>
                                <th rowspan="1" colspan="1">MAC地址</th>
                                <th rowspan="1" colspan="1">开关时间</th>
                                <th rowspan="1" colspan="1">状态</th>
                            </tr>
                           
                        </tfoot>
                        <tbody>
                        <%int i = 0; %>
                        <c:forEach items="${demonVos }" var="demonVo">
                            <tr>
                                <td><%=++i %></td>
                                <td>${demonVo.mac }</td>
                                <td>${demonVo.cmdTime }</td>
                                <td>     
                                	<div class="slider-frame primary">
                                	           <c:if test="${demonVo.status==true}">
                            			<span data-on-text="ON" data-off-text="OFF" class="slider-button on">
                            			${demonVo.statusString}
                            			</span>
                            		</c:if>
                            		<c:if test="${demonVo.status==false}">
                            			<span data-on-text="ON" data-off-text="OFF" class="slider-button">
                            			${demonVo.statusString}
                            			</span>
                            		</c:if>
                        	</div>
                                </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>

        </div>
    <script src="scripts/datatable/jquery.dataTables.js"></script>
	<!-- scripts -->
    <script type="text/javascript">
        $(document).ready(function() {
            $('#example').dataTable({
                "sPaginationType": "full_numbers"
            });
          	$('#deleteall').click(function(){
          		$.ajax({
          			url:"index/-1.json",
          			async:false,
          			type:"POST",
          			data:{"_method":"delete"},
                    success:function(){
                        window.location.reload();
                    }

          		});
          	});  
        });
    </script>
</body>
</html>

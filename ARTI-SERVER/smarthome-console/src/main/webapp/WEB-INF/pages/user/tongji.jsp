<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>统计信息</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="styles/datatable/css/dataTables.css" type="text/css" rel="stylesheet" />
  <link href="styles/fontAwesome/css/rating.css" type="text/css" rel="stylesheet" />
</head>
<body>

       

        <div id="pad-wrapper" class="datatables-page">
            <div class="row">
                <div class="col-md-12 table-responsive">
                    <table cellspacing="0" class="table table-striped table-hover table-bordered" id="example">
                        <thead>
                            <tr role="row">
                                <th>节点ID</th>
                                <th>数据总数</th>
                                <th>数据重复数</th>
                                <th>丢包数</th>
                                <th>重复比</th>
                                <th>丢包比</th>
                            </tr>
                        </thead>
                
                    </table>

                </div>
            </div>

        </div>

    <!-- end main container -->
    <script type="text/javascript" src="scripts/datatable/jquery.dataTables.js"></script>
    <script type="text/javascript" src="scripts/datatable/dataTables.tableTools.js" charset="utf-8"></script>
    <script type="text/javascript" src="scripts/custom/dataTables.js"></script>
    <script type="text/javascript" src="scripts/admin/table.settings.js"></script>
    <script type="text/javascript">

        $(document).ready(function() {

            $("#data-li").addClass('active').append("<div class='pointer'><div class='arrow'></div><div class='arrow_border'></div></div>");
            $("#tongji-li > a").addClass('active');$("#tongji-li").parent("ul.submenu").addClass('active');  
            //datatable
             $.extend(settings,{
                    //dom: '<"clear">lfrtip',
                   "columns":[
                        {"name":"nodeid","data":"nodeid", "orderable":false},
                        {"name":"total","data":"total", "orderable":false},
                        {"name":"errorCount","data":"errorCount", "orderable":false},
                         {"name":"duibao","data":"duibao", "orderable":false},
                        {"name":"error","data":"error", "orderable":false},
                         {"name":"diubaob","data":"diubaob", "orderable":false},
                    ]
            }); 

            $.extend(settings.ajax,{
                "url":"user/dataRecord/recordSis.json"
             });    
            var datastable = $("#example").DataTable(settings);
           // $.ajax({
           //      url:"user/dataRecord/recordSis.json",
           //      type:"get",
           //      dataType:"json",
           //      success:function(data){
           //          alert(data[0].nodeid);
           //            $('#info').empty();  
           //               var html = ''; 
           //               $.each(data, function(commentIndex, comment){
           //                  html += '<div><h6>' +comment["nodeid"]
           //                      +'</h6><h6>'+comment["total"]
           //                      +'</h6><h6>'+comment["errorCount"]
           //                      +'</h6><h6>'+comment["error"]
           //                      +'</h6></div>';
           //              });
           //               $('#info').html(html);
           //      }
           // });

        });
    </script>
</body>
</html>
<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>ARTI信息</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="styles/datatable/css/dataTables.css" type="text/css" rel="stylesheet" />
  <link href="styles/fontAwesome/css/rating.css" type="text/css" rel="stylesheet" />
</head>
<body>

       

        <!-- main container .wide-content is used for this layout without sidebar :)  -->

        <div id="pad-wrapper" class="datatables-page">
            <div class="row">
                <div class="col-md-12 table-responsive">
                    <table cellspacing="0" class="table table-striped table-hover table-bordered" id="example">
                        <thead>
                            <tr role="row">
                                <th>序号</th>
                                <th>ARTImac地址</th>
                                <th>节点序列号</th>
                                <th>状态</th>
                                <th>时间</th>
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
    <script src="scripts/validation/jqBootstrapValidation.js" type="text/javascript"></script>
    <script type="text/javascript">

        $(document).ready(function() {

            $("#data-li").addClass('active').append("<div class='pointer'><div class='arrow'></div><div class='arrow_border'></div></div>");
            $("#datainfo-li > a").addClass('active');$("#datainfo-li").parent("ul.submenu").addClass('active');  
            //datatable
             $.extend(settings,{
                    dom: '<"clear">lfrtip',
                   "columns":[
                        {"name":"id","data":"id"},
                        {"name":"artiMac","data":"artiMac", "orderable":false},
                        {"name":"nodeSerialNum","data":"nodeSerialNum", "orderable":false},
                        {"name":"status","data":"status",
                             "render":function(data, types, row){
                                if(data == true)
                                    return  '<i class="fa fa-bolt fa-2x fagreen">&nbsp;</i><small>开</small>';
                                else 
                                    return  '<i class="fa fa-times-circle-o fa-2x fared">&nbsp;</i><small>关</small>';
                            },
                            "orderable":false
                        },
                        {"name":"transferDateString","data":"transferDateString"},
                    ]
            }); 

            $.extend(settings.ajax,{
                "url":"user/dataRecord/search.json"
             });    
            var datastable = $("#example").DataTable(settings);
            var  runDraw=function(){
                setTimeout(runDraw,10000);
                 datastable.draw();
            };
            //runDraw();

        });
    </script>
</body>
</html>
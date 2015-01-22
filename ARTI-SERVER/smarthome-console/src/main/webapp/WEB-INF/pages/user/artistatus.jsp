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
                <div class="col-md-6 table-responsive">
                    <table cellspacing="0" class="table table-striped table-hover table-bordered" id="example1">
                        <thead>
                            <tr role="row">
                                <th>序号</th>
                                <th>名称</th>
                                <th>mac地址</th>
                                <th>在线状态</th>
                            </tr>
                        </thead>
                    </table>

                </div>
                <div class="col-md-6 table-responsive">
                    <table cellspacing="0" class="table table-striped table-hover table-bordered" id="example2">
                        <thead>
                            <tr role="row">
                                <th>序号</th>
                                <th>名称</th>
                                <th>序列号</th>
                                <th>是否注册网关</th>
                                <th>在线状态</th>

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

            $("#status-li").addClass('active').append("<div class='pointer'><div class='arrow'></div><div class='arrow_border'></div></div>");
            $("#statusinfo-li > a").addClass('active');$("#statusinfo-li").parent("ul.submenu").addClass('active');  
            //datatable

            var nodeSetting = $.extend(true,{},settings);  
            $.extend(nodeSetting,{
                    dom: '<"clear">lfrtip',
                   "columns":[
                        {"name":"id","data":"id"},
                        {"name":"name","data":"name"},
                        {"name":"serialNum","data":"serialNum"},
                        {"name":"artiAuth","data":"artiAuth",
                             "render":function(data, type, row){
                                if(data == true)
                                    return  '<i class="fa fa-circle fa-2x fagreen">&nbsp;</i><small>网关已注册该节点</small>';
                                else 
                                    return  '<i class="fa fa-circle fa-2x fared">&nbsp;</i><small>网关未注册该节点</small>';
                            },
                            "orderable":false
                        },
                        {"name":"status","data":"status",
                             "render":function(data, type, row){
                                if(data == true)
                                    return  '<i class="fa fa-circle fa-2x fagreen">&nbsp;</i><small>在线</small>';
                                else 
                                    return  '<i class="fa fa-circle fa-2x fared">&nbsp;</i><small>掉线</small>';
                            },
                            "orderable":false
                        },
                        // {"name":"data","data":"data"}
                    ]
            }); 

            $.extend(nodeSetting.ajax,{
                "url":"user/node/search.json"
            }); 

            $.extend(settings,{
                    dom: '<"clear">lfrtip',
                   "columns":[
                        {"name":"id","data":"id"}, 
                        {"name":"name","data":"name"},
                        {"name":"mac","data":"mac"},
                        {"name":"onLine","data":"onLine",
                             "render":function(data, type, row){
                                if(data == true)
                                    return  '<i class="fa fa-circle fa-2x fagreen">&nbsp;</i><small>在线</small>';
                                else 
                                    return  '<i class="fa fa-circle fa-2x fared">&nbsp;</i><small>掉线</small>';
                            },
                            "orderable":false
                        },
                    ]
            }); 

            $.extend(settings.ajax,{
                "url":"user/arti/search.json"
             }); 

           
            var artistable = $("#example1").DataTable(settings);
            var nodestable = $("#example2").DataTable(nodeSetting);
            var  runDraw=function(){
                setTimeout(runDraw,10000);
                 artistable.draw();
                nodestable.draw();
            };
            runDraw();
        });
    </script>
</body>
</html>
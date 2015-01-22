<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>节点信息</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="styles/datatable/css/dataTables.css" type="text/css" rel="stylesheet" />
  <link href="styles/fontAwesome/css/rating.css" type="text/css" rel="stylesheet" />
</head>
<body>

        <!-- modal -->
        <div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="createModalLable" aria-hidden="true">
             <!-- 表单-->
            <form class="form-horizontal" id="formArti" role="form">
                <div class="modal-dialog">
                        <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">
                                        <span aria-hidden="true">&times;</span>
                                        <sapn class="sr-only">Close</sapn>
                                    </button>
                                    <h4 class="modal-title" id="modalLabel">添加节点</h4>
                                </div>
                                <div class="modal-body">
                                    <div id="divForm">
                                        <div class="form-group control-group">
                                            <label for="inputMac" class="col-sm-2 control-label" >mac地址</label>
                                            <div class="col-sm-9 controls">
                                                <input type="text" class="form-control" id="inputMac" placeholder="mac地址" 
                                                    required
                                                    name="mac" />
                                                <p class="help-block"></p>
                                            </div>
                                        </div>
                                        <div class="form-group control-group">
                                            <label for="inputSerialNum" class="col-sm-2 control-label" >序列号</label>
                                            <div class="col-sm-9 controls">
                                                <input type="text" class="form-control" id="inputSerialNum" placeholder="序列号" 
                                                    required
                                                    name="serialNum" />
                                                <p class="help-block"></p>
                                            </div>
                                        </div>
                                        <div class="form-group control-group">
                                            <label for="inputSerialNum" class="col-sm-2 control-label" >网关</label>
                                            <div class="col-sm-9 controls">
                                                <input type="text" class="form-control" id="inputSerialNum" placeholder="网关" 
                                                    required
                                                    name="artiMac" />
                                                <p class="help-block"></p>
                                            </div>
                                        </div>
                                         <div class="form-group control-group">
                                            <label for="inputName" class="col-sm-2 control-label" >名称</label>
                                            <div class="col-sm-9 controls">
                                                <input type="text" class="form-control" id="inputName" placeholder="名称" 
                                                    required
                                                    name="name" />
                                                 <p class="help-block"></p>
                                            </div>
                                        </div>
                                         <div class="form-group control-group">
                                            <label for="inputName" class="col-sm-2 control-label" >描述</label>
                                            <div class="col-sm-9 controls">
                                                <input type="text" class="form-control" id="inputName" placeholder="描述" 
                                                    name="descr" />
                                                 <p class="help-block"></p>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="divDelete">
                                        <p class="text-center lead"> 确定要删除吗？</p>
                                    </div>
                                </div>
                                <div class="modal-footer"><p class="help-block" id="helpMsg"></p>
                                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                        <button type="submit" class="btn btn-primary" id="buttonSubmit">添加提交</button>
                                        <button type="button" class="btn btn-primary" id="buttonDelete" style="display:none;">确认删除</button>
                                        <button type="submit" class="btn btn-primary" id="buttonEdit" style="display:none;">保存修改</button>
                                </div>
                        </div>
                </div>
            </form> 
        </div>

        <!-- main container .wide-content is used for this layout without sidebar :)  -->

        <div id="pad-wrapper" class="datatables-page">
            <div class="row">
                <div class="col-md-12 table-responsive">
                    <table cellspacing="0" class="table table-striped table-hover table-bordered" id="example">
                        <thead>
                            <tr role="row">
                                <th>序号</th>
                                <th>mac地址</th>
                                <th>序列号</th>
                                <th>网关是否已注册</th>
                                <th>名称</th>
                                <th>描述</th>
                                <th>所属网关mac地址</th>
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

            $("#arti-li").addClass('active').append("<div class='pointer'><div class='arrow'></div><div class='arrow_border'></div></div>");
            $("#nodeinfo-li > a").addClass('active');$("#nodeinfo-li").parent("ul.submenu").addClass('active');  
            //datatable
             $.extend(settings,{
                    dom: '<"clear">lfrtip',
                   "columns":[
                        {"name":"id","data":"id"},
                        {"name":"mac","data":"mac"},
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
                        {"name":"name","data":"name"},
                        {"name":"descr","data":"descr"},
                        {"name":"artiMac","data":"artiMac"}
                    ]
            }); 

            $.extend(settings.ajax,{
                "url":"user/node/search.json"
             });    
            var userstable = $("#example").DataTable(settings);


            //datatableTool

            function resetForm(){
                    $("#buttonSubmit").show().prop("disabled",false);;
                    $("#buttonDelete").hide().prop("disabled",false);;
                    $("#buttonEdit").hide().prop("disabled",false);;
                   $("#inputMac").val("").prop("disabled",false);
                    $("#inputSerialNum").val("").prop("disabled",false);
                    $("#inputName").val("");
            }
            var created = false;
            var edited = false;
            var deleted = false;
            var  _method1 = '';
            TableTools.BUTTONS.create = {
                "sButtonText":"添加",
                "fnClick":function(nButton,oConfig){
                    resetForm();
                    _method1 = '';
                    $("#divDelete").hide();
                    $("#divForm").show();
                    //jqBootstrapValidation
                    if(created == false){
                        edited = false;
                        created = true;
                        $("form input").not("[type=submit]").jqBootstrapValidation(); 
                   }
                 }       
            };         
            TableTools.BUTTONS.delete = {
                "sButtonText":"删除",
                 "fnClick":function(nButton,oConfig){
                    resetForm();
                    $("#buttonSubmit").hide();
                    $("#buttonDelete").show();
                    var d = userstable.rows('table tr.active').data();
                     if(d.length < 1){
                        $("#modalLabel").text("");
                        $("#buttonDelete").prop('disabled',true);
                        $("#divDelete p").text("选择一个");
                        $("#divForm").hide();
                        $("#divDelete").show();
                        return;
                    }
                    $("#modalLabel").text("删除节点");
                     var data = userstable.rows('table tr.active').data();
                    var d = true;
                    for(var i=0; i<data.length;i++){
                        if(data[i].status == false){
                            d=false;
                            break;
                        }
                    }
                    if(d){
                        $("#divDelete p").text("确定要删除吗？");
                    }else{
                        $("#divDelete p").text("节点["+data[i].serialNum+"]不在线，确定要强制删除吗？");
                    }
                    $("#divForm").hide();
                    $("#divDelete").show();

                 }
            };
            $("#buttonDelete").click(function(){
                  var data = userstable.rows('table tr.active').data();
                  var d = '';
                  for(var i=0; i<data.length;i++){
                    d=d+'ids='+ data[i].id+'&';
                  }
                  $.ajax({
                    url: 'user/node.json',
                    type: 'post',
                    dataType: 'json',
                    data: d+'_method=delete'
                   }).done(function(data) {
                        $("#modal").modal('hide');
                        userstable.draw();
                    });
             });
            TableTools.BUTTONS.edit = {
                "sButtonText":"修改",
                 "fnClick":function(nButton,oConfig){
                    resetForm();
                   var d = userstable.rows('table tr.active').data();
                    if(d.length != 1){
                        $("#modalLabel").text("");
                        $("#buttonSubmit").hide();
                        $("#divDelete p").text("选择一个");
                        $("#divForm").hide();
                        $("#divDelete").show();
                        return;
                    };
                    if(edited == false){
                        edited = true;
                        created=false;
                        $("form input").not("[type=submit]").jqBootstrapValidation('destroy'); 
                    }
                     _method1 = '&id='+d[0].id+'&_method=put';
                    $("#modalLabel").text("修改节点");
                    $("#buttonSubmit").hide();
                    $("#buttonEdit").show();
                    $("#divDelete").hide();
                    $("#inputMac").val(d[0].mac).prop("disabled",true);
                    $("#inputSerialNum").val(d[0].serialNum).prop("disabled",true);
                    $("#inputName").val(d[0].name);
                    $("#divForm").show();

                 }
            };
            $("#formArti").submit(function(e){

                $.ajax({
                    url: 'user/node.json',
                    type: 'post',
                    dataType: 'json',
                    data: $("#formArti").serialize()+_method1
                })
                .done(function(data) {
                        $("#modal").modal('hide');
                        userstable.draw();
                });
                e.preventDefault();
            });
           
            $.extend(TableTools.BUTTONS.create,TTSetting);
            $.extend(TableTools.BUTTONS.delete,TTSetting);
            $.extend(TableTools.BUTTONS.edit,TTSetting);
           
           var usersTT=new $.fn.dataTable.TableTools(userstable,{
                "sSwfPath": "scripts/datatable/swf/copy_csv_xls_pdf.swf",
                        "sRowSelect" : "os",
                        "aButtons": [
                            "create",
                            "delete",
                            "edit",
                            {
                                "sExtends": "collection",
                                "sButtonText": "导出",
                                "aButtons": [
                                    {"sExtends":"copy","sButtonText":"复制"},  
                                    {"sExtends":"xls","sButtonText":"xls表格"},
                                    {"sExtends":"csv", "sButtonText":"csv表格"},
                                    {"sExtends":"print","sButtonText":"打印"}
                                ],
                                "sButtonClass": "btn-info dropdown-toggle",
                                "fnInit": function(nButton,oConfig){
                                    $(nButton).append("<span class='caret'></span>");
                                }
                            },
                            {"sExtends":"select_all","sButtonText":"全选", "sButtonClass": "btn-info dropdown-toggle"},
                            {"sExtends":"select_none","sButtonText":"取消选定", "sButtonClass": "btn-info dropdown-toggle"}

                        ]
            });
            $(usersTT.fnContainer()).insertBefore('div.clear');
            
        });
    </script>
</body>
</html>
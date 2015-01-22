<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>用户资料</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="styles/datatable/css/dataTables.css" type="text/css" rel="stylesheet" />
</head>
<body>

        <!-- modal -->
        <div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="createModalLable" aria-hidden="true">
             <!-- 表单-->
            <form class="form-horizontal" id="formUser" role="form">
                <div class="modal-dialog">
                        <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">
                                        <span aria-hidden="true">&times;</span>
                                        <sapn class="sr-only">Close</sapn>
                                    </button>
                                    <h4 class="modal-title" id="modalLabel">添加用户</h4>
                                </div>
                                <div class="modal-body">
                                    <div id="divForm">
                                        <div class="form-group control-group">
                                            <label for="inputUsername" class="col-sm-2 control-label" >用户名</label>
                                            <div class="col-sm-9 controls">
                                                <input type="text" class="form-control" id="inputUsername" placeholder="用户名" 
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
                                        <div class="form-group control-group">
                                            <label for="inputPassword" class="col-sm-2 control-label" >密码</label>
                                            <div class="col-sm-9 controls">
                                                <input id="inputPassword" class="form-control" type="password" placeholder="密码"
                                                    minlength="6"
                                                    required
                                                    data-validation-minlength-message="密码字符在6到20之间"
                                                    data-validation-required-message="密码不能为空"
                                                    name="password" />
                                                <p class="help-block"></p>
                                            </div>
                                        </div>
                                        <div class="form-group control-group">
                                            <label for="inputRepassword" class="col-sm-2 control-label" >确认密码</label>
                                            <div class="col-sm-9 controls">
                                                <input id="inputRepassword" class="form-control" type="password" placeholder="确认密码"
                                                    data-validation-match-match="password"
                                                    data-validation-match-message="密码不一致" />
                                                <p class="help-block"></p>
                                            </div>
                                        </div>
                                         <div class="form-group control-group">
                                            <label for="inputName" class="col-sm-2 control-label" >姓名</label>
                                            <div class="col-sm-9 controls">
                                                <input type="text" class="form-control" id="inputName" placeholder="姓名" 
                                                    required
                                                    name="name" />
                                                 <p class="help-block"></p>
                                            </div>
                                        </div>
                                         <div class="form-group control-group">
                                            <label for="inputAge" class="col-sm-2 control-label" >年龄</label>
                                            <div class="col-sm-9 controls">
                                                <input type="number" class="form-control" id="inputAge" placeholder="年龄" 
                                                    required
                                                    name="age" />
                                                 <p class="help-block"></p>
                                            </div>
                                        </div>
                                         <div class="form-group control-group">
                                            <label for="inputEmail" class="col-sm-2 control-label" >邮箱</label>
                                            <div class="col-sm-9 controls">
                                                <input type="email" class="form-control" id="inputEmail" placeholder="邮箱" 
                                                     required
                                                    data-validation-ajax-ajax="admin/isEmailExist.json"
                                                    data-validation-required-message="邮箱不能为空"
                                                    data-validation-email-message="邮箱格式不对"
                                                    data-validation-ajax-message="该邮箱已被使用"
                                                    name="email"/>
                                                <p class="help-block"></p>
                                            </div>
                                        </div>
                                         <div class="form-group control-group">
                                            <label for="inputCellphone" class="col-sm-2 control-label" >手机</label>
                                            <div class="col-sm-9 controls">
                                                <input type="text" class="form-control" id="inputCellphone" placeholder="手机"
                                                    name="cellphone"/>
                                                 <p class="help-block"></p>
                                            </div>
                                        </div>
                                         <div class="form-group control-group">
                                            <label for="inputAddress" class="col-sm-2 control-label" >地址</label>
                                            <div class="col-sm-9 controls">
                                                <input type="text" class="form-control" id="inputAddress" placeholder="地址" 
                                                    required
                                                    name="address" />
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
                                <th>用户名</th>
                                <th>密码</th>
                                <th>姓名</th>
                                 <th>年龄</th>
                                <th>注册时间</th>
                                <th>邮箱</th>
                                <th>手机</th>
                                <th>地址</th>
                            </tr>
                        </thead>
                        
                        <tfoot>
                            <tr>
                                <th >序号</th>
                                <th >用户名</th>
                                <th>密码</th>
                                <th >姓名</th>
                                 <th >年龄</th>
                                <th >注册时间</th>
                                <th >邮箱</th>
                                <th >手机</th>
                                <th >地址</th>
                            </tr>
                        </tfoot>
                
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
    <script src="<c:url value="scripts/security/jQuery.md5.js"/>" type="text/javascript"></script>
    <script type="text/javascript">

        $(document).ready(function() {

            $("#users-li").addClass('active').append("<div class='pointer'><div class='arrow'></div><div class='arrow_border'></div></div>");
            $("#userlist-li > a").addClass('active');$("#userlist-li").parent("ul.submenu").addClass('active');  
            //datatable
             $.extend(settings,{
                    dom: '<"clear">lfrtip',
                   "columns":[
                        {"name":"id","data":"id"},
                        {"name":"username","data":"username"},
                        {"name":"password","data":"password","visible":false},
                        {"name":"name","data":"name"},
                        {"name":"age","data":"age"},
                        {"name":"registerDate","data":"registerDateString"},
                        {"name":"email","data":"email"},
                        {"name":"cellphone","data":"cellphone"},
                        {"name":"address","data":"address"}
                    ]
            }); 

            $.extend(settings.ajax,{
                "url":"admin/users/search.json"
             });    
            var userstable = $("#example").DataTable(settings);


            //datatableTool

            function resetForm(){
                    $("#buttonSubmit").show().prop("disabled",false);;
                    $("#buttonDelete").hide().prop("disabled",false);;
                    $("#buttonEdit").hide().prop("disabled",false);;
                   $("#inputUsername").val("").prop("disabled",false);
                    $("#inputPassword").val("").prop("disabled",false);
                    $("#inputRepassword").val("").prop("disabled",false);
                    $("#inputName").val("");
                    $("#inputAge").val("");
                    $("#inputEmail").val("").prop("disabled",false);
                    $("#inputCellphone").val("");
                    $("#inputAddress").val("");
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
                    $("#modalLabel").text("删除用户");
                    $("#divDelete p").text("确定要删除吗？");
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
                    url: 'admin/users.json',
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
                    $("#modalLabel").text("修改用户");
                    $("#buttonSubmit").hide();
                    $("#buttonEdit").show();
                    $("#divDelete").hide();
                    $("#inputUsername").val(d[0].username).prop("disabled",true);
                    $("#inputPassword").val(d[0].password).prop("disabled",true);
                    $("#inputRepassword").val(d[0].password).prop("disabled",true);
                    $("#inputName").val(d[0].name);
                    $("#inputAge").val(d[0].age);
                    $("#inputEmail").val(d[0].email).prop("disabled",true);
                    $("#inputCellphone").val(d[0].cellphone);
                    $("#inputAddress").val(d[0].address);
                    $("#divForm").show();

                 }
            };
            $("#formUser").submit(function(e){
                var pwdMD5Twice = $.md5($.md5($("#inputPassword").val()));
                $("#inputPassword").val(pwdMD5Twice);
                $.ajax({
                    url: 'admin/users.json',
                    type: 'post',
                    dataType: 'json',
                    data: $("#formUser").serialize()+_method1
                })
                .done(function(data) {
                    if(data.valid == 1 || data.userVo != null){
                        $("#modal").modal('hide');
                        userstable.draw();
                    } else
                        $("#helpMsg").val(data.message);
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
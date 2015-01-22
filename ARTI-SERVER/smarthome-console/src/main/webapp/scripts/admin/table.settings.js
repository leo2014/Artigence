
var settings = {
        "processing": true,
        "serverSide": true,
        "ajax": {
            "type":"POST",
            "data": function(d){
               return JSON.stringify(d);
            },
            contentType:"application/json;charset=UTF-8"
        },

     

       "pagingType":"full_numbers",
        "language": {
            "lengthMenu": "每页显示 _MENU_ 条数据",
            "emptyTable": "没有数据显示",
            "zeroRecords": "没发现匹配的数据记录",
            "info": "显示第 _START_ 到 _END_ ,总计 _TOTAL_ 条",
            "infoEmpty": "总计 0 条数据",
            "infoFiltered": "从总条数 _MAX_ 数据中过滤",
            "infoPostFix": "",
            "loadingRecords": "数据加载中......",
            "processing": "数据处理中......",
            "paginate": {
                "first":"首页",
                "last":"最后一页",
                "next":"下一页",
                "previous":"上一页"
            },
            "search": "搜索 ",
            "aria":{
                "sortAscending":"选择升序",
                "sortDescending":"选择降序"
            }
        }
}

var TTSetting = {
    "sAction" : "text",
    "sTag" : "default",
    "sFieldBoundary":"",
    "sFieldSeperator":"\t",
    "sNewLine":"<br>",
    "sToolTip":"",
    "sButtonClass":"btn-info dropdown-toggle",
    "sButtonClassHover":"DTTT_button_text_hover",
    "mColumns":"all",
    "bHeader":true,
    "bFooter":true,
    "sDiv":"",
    "fnMouseover":null,
    "fnMouseout":null,
    //"fnClick":null,
    "fnSelect":null,
    "fnComplete":null,
    "fnInit": function(nButton,oConfig){
        $(nButton).attr({
            "data-toggle": "modal",
            "data-target": "#modal"
        });
    }
}
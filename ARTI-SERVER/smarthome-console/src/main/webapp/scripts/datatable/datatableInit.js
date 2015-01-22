$(document).ready(function() {
    $('table').dataTable({
        "pagingType": "full_numbers",
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
    });
});
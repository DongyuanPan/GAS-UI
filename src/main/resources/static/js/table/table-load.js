// 请求加载数据，渲染 table
function load(table) {
    table.render({
        elem: '#currentTableId',
        url: '/student',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "checkbox", width: 50},
            {field: 'id', width: 80, title: 'ID', sort: true, align: "center"},
            {field: 'studentNum', width: 80, title: '学号', align: "center"},
            {field: 'name', width: 80, title: '姓名', align: "center"},
            {field: 'sex', width: 80, title: '性别', align: "center"},
            {field: 'enrollmentTime', width: 110, title: '入学日期', sort: true, align: "center"},
            {field: 'phone', minWidth: 80 , title: '手机', align: "center"},
            {field: 'degree', width: 80 , title: '学位', align: "center"},
            {field: 'type', width: 80 , title: '状态', align: "center"},
            {field: 'employment', width: 80 , title: '去向', align: "center"},
            {field: 'email', minWidth: 120 , title: '邮箱', align: "center"},
            {title: '操作', minWidth: 100, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
// 请求加载数据，渲染 table
function loadpatent(table) {
    table.render({
        elem: '#currentTableId',
        url: '/patent',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "checkbox", width: 50},
            {field: 'id', width: 80, title: 'ID', sort: true, align: "center"},
            {field: 'studentNum', width: 80, title: '学号', sort:true,align: "center"},
            {field: 'name', width: 80, title: '发明人', align: "center"},
            {field: 'degree', width: 80, title: '学历', align: "center"},
            {field: 'enrollmentTime', width: 110, title: '通过日期', sort: true, align: "center"},
            {field: 'type', width: 90, title: '类型'},
            {field: 'patname', width: 200, title: '专利名称', align: "center"},
            {field: 'summary', width: 80, title: '摘要'},
            {field: 'email', minWidth: 180, title: '邮箱', align: "center"},
            {title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}

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

// 请求加载专利数据，渲染 table，根据是否要搜索及搜索条件，映射到不同url
function loadpatent(table,name,keyword) {
    var strurl='/patent';
    if(name==""&&keyword==""){

    }else if(name==""){
        strurl='/patent/keyword';
    }else if(keyword==""){
        strurl='/patent/name';
    } else{
        strurl='/patent/condition';
    }
    table.render({
        elem: '#currentTableId',
        url: strurl,
        where:{
            'name':name,
            'keyword':keyword,
        },
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "checkbox", width: 50},
            {field: 'id', width: 80, title: 'ID', sort: true, align: "center"},
            {field: 'patname', width: 200, title: '专利名称', align: "center"},
            {field: 'name', width: 100, title: '第一作者', align: "center"},
            {field: 'secondname', width: 100, title: '其他作者', align: "center"},
            {field: 'enrollmentTime', width: 110, title: '时间', sort: true, align: "center"},
            {field: 'type', width: 90, title: '类型'},
            {field: 'summary', width: 80, title: '摘要'},
            {title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}

function loadPap(table) {
    table.render({
        elem: '#currentTableId',
        url: '/paper',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "checkbox", width: 50},
            {field: 'id', width: 80, title: 'ID', sort: true, align: "center"},
            {field: 'title', width: 80, title: '论文', align: "center"},
            {field: 'author1', width: 80, title: '第一作者', align: "center"},
            {field: 'author2', width: 80, title: '第二作者', align: "center"},
            {field: 'authorOther', minWidth: 80 , title: '其他作者', align: "center"},
            {field: 'origin', minWidth: 120 , title: '来源', align: "center"},
            {title: '操作', minWidth: 100, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
function loadWorkflow(table) {
    table.render({
        elem: '#currentTableId',
        url: '/workflow',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "checkbox", width: 50},
            {field: 'id', width: 80, title: 'ID', sort: true, align: "center"},
            {field: 'title', width: 80, title: '工作流名称', align: "center"},
            {field: 'information', width: 80, title: '描述信息', align: "center"},
            {title: '操作', minWidth: 100, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}


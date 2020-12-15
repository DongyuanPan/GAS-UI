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
 //加载参数模板表
function loadresSample(table,name) {
    var strurl='/resSample';
    if(name!=""){
        strurl='/resSample/name';
    }
    table.render({
        elem: '#currentTableId',
        url: strurl,
        where:{
            'name':name,
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
            {field: 'vmname', width: 100, title: '虚拟机名称', align: "center"},
            {field: 'count', width: 150, title: '台数', sort: true, align: "center"},
            {field: 'mirror', width: 130, title: '镜像', sort: true, align: "center"},
            {field: 'ram', width: 120, title: '内存', align: "center"},
            {field: 'mips', width: 100, title: '处理速度(MIPS)', align: "center"},
            {field: 'bw', width: 150, title: '带宽', sort: true, align: "center"},
            {field: 'cpu', width: 130, title: 'CPU核数', sort: true, align: "center"},
            {title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
function loadresource(table,name) {
    var strurl='/resource';
    if(name!=""){
        strurl='/resource/resname';
    }
    table.render({
        elem: '#currentTableId',
        url: strurl,
        where:{
            'name':name,
        },
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "checkbox", width: "10%"},
            {field: 'id', width: "10%", title: 'ID', sort: true, align: "center"},
            {field: 'person', width: "10%", title: '创建人', align: "center"},
            {field: 'name', width: "15%", title: '资源名称', align: "center"},
            {field: 'hostnum', width: "5%", title: 'Host数量', sort: true, align: "center"},
            {field: 'crtime', width: "20%", title: '创建时间', sort: true, align: "center"},
            {title: '操作', width: "20%", toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
function loadresourceInSimPage(table) {
    table.render({
        elem: '#currentTableIdOfResource',
        url: '/resource',
        cols: [[
            {type: "radio", width: "10%"},
            {field: 'id', width: "10%", title: 'ID', sort: true, align: "center"},
            {field: 'person', width: "10%", title: '创建人', align: "center"},
            {field: 'name', width: "25%", title: '资源名称', align: "center"},
            {field: 'hostnum', width: "10%", title: 'Host数量', sort: true, align: "center"},
            {field: 'crtime', width: "35%", title: '创建时间', sort: true, align: "center"}
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
            {type: "checkbox", width: "10%"},
            {field: 'id', width: "10%", title: 'ID', sort: true, align: "center"},
            {field: 'title', width: "30%", title: '工作流名称', align: "left"},
            {field: 'information', width: "40%", title: '描述信息', align: "left"},
            {title: '操作', width: "10%", toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
function loadWorkflowInSimPage(table) {
    table.render({
        elem: '#currentTableIdOfWorkflow',
        url: '/workflow',
        cols: [[
            {type: "radio", width: "10%"},
            {field: 'id', width: "10%", title: 'ID', sort: true, align: "center"},
            {field: 'title', width: "40%", title: '工作流名称', align: "left"},
            {field: 'information', width: "40%", title: '描述信息', align: "left"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
// 请求加载项目数据，渲染 table，根据是否要搜索及搜索条件，映射到不同url
function loadProject(table, title) {
    var strurl = '/project';
    if(title !== ""){
        strurl = '/project/title';
    }
    table.render({
        elem: '#currentTableId',
        url: strurl,
        where:{
            'title':title,
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
            {field: 'title', width: 200, title: '项目名称', align: "center"},
            {field: 'pic', width: 100, title: '负责人', align: "center"},
            {field: 'startDate', width: 150, title: '开始日期', sort: true, align: "center"},
            {field: 'endDate', width: 150, title: '结束日期', sort: true, align: "center"},
            {field: 'type', width: 200, title: '类型', sort: true, align: "center"},
            {title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
// 请求加载算法数据，渲染 table，根据是否要搜索及搜索条件，映射到不同url
function loadAlgorithm(table, name) {
    var strurl='/algorithm';
    if(name !== "") {
        strurl = '/algorithm/name';
    }
    table.render({
        elem: '#currentTableId',
        url: strurl,
        where:{
            'name':name,
        },
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "checkbox", width: "10%"},
            {field: 'id', width: "10%", title: 'ID', sort: true, align: "center"},
            {field: 'name', width: "30%", title: '算法名称', sort: true, align: "left"},
            {field: 'summary', width: "40%", title: '摘要', align: "left"},
            {title: '操作', minWidth: "10%", toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
function loadAlgorithmInSimPage(table) {
    table.render({
        elem: '#currentTableIdOfAlgorithm',
        url: '/algorithm',
        cols: [[
            {type: "radio", width: "10%"},
            {field: 'id', width: "10%", title: 'ID', sort: true, align: "center"},
            {field: 'name', width: "40%", title: '算法名称', sort: true, align: "left"},
            {field: 'summary', width: "40%", title: '摘要', align: "left"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
}
layui.use(['laypage', 'layer'], function () {
    var laypage = layui.laypage
        , layer = layui.layer, $ = layui.jquery
    ;


    $(".modBtn").click(function () {

        var btn = $(this);
        var id = btn.attr("id");

        var width = (btn.attr("width") || 500);
        var height = (btn.attr("height") || 500);
        var modPage = btn.attr("modPage") + "/";
        openWindow(modPage + id, 2, btn.attr("title") || "修改", width, height);

    });


    $(".delBtn").click(function () {

        var btn = $(this);
        var id = btn.attr("id");
        var msg = btn.attr("msg");
        var action = btn.attr("action");
        confirm(msg, action, {id: id});

    });


    $("#addBtn").click(function () {

        var btn = $(this);


        var width = (btn.attr("width") || 500);

        var height = (btn.attr("height") || 500);
        var addPage = btn.attr("addPage");

        openWindow(addPage, 2, (btn.attr("title") || "添加"), width, height);


    });


    laypage.render({
        elem: 'page'
        , count: $("#page").attr("totalCount")
        , theme: '#1E9FFF'
        , limit: $("#page").attr("pageSize"),
        curr: $("#page").attr("pageNumber"),

        jump: function (e, first) {

            if (!first) { //一定要加此判断，否则初始时会无限刷新
                $("#pageNumber").val(e.curr);
                $("#submitForm").submit();
            }


        }
    });


});



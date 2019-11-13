layui.use(['laypage', 'layer', 'form', 'laydate'], function () {
    var laypage = layui.laypage
        , layer = layui.layer, $ = layui.jquery,
        laydate = layui.laydate,
        form = layui.form;

    form.render();

    $(".section").each(function () {
        try {
            if (parseInt($(this).val(), 10) <= 0)
                $(this).val("");
        } catch (e) {

        }

    });


    lay('.date').each(function () {
        laydate.render({
            elem: this,
            trigger: 'click',
            theme: '#molv',
            format: 'yyyy-MM-dd',
            isInitValue: ($(this).val() != "")
        });
    });


    form.on('checkbox(allChoose)', function (data) {
        var child = $(":input[name='ids']");
        child.each(function (index, item) {
            item.checked = data.elem.checked;

        });
        form.render('checkbox');
    });


    form.on('checkbox(oneChoose)', function (data) {
        $("#allChoose").prop("checked", $(":input[name='ids']:checked").length == $(":input[name='ids']").length);
        form.render('checkbox');
    });


    $(document).on('click', "#delAllBtn", function (data) {

        if ($(":input[name='ids']:checked").length == 0) {
            layer.msg("请选择删除的条目");
            return;
        }

        var url = $(this).attr("delAllPage");
        var ids = [];
        $(":input[name='ids']:checked").each(function () {
            ids.push($(this).attr("keyId"))
        });

        confirm("确定要批量删除吗？", url, {ids: ids});


    });


    $(document).on('click', "#submitBtn", function (data) {

        var json = data.field;
        var checkBoxMap = new Map();
        $(".layui-form-checked").each(function (i) {
                var obj = $(this).prev();
                var javaType = obj.attr("javaType").toLowerCase();
                var searchType = obj.attr("searchType");


                if (checkBoxMap.has(obj.attr("name"))) {
                    var oneObj = checkBoxMap.get(obj.attr("name"));
                    if (searchType == 7) {
                        if (javaType == "integer") {
                            oneObj = parseInt(oneObj) + parseInt(obj.val());
                            checkBoxMap.set(obj.attr("name"), oneObj);
                        } else if (javaType == "float" || javaType == "double") {
                            oneObj = parseFloat(oneObj) + parseFloat(obj.val());
                            checkBoxMap.set(obj.attr("name"), oneObj);
                        }
                    } else {
                        oneObj.push(obj.val());
                        checkBoxMap.set(obj.attr("name"), oneObj);

                    }

                } else {
                    if (searchType == 7) {
                        checkBoxMap.set(obj.attr("name"), obj.val());
                    } else {
                        var oneObj = [];
                        oneObj.push(obj.val());
                        checkBoxMap.set(obj.attr("name"), oneObj);
                    }
                }
            }
        );
        checkBoxMap.forEach(function (item, key, mapObj) {

            if (!(item instanceof Array)) {
                $(":input[name='" + key + "']").remove();
                $("#submitForm").append("<input type='hidden' name='" + key + "' value='" + item.toString() + "'>")

            }

        });

        var radioMap = new Map();
        $(".layui-form-radioed").each(function (i) {
                var obj = $(this).prev();
                var oneObj = [];
                if (radioMap.has(obj.attr("name"))) {
                    oneObj = radioMap.get(obj.attr("name"));
                }
                oneObj.push(obj.val());
                radioMap.set(obj.attr("name"), oneObj);
            }
        );
        radioMap.forEach(function (item, key, mapObj) {
            if (item == "-1") {
                $(":input[name='" + key + "']").remove();
            }
        });


        $("#submitForm").submit();


        return false;


    });


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
        openWindow(addPage, 2, btn.attr("title") || "添加", width, height);


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

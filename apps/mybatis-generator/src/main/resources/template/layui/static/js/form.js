layui.use(['form', 'layedit', 'laydate', 'element', 'jquery', 'layer'], function () {
    var form = layui.form,
        laydate = layui.laydate,
        layedit = layui.layedit,
        layer = layui.layer,
        $ = layui.jquery;
    form.render();
    var editHtmlArr = [];
    lay('.edit').each(function () {
        var id = $(this).attr("id");
        var index = layedit.build(id, {"height": "180px"}); //建立编辑器
        editHtmlArr.push({"id": id, "index": index});
    });


    lay('.date').each(function () {
        laydate.render({
            elem: this
            , trigger: 'click',
            theme: '#molv',
            type: 'datetime',
            isInitValue: ($(this).val() != "")
        });
    });


    form.on('submit(go)', function (data) {


        var json = data.field;
        if (editHtmlArr.length > 0) {
            for (var i = 0; i < editHtmlArr.length; i++) {
                json[editHtmlArr[i].id] = layedit.getContent(editHtmlArr[i].index);
            }
        }


        var checkBoxMap = new Map();


        $("input:checkbox:checked").each(function (i) {
            var obj = $(this);
            var name = obj.attr("name");
            var value = obj.val();
            var javaType = obj.attr("javaType");
            if (javaType != null && javaType != "") {
                javaType = javaType.toLowerCase();
            }


            if (checkBoxMap.has(name)) {

                var oneObj = checkBoxMap.get(name);

                if (javaType == "integer") {
                    oneObj = parseInt(oneObj) + parseInt(value);

                } else if (javaType == "float" || javaType == "double") {

                    oneObj = parseFloat(value) + parseFloat(value);


                } else {
                    oneObj.push(value);
                }


                checkBoxMap.set(name, oneObj);

            } else {

                if (javaType == "integer") {


                    checkBoxMap.set(name, parseInt(value));

                } else if (javaType == "float" || javaType == "double") {

                    checkBoxMap.set(name, parseFloat(value));


                } else {
                    checkBoxMap.set(name, new Array(value));
                }


            }


        });


        checkBoxMap.forEach(function (item, key, mapObj) {
            try {
                if (item instanceof Array) {
                    json[key] = "," + item.join(",") + ","
                } else {
                    json[key] = item.toString();
                }


            } catch (e) {

            }

        });


        var action = $("#skForm").attr("action");
        var sussMsg = $(this).attr("sussMsg") || "添加成功";
        postData(action, json, function (res, request) {
            layer.msg(sussMsg, {time: 3000}, function () {
                top.location.reload();
            });

        });


        return false;


    });


});
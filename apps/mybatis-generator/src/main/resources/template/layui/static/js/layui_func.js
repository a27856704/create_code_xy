layui.use(['form', 'layedit', 'laydate', 'element', 'jquery', 'layer'], function () {
    var form = layui.form,
        laydate = layui.laydate,
        layedit = layui.layedit,
        layer = layui.layer,
        $ = layui.jquery;


    //自定义验证规则

    form.verify({

        double: function (value) {
            if (value == "")
                return;

            if (/^\d+$/.test(value) == false && /^\d+\.\d+$/.test(value) == false) {
                return '你输入的不是数字';
            }
        }
    });


});
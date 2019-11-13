function ajaxData(type, url, data, callback, beforeSend, complete, async, error) {
    $.ajax({
        type: type,
        url: url,
        data: data,
        async: async,
        cache: false,

        /* headers: {
         'jsonType': 'jsonType',
         'nbrcbefore': date,
         'nbrcafter': $.md5(nbrcKey + $.md5('' + date)),
         'keyid': nbrcKey,
         'nbrctoken': localStorage.getItem('nbrcToken') != null ? localStorage.nbrcToken : ''
         },*/
        dataType: 'json',
        beforeSend: beforeSend,
        success: function (result, textStatus, request) {


            if (result.success == true) {

                callback(result, request);
            } else {
                isFalse(result);
            }
        },
        error: error,
        complete: complete
    });
}

function postData(url, data, callback, beforeSend, complete, async, error) {


    ajaxData("post", url, data, callback, beforeSend, complete, async, error)

}


function postBodyData(url, data, callback) {


    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: url,
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result, textStatus, request) {


            if (result.success == true) {

                callback(result, request);
            } else {
                isFalse(result);
            }
        },
    });

}


function getData(url, data, callback, beforeSend, complete, async, error) {

    ajaxData("get", url, data, callback, beforeSend, complete, async, error)

}

function isFalse(result) {
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.msg(result.message);
    });
}


function confirm(msg, url, data) {


    layui.use(['layer'], function () {

        var layer = layui.layer;
        layer.confirm(msg, {
            btn: ['确定', '取消']//按钮
        }, function (index) {

            layer.close(index);

            postData(url, data, function (res, req) {
                location.reload();
            });
            var index = layer.load(0, {shade: [0.7, '#393D49']}, {shadeClose: true}); //0代表加载的风格，支持0-2

        });


    });


}


function openWindow(url, type, title, width, height) {


    layui.use(['layer'], function () {

        var layer = layui.layer;


        var aWidth = (width || 500) + "px";
        var aHeight = (height || 500) + "px";


        layer.open({
            type: type || 2,
            title: title || "信息",

            offset: 'auto',
            shade: [0.8, '#393D49'],
            area: [aWidth, aHeight],

            content: url,
            shadeClose: true,
            anim: 2,
            maxmin: true,
            maxWidth: 800,
            maxHeight: 780,

        });

    });


}
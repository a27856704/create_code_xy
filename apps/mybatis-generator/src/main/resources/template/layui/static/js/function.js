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

function getData(url, data, callback, beforeSend, complete, async, error) {

    ajaxData("get", url, data, callback, beforeSend, complete, async, error)

}

function isFalse(result) {
    layer.msg(result.message);


}


function confirm(msg, url, data) {

    layer.confirm(msg, {
        btn: ['确定', '取消']//按钮
    }, function (index) {
        layer.close(index);
        postData(url, data, function (res, req) {
            location.reload();
        });
        var index = layer.load(0, {shade: [0.7, '#393D49']}, {shadeClose: true}); //0代表加载的风格，支持0-2

    });


}


function openWindow(url, type, title, width, height, full) {

    var width = (width || 500);
    var height = (height || 500);

    var full = full || false;


    if (height == null || height == '') {
        height = ($(window).height() - 50);
    }


    if (height > $(window).height() || height <= 0) {
        height = ($(window).height() - 50);
    }

    height = height + "px";
    width = width + "px";


    var index = layer.open({
        type: type || 2,
        title: title || "信息",

        offset: 'auto',
        shade: [0.8, '#393D49'],
        area: [width, height],
        content: [url, 'yes'],
        shadeClose: true,
        anim: 2,
        maxmin: false,
        maxWidth: 800,

    });

    if (full)

        layer.full(index);


}
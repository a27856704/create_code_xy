$(function () {

    //用户现实注销退出
    $('#adminInfo').click(function (e) {
        stopPropagation(e)
        $('.balloon_box').slideDown();
    })
    $(document).bind("click", function (e) {
        $('.balloon_box').slideUp();
    })
    $('.balloon_box').bind("click", function (e) {
        stopPropagation(e)
    })
    $(".reset_btn").click(function () {
        document.getElementById("postForm").reset();
        $(".wu_editor_textarea").val("");
        $('.w-e-text').empty()
        $(".wu_uploader_list").empty();
    })
    // $("#postForm").on("reset",function () {
    //     console.log("5555")
    // })
    $("#searchBtn").click(function () {
        $("#searchForm").submit();
    });

    /**
     * 左侧导航栏
     */
    $('#leftMenu dt').click(function () {
        var _this = $(this)
        if (_this.hasClass("active")) {
            _this.next().slideUp().end().removeClass("active");
            return;
        }
        _this.next().slideDown().end().addClass("active").parent().siblings().children("dd").slideUp()
        _this.parent().siblings().children("dt").removeClass("active")
    })
})

//阻止冒泡事件
function stopPropagation(e) {//把事件对象传入
    if (e.stopPropagation) //支持W3C标准
        e.stopPropagation();
    else //IE8及以下浏览器
        e.cancelBubble = true;
}

/**
 * 数据校验
 * @param config
 *  config.formDom:验证表单的form id
 *  config.rules:验证规则
 *  required:true               必输字段
 email:true                  必须输入正确格式的电子邮件
 url:true                    必须输入正确格式的网址
 date:true                   必须输入正确格式的日期，日期校验ie6出错，慎用
 dateISO:true                必须输入正确格式的日期(ISO)，例如：2009-06-23，1998/01/22 只验证格式，不验证有效性
 number:true                 必须输入合法的数字(负数，小数)
 digits:true                 必须输入整数
 creditcard:true             必须输入合法的信用卡号

 equalTo:"#password"         输入值必须和#password相同
 accept:                     输入拥有合法后缀名的字符串（上传文件的后缀）
 maxlength:5                 输入长度最多是5的字符串(汉字算一个字符)
 minlength:10                输入长度最小是10的字符串(汉字算一个字符)
 rangelength:[5,10]          输入长度必须介于 5 和 10 之间的字符串")(汉字算一个字符)
 range:[5,10]                输入值必须介于 5 和 10 之间
 max:5                       输入值不能大于5
 min:10                      输入值不能小于10

 *  config.message: 不符合提示 爱写不写
 *  config.btn:按钮
 *  config.button: 按钮样式
 *  config.callback:回调函数
 */
function validatePost(config) {
    var _this = config.btn;
    var _formDom = config.formDom;
    var verifyEles = _formDom.find("*[data-verify]")
    var rules = new Object()
    var messages = new Object()
    verifyEles.each(function () {
        var _vThis = $(this)
        var verifyObj = _vThis.attr("name")
        if (verifyObj) {
            var verifyRules = eval('(' + _vThis.attr("data-verify-rules") + ')');
            rules[verifyObj] = verifyRules
        }

        var verifyMsg = _vThis.attr("data-verify-message")
        if (verifyMsg && verifyMsg.length > 2) {
            var verifyMessages = eval('(' + _vThis.attr("data-verify-message") + ')');
            messages[verifyObj] = verifyMessages
        }
    })

    $("*[append-type]").remove()
    var multipleEles = _formDom.find("*[javatype]:checked,*[javafiles]")
    var multipleNames = new Array()

    multipleEles.each(function () {
        var _mThis = $(this)
        var _name = _mThis.attr("name")
        if (multipleNames.indexOf(_name) < 0) {
            multipleNames.push(_mThis.attr("name"))
        }
    })
    for (var i = 0; i < multipleNames.length; i++) {
        var _curNames = multipleNames[i];
        var _curVal = "";
        multipleEles.each(function () {
            var _nThis = $(this)
            if (_nThis.attr("name") == multipleNames[i]) {
                if (_nThis.attr("javatype") == "string" || _nThis.attr("javafiles") == "string") {
                    _curVal += "," + _nThis.val()
                }

                if (_nThis.attr("javatype") == "integer") {
                    _curVal = parseInt(_curVal || 0) + parseInt(_nThis.val())
                }

            }
        })

        if (typeof _curVal == "string")
            _curVal += ","
        _formDom.append('<input type="hidden" append-type name="' + _curNames.substring(0, _curNames.length - 1) + '"  value="' + _curVal + '" />')
    }


    config.formDom.validate({
        rules: rules,
        messages: messages,
        onkeyup: false,
        focusCleanup: true,
        success: "valid",
        submitHandler: function (form) {
            var defaultClassName = _this.attr('class')

            if (defaultClassName.indexOf('loading_btn') > -1)
                return;

            var replaceName = defaultClassName.replace(config.button || 'primary_btn', 'loading_btn')
            _this.attr("class", replaceName);
            $(form).ajaxSubmit({
                dataType: 'json',
                headers: {},
                success: function (backdata) {
                    _this.attr("class", defaultClassName);
                    if (typeof config.callback == 'function') {
                        if (backdata.success) {
                            config.callback(backdata)
                            return;
                        }

                        if (backdata.data.code == 1039) {
                            layer.msg(backdata.message, {icon: 2, title: '提示'});
                            location.href = "/login";
                            return;
                        }
                        layer.msg(backdata.message, {icon: 2, title: '提示'});
                        config.callback(backdata)
                    }
                }
            });
        }
    })
}

/**
 * 异步请求数据
 * @param obj
 *  icon:0:提示，1：成功，2：错误,3:问号4：加密，5：哭脸，6：笑脸
 *  type:请求方式：post，get，put
 *  url：请求地址
 *  showTxt:数据返回提示
 *  confirmTxt：操作前询问
 *  id:数据id
 *  status:数据状态
 */
function dataChange(obj) {
    var _this = $(obj)
    var _type = _this.attr("data-type") || "post"
    var _newStatus = _this.attr('data-newStatus') || ''
    var _url = _this.attr("data-url") || '';
    var _showTxt = _this.attr("data-showTxt") || "操作成功";
    var _confirmTxt = _this.attr("data-confirmTxt") || "确定要操作吗?";
    var _id = _this.attr("data-id") || '';
    var data = new Object();
    if (_url == '')
        layer.msg('请求地址未填写，太不认真了', {icon: 5})
    if (_id != '')
        data.id = _id;
    if (_newStatus != "")
        data.status = _newStatus
    layer.confirm(_confirmTxt, {icon: 3, title: '提示'}, function (index) {
        layer.close(index);
        var loadIndex = layer.load(0, {
            shade: [0.3, '#fff'] //0.1透明度的白色背景
        });

        getData({
            url: _url,
            type: _type,
            formData: data
        }, function (backdata) {
            layer.close(loadIndex)
            if (backdata.success) {
                layer.msg(_showTxt, function () {
                    location.reload();
                })
                return;
            }

            if (backdata.data.code == 1039) {
                layer.msg(backdata.message, {icon: 2, title: '提示'}, function () {
                    location.href = "/login";
                });
                return;
            }
            layer.msg(backdata.message);
        })
    });
}

/**
 * aja数据请求
 * @param config
 *  config.url
 *  config.type
 *  config.formData
 *  config.callback
 */
function getData(config, callback) {
    $.ajax({
        type: config.type,
        dataType: 'json',
        url: config.url,
        data: config.formData
    }).done(function (backdata, status, headers, config) {

        if (backdata.success) {
            callback(backdata)
            return;
        }

        if (backdata.data && backdata.data.code && backdata.data.code == 1039) {
            layer.msg(backdata.message, {icon: 2, title: '提示'}, function () {
                location.href = "/login";
            });
            return;
        }
        layer.msg(backdata.message);
        callback(backdata)

    }).fail(function (data, status, headers, config) {
        layer.close()
        if (status !== 'success') {
            layer.alert('网络连接失败，请刷新', {icon: 2})
        }
    })
}


jQuery.fn.extend({
    editorUploadImg(opts) {
        var editorThis = $(this)
        var editorDefault = {
            uploadImgServer: "",
            inputName: ""
        }
        var editorOptions = $.extend({}, editorDefault, opts || {});
        if (editorOptions.uploadImgServer == "") {
            layer.msg("上传地址不能为空")
            return
        }
        var Editor = window.wangEditor
        var myEditor = new Editor(editorThis.selector)
// 配置服务器端地址
        myEditor.customConfig.uploadImgServer = editorOptions.uploadImgServer
        myEditor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
        myEditor.customConfig.uploadFileName = 'files';
        myEditor.customConfig.uploadImgTimeout = 30000
        myEditor.customConfig.uploadImgHooks = {
            customInsert: function (insertImg, result, editor) {
                // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
                // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

                // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
                var urls = result.data.path.split(',');
                var website = result.data.imgWebSite;

                for (var urlI = 0; urlI < urls.length; urlI++)
                    if (urls[urlI])
                        insertImg(website + '/' + urls[urlI]);

                // result 必须是一个 JSON 格式字符串！！！否则报错
            }
        }
        myEditor.customConfig.customAlert = function (info) {
            // info 是需要提示的内容
            layer.msg(info)
        }
        var prevObj = editorThis.prev()
        var defaultEditorHtml = prevObj.text();
        $(this).parent().append('<textarea class="wu_editor_textarea" id="editorText' + editorOptions.inputName + '" name="' + editorOptions.inputName + '">' + defaultEditorHtml + '</textarea>')
        myEditor.customConfig.onchange = function (htmlData) {
            $("#editorText" + editorOptions.inputName).val(htmlData)
        }
        myEditor.create()
        // var defaultEditorHtml=editorThis.html();
        prevObj.empty()
        // $("#editorText"+editorOptions.inputName).val(defaultEditorHtml)
        myEditor.txt.html(defaultEditorHtml)

    }
})

/**
 * 异步图片上传
 */
jQuery.fn.extend({
    fileUploadAsyn(opts) {
        var uploadThis = $(this);
        $list = uploadThis.parent().prev();
        var defaultOpts = {
            auto: true,
            fileName: "files",
            savePath: "",
            isMultiple: false,
            // fileNumLimit:100,
            //文件上传地址
            server: "",
            //文件合并地址
            mergeServer: "",
            //文件删除地址
            deleteServer: "",
            //是否可选择同一文件
            duplicate: true,
            //是否分片上传
            chunked: true,
            //分片文件大小
            chunkSize: 1 * 1024 * 1024,
            disableGlobalDnd: false,
            //accept 接收类型定义
            accept: null,
            acceptType: "image",
            //thumb定义
            thumbWidth: 50,
            thumbHeight: 50,
            // 图片质量，只有type为`image/jpeg`的时候才有效。
            thumbQuality: 70,
            // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
            thumbAllowMagnify: false,
            //swf地址
            swfPath: 'http://localhost:3000/public/js/lib/webupload/Uploader.swf',
            // 是否允许裁剪。
            thumbCrop: true,

            // 为空的话则保留原有图片格式。
            // 否则强制转换成指定的类型。
            thumbType: 'image/jpeg'
        }
        var uploadOptions = $.extend({}, defaultOpts, opts || {});
        if (uploadOptions.server == "") {
            layer.msg("上传地址不能为空")
            return
        }
        console.log(uploadOptions)
        var uploader = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: uploadOptions.auto,
            // swf文件路径
            swf: uploadOptions.swfPath,

            // fileNumLimit:uploadOptions.fileNumLimit,
            // 文件接收服务端。
            // server: 'http://127.0.0.1:8888/webuploader',
            server: uploadOptions.server,
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {
                id: uploadThis.selector,
                multiple: uploadOptions.isMultiple
            },

            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            //是否可选择同一文件
            duplicate: uploadOptions.duplicate,
            chunked: uploadOptions.chunked,
            //分片文件大小
            chunkSize: uploadOptions.chunkSize,
            //拖拽的容器
            dnd: uploadThis.selector,
            //是否禁用拖拽
            disableGlobalDnd: uploadOptions.disableGlobalDnd,
            //指定接受哪些类型的文件
            accept: uploadOptions.accept,
            //缩略图
            thumb: {
                width: uploadOptions.thumbWidth,
                height: uploadOptions.thumbHeight,

                // 图片质量，只有type为`image/jpeg`的时候才有效。
                quality: uploadOptions.thumbQuality,

                // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
                allowMagnify: uploadOptions.thumbAllowMagnify,

                // 是否允许裁剪。
                crop: uploadOptions.thumbCrop,

                // 为空的话则保留原有图片格式。
                // 否则强制转换成指定的类型。
                type: uploadOptions.thumbType
            }

        });
        // $("#ctlBtn").on("click", function () {
        //     uploader.upload()
        // })
        // 当有文件被添加进队列的时候
        uploader.on('fileQueued', function (file) {
            // $list.append( '<div id="' + file.id + '" class="item"><div class="item_text">' +
            //     '<span class="info">文件：' + file.name + '</span>' +
            //     '<span class="state icon_font wait">等待上传...</span>' +
            //     '</div> </div>' );
            var $li = $('<div id="' + file.id + '"  class="wu_uploader_item">\n' +
                '                          <div class="wu_uploader_thumb icon_font"><img src="" id="img_' + file.id + '"/></div>\n' +
                '                          <div class="wu_uploader_progress">\n' +
                '                            <div class="wu_upload_state"><span>' + file.name + '</span><span class="state icon_font wait">等待上传</span></div>\n' +
                '                            <div class="progress progress-striped active">\n' +
                '                              <div class="progress-bar" role="progressbar" style="width: 0%"></div>\n' +
                '                            </div>\n' +
                '                          </div>\n' +
                '                        </div>')
            var $img = $li.find("img")
            if (uploadOptions.isMultiple)
                uploadThis.parent().prev().append($li)
            if (!uploadOptions.isMultiple)
                uploadThis.parent().prev().html($li)
            if (uploadOptions.acceptType == "image")
                uploader.makeThumb(file, function (error, src) {
                    if (error) {
                        $('#img_' + file.id).attr('src', "/images/default_uploader_img.png")
                        // $img.replaceWith("<span>不能预览</span>")
                        return;
                    }
                    $('#img_' + file.id).attr('src', src)
                }, uploadOptions.thumbWidth, uploadOptions.thumbHeight);

        });
// 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress .progress-bar');

            // 避免重复创建
            // if ( !$percent.length ) {
            //     $percent = $('<div class="progress progress-striped active">' +
            //         '<div class="progress-bar" role="progressbar" style="width: 0%">' +
            //         '</div>' +
            //         '</div>').appendTo( $li ).find('.progress-bar');
            // }

            $li.find('span.state').text('文件上传中...');

            $percent.css('width', percentage * 100 + '%');
        });
        uploader.on('uploadSuccess', function (file, response) {

            var fileId = response.data.fileId;
            var fileInfo = response.data.fileInfo;
            getData({
                type: "post",
                url: uploadOptions.mergeServer,//"http://127.0.0.1:8888/mergeFileInfo",
                formData: {
                    id: fileInfo.id,
                    name: fileInfo.name,
                    lastModifiedDate: fileInfo.lastModifiedDate,
                    size: parseInt(fileInfo.size),
                    savePath: uploadOptions.savePath
                }

            }, function (backdata) {
                if (backdata.success) {
                    var filePath = backdata.data.filePath;
                    $('#' + file.id).find('span.state').text('已上传').addClass("success").removeClass("wait");
                    if (uploadOptions.isMultiple) {
                        $("#" + fileId).append("<input type='hidden' javafiles='string' name='" + uploadOptions.fileName + "' value='" + filePath + "'/>")
                    } else {
                        $("#" + fileId).append("<input type='hidden' name='" + uploadOptions.fileName + "' value='" + filePath + "'/>")
                    }
                }
            })

        });

        uploader.on('uploadError', function (file) {
            $('#' + file.id).find('p.state').text('上传出错').addClass("error").removeClass("wait");
        });

        uploader.on('uploadComplete', function (file) {
            // $( '#'+file.id ).find('.progress').fadeOut();
        });
        //删除当前上传的文件
        $list.on("click", ".wu_uploader_thumb", function () {
            var _thumbThis = $(this)
            layer.confirm("确定删除该文件吗", {icon: 3, title: '提示'}, function (index) {
                layer.close(index)
                var _uploadImgPath = _thumbThis.parent().children("input").val();

                getData({
                    type: "post",
                    url: uploadOptions.deleteServer,//"http://127.0.0.1:8888/mergeFileInfo",
                    formData: {
                        fileName: _uploadImgPath
                    }
                }, function (backdata) {
                    if (backdata.success) {
                        if (!BooleanUndefined(_thumbThis.parent().attr("id")))
                            uploader.removeFile(_thumbThis.parent().attr("id"))
                        _thumbThis.parent().remove()
                    }
                })
            })
        })
    }
})

jQuery.fn.extend({
    uploadPreview: function (opts) {
        var _self = this,
            _this = $(this);
        var defaults = {
            width: 100,
            height: 100,
            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
            Callback: function () {
            }
        }
        var options = $.extend({}, defaults, opts || {});


        _self.getObjectURL = function (file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
        _this.change(function () {
            if (this.value) {
                if (!RegExp("\.(" + options.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                    layer.msg("选择文件错误,图片类型必须是" + options.ImgType.join("，") + "中的一种")
                    this.value = "";
                    return false
                }

                var imgFiles = this.files, imgHtml = ""
                for (var i = 0; i < imgFiles.length; i++) {
                    imgHtml += '<img src="' + _self.getObjectURL(imgFiles[i]) + '" style="width: ' + options.width + 'px;height: ' + options.height + 'px;">   '
                }
                $(this).parent().prev().html(imgHtml)
                options.Callback()
            }
        })
    }
});

function BooleanUndefined(val) {
    return !val || val == undefined || typeof val == "undefined";
}

function HTMLDecode(text) {
    var temp = document.createElement("div");
    temp.innerHTML = text;
    var output = temp.innerText || temp.textContent;
    temp = null;
    return output;
}
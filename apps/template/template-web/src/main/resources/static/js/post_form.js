$(function () {
    $("#postBtn").click(function () {
        var _this = $(this);
        var _link = _this.attr("data-success-link") || "";
        var _msgText = _this.attr("data-msg-text") || "提交成功";
        var config = {

            formDom: _this.parents("form"),
            btn: _this,
            callback: function (backdata) {
                if (backdata.success) {
                    layer.msg(_msgText, {icon: 1, title: "提示"}, function () {
                        if (_link == "") {
                            location.reload()
                            return
                        }
                        location.href = _link
                    })
                } else {
                    layer.msg(backdata.message, {icon: 2, title: "提示"})
                }
            }
        }
        validatePost(config)
        _this.parents("form").submit()
    })

    var editors = $('.wu_editor')
    for (var eI = 0; eI < editors.length; eI++) {
        var _inputname = $('.wu_editor').eq(eI).attr("data-editorname")
        var boxClass = "editor" + _inputname + eI
        $('.wu_editor').eq(eI).addClass(boxClass);
        $("." + boxClass).editorUploadImg({
            uploadImgServer: globalConfig.editorObj.uploadImgServer,//'http://127.0.0.1:8888/editorUpload',
            inputName: _inputname
        })
    }


    /**
     * 同步上传
     * 参数
     width: 100,
     height: 100,
     ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
     */
    $(".upload_img").uploadPreview({})


    /**
     * 异步文件上传
     * 参数定义
     auto: true,
     fileName:"files" 默认文件地址名称
     isMultiple:false: 是否需要合并地址提交 默认flase
     server: "",  这个为必传项
     //是否可选择同一文件
     duplicate: false,
     //是否分片上传
     chunked: true,
     //分片文件大小
     chunkSize: 1 * 1024,

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

     // 是否允许裁剪。
     thumbCrop: true,

     // 为空的话则保留原有图片格式。
     // 否则强制转换成指定的类型。
     thumbType: 'image/jpeg'
     */

    var uploaderPickers = $('.wu_uploader_picker')
    for (var uploaderI = 0; uploaderI < uploaderPickers.length; uploaderI++) {
        var uploaderFileName = uploaderPickers.eq(uploaderI).attr("data-filename")
        var savePath = uploaderPickers.eq(uploaderI).attr("data-savepath")
        var isMultiple = uploaderPickers.eq(uploaderI).attr("data-multiple") + ""
        var uploaderFileClass = "pick" + uploaderFileName + uploaderI
        uploaderPickers.eq(uploaderI).addClass(uploaderFileClass)
        var uploadConfig = {
            server: globalConfig.asyncUploadObj.server,// 'http://127.0.0.1:8888/webuploader',
            swfPath: globalConfig.asyncUploadObj.swfPath,//'http://localhost:3000/public/js/lib/webupload/Uploader.swf',
            mergeServer: globalConfig.asyncUploadObj.mergeServer,
            deleteServer: globalConfig.asyncUploadObj.deleteServer,
            fileName: "files"
        }
        if (!BooleanUndefined(uploaderFileName))
            uploadConfig.fileName = uploaderFileName
        if (!BooleanUndefined(savePath))
            uploadConfig.savePath = savePath

        if (isMultiple == "true") {
        }
        uploadConfig.isMultiple = true
        if (isMultiple == "false") {
            uploadConfig.isMultiple = false
            uploadConfig.fileNumLimit = 1
        }


        $("." + uploaderFileClass).fileUploadAsyn(uploadConfig);
    }


    // $(".wu_uploader_list").on("click", ".wu_uploader_thumb", function () {
    //     const _this = $(this)
    //     layer.confirm("确定删除该文件吗", {icon: 3, title: '提示'}, function (index) {
    //         layer.close(index)
    //         _this.parent().remove()
    //     })
    // })


})


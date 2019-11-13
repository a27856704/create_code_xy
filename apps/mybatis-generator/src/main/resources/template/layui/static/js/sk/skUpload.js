layui.define(['layer', 'element', 'upload', 'form'], function (exports) {

    var upload = layui.upload;
    var form = layui.form;

    var mods = function (config) {

        this.url = "/back/postUpload";
        this.field = "file";

        this.config = {
            elem: "uploadBtn",
            multiple: false,
            uploadFlag: "img",
            returnElem: "upload",
            accept: "images",
            acceptMime: "image/gif,image/jpeg,image/png"
        }

        this.config = $.extend(this.config, config);

    }


    mods.prototype.render = function () {
        var _this = this;
        var c = _this.config;

        if (c.multiple) {
            this.url = "/back/postUploads";
            this.field = "files";
        }


        upload.render({
            elem: "#" + c.elem //绑定元素
            , url: _this.url //上传接口
            , data: {
                uploadFlag: c.uploadFlag
            }
            , multiple: c.multiple
            , field: _this.field
            , accept: c.accept
            , acceptMime: c.acceptMime


            , before: function () {
                if ($("#" + c.returnElem).val() != undefined) {
                    $("#" + c.returnElem).val("");
                }
            }
            , done: function (res) {
                var path = res.data.path;


                if ($("#" + c.returnElem).val() == undefined) {

                    $("#" + c.elem).before("   <input type=\"hidden\"  name=\"" + c.returnElem + "\" id=\"" + c.returnElem + "\"/>");
                    form.render();
                }


                if (c.multiple) {
                    path = $("#" + c.returnElem).val() + path;

                }
                $("#" + c.returnElem).val(path);
                form.render();


                //  $("#" + c.returnElem).val(res.data.path);

            }
            , error: function (res) {
                //请求异常回调
                console.log(res);
            },


        });
    }


    exports('skUpload', function (config) {
        var _this = new mods(config);
        _this.render();
        return _this;
    });


});
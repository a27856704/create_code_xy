var globalConfig = {
    //富文本编辑器
    editorObj: {
        uploadImgServer: 'http://127.0.0.1:8091/postUploads'//文本编辑器上传地址
    },
    //异步上传
    asyncUploadObj: {
        server: 'http://127.0.0.1:8091/webUploader',//异步上传地址
        swfPath: 'http://127.0.0.1:8091/js/lib/webupload/Uploader.swf',//flash 文件地址
        mergeServer: 'http://127.0.0.1:8091/mergingChunks',//合并文件请求地址
        deleteServer: 'http://127.0.0.1:8091/delFile',//删除文件地址
    },

}

//var website="http://nmn.gpqqg.com";
var website="http://localhost:8092";

var globalConfig = {
    //富文本编辑器
    editorObj: {
        uploadImgServer: website+'/postUploads'//文本编辑器上传地址
    },
    //异步上传
    asyncUploadObj: {
        server: website+'/webUploader',//异步上传地址
        swfPath: website+'/js/lib/webupload/Uploader.swf',//flash 文件地址
        mergeServer: website+'/mergingChunks',//合并文件请求地址
        deleteServer: website+'/delFile',//删除文件地址
    },

}
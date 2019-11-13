layui.config({
base: '/js'
}
).extend({
skUpload: '/sk/skUpload',

}).use(['form', 'skUpload'], function () {

var form = layui.form;


<#list fileList as item>

    var ${item.name+"Upload"} = layui.skUpload;

    ${item.name+"Upload"} = ${item.name+"Upload"}({
    elem: "${item.name+'Btn'}",
    multiple: ${(item.inputType==9)?string('true','false')},
    uploadFlag: "${entityName+"_"+item.name}",
    returnElem: "${item.name}"
    });

</#list>


});
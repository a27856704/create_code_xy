layui.define(['layer', 'element', 'table'], function (exports) {

    var $ = layui.jquery
        , table = layui.table
    ;


    var mods = $.extend({}, table);

    exports('skTable', mods);


});
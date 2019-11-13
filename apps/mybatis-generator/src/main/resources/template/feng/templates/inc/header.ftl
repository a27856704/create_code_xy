<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head th:fragment="header">
    <meta charset="UTF-8">
    <title>${projectName}</title>
    <link rel="stylesheet" type="text/css" href="/style/list.css" th:href="@{/style/list.css}">
    <link rel="stylesheet" type="text/css" href="/style/form.css" th:href="@{/style/form.css}">
    <link rel="stylesheet" type="text/css" href="/js/lib/webupload/webuploader.css"
          th:href="@{/js/lib/webupload/webuploader.css}">
    <script src="/js/lib/jquery/jquery.min.js" th:src="@{/js/lib/jquery/jquery.min.js}"></script>
    <script src="/js/lib/layer/2.4/layer.js" th:src="@{/js/lib/layer/2.4/layer.js}"></script>
    <script src="/js/config.js" th:src="@{/js/config.js}"></script>
    <script src="/js/main.js" th:src="@{/js/main.js}"></script>
    <script src="/js/lib/My97DatePicker/4.8/WdatePicker.js"
            th:src="@{/js/lib/My97DatePicker/4.8/WdatePicker.js}"></script>
    <script src="/js/pager.js" th:src="@{/js/pager.js}"></script>
    <script src="/js/lib/jquery/jquery.form.js" th:src="@{/js/lib/jquery/jquery.form.js}"></script>
    <script src="/js/lib/validation/jquery.validate.js" th:src="@{/js/lib/validation/jquery.validate.js}"></script>
    <script src="/js/lib/validation/validate-methods.js" th:src="@{/js/lib/validation/validate-methods.js}"></script>
    <script src="/js/lib/validation/messages_zh.js" th:src="@{/js/lib/validation/messages_zh.js}"></script>
    <script src="/js/lib/webupload/webuploader.min.js" th:src="@{/js/lib/webupload/webuploader.min.js}"></script>
    <script src="/js/lib/wangEditor/wangEditor.min.js" th:src="@{/js/lib/wangEditor/wangEditor.min.js}"></script>
    <script src="/js/post_form.js" th:src="@{/js/post_form.js}"></script>
</head>


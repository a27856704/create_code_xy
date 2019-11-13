<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head th:fragment="header">
    <meta charset="UTF-8">
    <title>${projectName}</title>
    <link rel="stylesheet" href="/frame/layui/css/layui.css" th:href="@{/frame/layui/css/layui.css}">

    <link rel="stylesheet" href="/frame/static/css/style.css" th:href="@{/frame/static/css/style.css}">
    <link rel="icon" href="/frame/static/image/code.png" th:href="@{/frame/static/image/code.png}">
    <script src="../js/jquery-3.2.1.min.js" th:src="@{/js/jquery-3.2.1.min.js}"></script>
    <script type="text/javascript" src="/frame/layui/layui.js"></script>
    <script src="../js/function.js" th:src="@{/js/function.js}"></script>
    <script src="../js/ajaxfileupload.js" th:src="@{/js/ajaxfileupload.js}"></script>
</head>


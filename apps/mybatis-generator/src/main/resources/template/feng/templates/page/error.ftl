<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>${projectName}</title>

    <link rel="stylesheet" type="text/css" href="/style/login.css">
    <script src="/js/lib/jquery/jquery.min.js"></script>
    <script src="/js/lib/jquery/jquery.form.js"></script>
    <script src="/js/lib/layer/2.4/layer.js" th:src="@{/js/lib/layer/2.4/layer.js}"></script>
    <script src="/js/lib/validation/jquery.validate.js"></script>
    <script src="/js/lib/validation/validate-methods.js"></script>
    <script src="/js/lib/validation/messages_zh.js"></script>
    <script src="/js/main.js"></script>
</head>
<body>
<div class="login_box">
    <div class="login_title">
        <ul>
            <li>系统异常</li>
            <li th:text="${r'${msg}'}"></li>
        </ul>
    </div>

</div>
</body>

</html>
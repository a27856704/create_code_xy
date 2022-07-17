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
            <li>${projectName}</li>
            <li>● 更简单 ● 更集中 ● 更易操作</li>
        </ul>
    </div>
    <div class="login_form">
        <div class="login_form_box">
            <form id="formBox" action="/" method="post">
                <div class="form_box">
                    <label>用户名/Username</label>
                    <label>
                        <input type="text" name="username" id="username" autocomplete="on" placeholder="请输入用户名"
                               required/>
                    </label>
                </div>
                <div class="form_box">
                    <label>密码/Password</label>
                    <label>
                        <input type="password" placeholder="请输入密码" id="password" autocomplete="on" name="password"
                               required/>
                    </label>
                </div>

                <div class="form_box">
                    <p class="button login_btn" id="loginBtn" type="button"><span class="loading_icon"></span>登录</p>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $("#loginBtn").click(function () {

            var loadIndex = layer.load(0, {
                shade: [0.3, '#fff'] //0.1透明度的白色背景
            });
            var data = new Object();
            data.username = $("#username").val();
            data.password = $("#password").val();
            getData({type: 'post', url: "/login", formData: data}, function (backdata) {

                layer.close(loadIndex);
                if (backdata.success) {
                    location.href = "/";
                    return;
                } else {
                    layer.msg(backdata.message);
                }


            });


        });
    })
</script>
</html>
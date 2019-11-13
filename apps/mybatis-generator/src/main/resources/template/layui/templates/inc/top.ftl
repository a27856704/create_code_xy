<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<div class="layui-header" th:fragment="top">
    <div class="layui-logo">${projectName}</div>

    <ul class="layui-nav layui-layout-right">
        <li class="layui-nav-item">
            <a href="javascript:;">

                <span th:if="${r'${session.user_session}'}" th:text="${r'${session.user_session}'}"></span>
            </a>

        </li>
        <li class="layui-nav-item"><a th:href="${r'@{/logout}'}">退了</a></li>
    </ul>
</div>







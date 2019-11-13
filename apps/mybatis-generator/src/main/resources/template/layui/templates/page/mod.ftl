<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">

<head th:replace="inc/header::header"/>


<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

    <#assign add=false>
    <form class="layui-form" th:action="${r'${action}'}" th:id="skForm" style="padding: 15px;">
        <#list list as item>
            <#include "filed/${item.inputTag}.ftl">
        </#list>
        <#include "filed/SUBMIT.ftl">
    </form>
</div>
<script type="text/javascript" th:inline="javascript" src="/js/form.js"></script>
<script type="text/javascript" th:inline="javascript" src="/js/mod.js"></script>
<script type="text/javascript" th:inline="javascript" src="/js/${entityName}/${entityName}.js"></script>
</body>
</html>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head th:replace="inc/header::header"/>
<body>
<div class="container_main">
    <div th:replace="inc/top::top"/>
    <div th:replace="inc/menu::menu('${menuName}','list')"/>
    <div class="container_box">
        <div class="container_block">
            <div class="container_title" th:text="${r'${menuModel}'}">图书管理</div>
            <div class="post_box">
                <#assign add=true>
                <form id="postForm" th:action="${r'${action}'}" method="post">
                    <div class="container_fluid clear_box">
                        <#list list as item>
                            <#if item.inputType==6 ||item.inputType==8|| item.inputType==9 >
                                <div class="row col_12">
                                    <#include "filed/${item.inputTag}.ftl">

                                </div>
                            <#else>
                                <div class="row col_6">
                                    <#include "filed/${item.inputTag}.ftl">

                                </div>
                            </#if>
                        </#list>
                        <div class="row col_12">
                            <#include "filed/SUBMIT.ftl">

                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div th:replace="inc/footer::footer"/>
    </div>
</div>
</body>
</html>
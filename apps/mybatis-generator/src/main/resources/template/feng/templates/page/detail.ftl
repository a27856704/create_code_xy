<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head th:replace="inc/header::header"/>
<body>
<div class="container_main">
    <div th:replace="inc/top::top"/>
    <div th:replace="inc/menu::menu(${r'${menuModelName}'},${r'${menuModelPage}'})"/>
    <div class="container_box">
        <div class="container_block">
            <div class="container_title" th:text="${r'${menuModel}'}">模块名称</div>
            <div class="container_tab">
                <ul>
                    <li class="active"><a href="#" th:text="${r'${detailTitle==null?'+'\'详情\''+':detailTitle}'}">详情</a></li>
                </ul>
            </div>
            <div class="post_box">
                <#assign add=true>
                <form id="postForm" th:action="${r'${action}'}" method="post">
                    <div class="container_fluid clear_box">
                        <#list list as item>
                           <#if item?? && item.inputTag?? >
                                <#if item.inputType==6 ||item.inputType==8|| item.inputType==9 >
                        <div class="row col_12">
                            <#include "filedRead/${item.inputTag}.ftl">
                        </div>
                                <#else>
                        <div class="row col_12">
                            <#include "filedRead/${item.inputTag}.ftl">
                        </div>
                                </#if>
                           </#if>
                        </#list>
                        <div class="row col_12">
                            <#include "filedRead/SUBMIT.ftl">
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
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">

<head th:replace="inc/header::header"/>

<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

    <div th:replace="inc/top::top"/>

    <div th:replace="inc/menu::menu('${menuName}','list')"/>


    <div class="layui-body min-auto">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend th:text="${r'${currentMenu}'}">${tableRemarks}列表</legend>
        </fieldset>


        <form id="submitForm" class="layui-form" style="width: 100%">
            <table class="layui-table" lay-skin="nob" lay-even lay-size="sm" style="margin-bottom: 10px;border: none;">
                <tr>
                    <th>
                        <input type="hidden" name="pageNumber" id="pageNumber" th:value="${r'${page.currentPage}'}"/>
                        <#assign showSearchBtn=false>
                        <#list list as item>
                        <#if ((item.searchFlag!0)>0)>
                            <#include "search/${item.inputTypeEnum}.ftl">
                        </#if>
                        <#if ((item_index % 3==0) && item_index>0)>
                    </th>
                </tr>
                <tr>
                    <th>
                        </#if>
                        <#assign showSearchBtn=true>
                        </#list>
                        <#if showSearchBtn>
                            <button class="layui-btn" type="button" lay-filter="go" id="submitBtn"><i
                                        class="layui-icon">&#xe615;</i>搜索
                            </button>
                        </#if>
                        <button
                                th:if="${r'${T('+manageController+').contains(session?.rights_session,addPage)}'}"
                                type="button" title="添加" width="800" height="0" th:attr="addPage=${r'${addPage}'}"
                                class="layui-btn" id="addBtn"><i class="layui-icon">&#xe608;</i>添加
                        </button>
                        <button
                                th:if="${r'${T('+manageController+').contains(session?.rights_session,delAllPage)}'}"
                                type="button" title="删除" width="800" height="600"
                                th:attr="delAllPage=${r'${delAllPage}'}"
                                class="layui-btn" id="delAllBtn"><i class="layui-icon layui-icon-delete"></i>批量删除
                        </button>

                    </th>
                </tr>
            </table>
        </form>

        <div class="layui-form" style="margin-left: 5px;">
            <table class="layui-table" lay-even lay-size="sm">
                <thead>
                <tr>
                    <th style="width: 30px;"><input type="checkbox" id="allChoose" name="allChoose" lay-skin="primary"
                                                    lay-filter="allChoose"></th>
                    <th>序号</th>
                    <#list list as item>
                        <#if (item.showListPage!false)>
                            <th>${item.descName}</th>
                        </#if>
                    </#list>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr th:each="item,stat:${r'${list}'}">
                    <td><input lay-filter="oneChoose" type="checkbox" name="ids" th:attr="keyId=${r'${item.id}'}"
                               lay-skin="primary"></td>
                    <td th:text="${r'${stat.count+page.begin}'}">序号</td>
                    <#list list as item>
                        <#if (item.showListPage!false)>
                            <#if item.javaType!='Date'>


                                <#if item.valueList?? && (item.valueList?size > 0)>

                                    <td th:text="${r'${T('+manageController+').containsValue('+'\''+item.valueString+'\',item.'+item.name+')}'}">${item.nameDesc!''}</td>

                                <#else>
                                    <td th:text="${r'${item.'+item.name+'}'}">${item.nameDesc!''}</td>
                                </#if>



                            <#else>
                                <td th:text="${r'${#dates.format(item.'+item.name+',\'yyyy-MM-dd HH:mm:ss\')}'}">${item.nameDesc!''}</td>
                            </#if>
                        </#if>
                    </#list>
                    <td>
                        <button
                                th:if="${r'${T('+manageController+').contains(session?.rights_session,modPage)}'}"

                                class="layui-btn layui-btn-small modBtn" type="button"
                                th:id="${r'${item.id}'}"
                                title="修改"
                                width="800"
                                height="0"
                                th:attr="modPage=${r'${modPage}'}"><i class="layui-icon">&#xe642;</i>修改
                        </button>
                        <button
                                th:if="${r'${T('+manageController+').contains(session?.rights_session,deleteAction)}'}"
                                class="layui-btn  layui-btn-small layui-btn-danger delBtn" type="button"
                                th:id="${r'${item.id}'}"
                                msg="确认要删除?"
                                th:attr="action=${r'${deleteAction}'}"><i class="layui-icon">&#xe640;</i>删除
                        </button>

                    </td>


                </tr>


                </tbody>


            </table>

            <div id="page"
                 th:attr="pageNumber=${r'${page.currentPage},totalCount=${page.totalCount},pageSize=${page.pageSize}'}"></div>
        </div>
    </div>
    <div th:replace="inc/footer::footer"/>
</div>
<script type="text/javascript" th:inline="javascript" src="/js/list.js"></script>

</body>
</html>
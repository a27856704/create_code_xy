<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head th:replace="inc/header::header"/>
<body>
<div class="container_main">
    <div th:replace="inc/top::top"/>
    <div th:replace="inc/menu::menu(${r'${menuModelName}'},${r'${menuModelPage}'})"/>
    <div class="container_box">
        <div class="container_block">
            <div class="container_title" th:text="${r'${menuModel}'}">模块</div>
            <div class="container_tab">
                <ul>
                    <li class="active">
                        <a href="#" th:text="${r'${listTitle==null?'+'\'列表\''+':listTitle}'}">${tableRemarks}列表</a>
                    </li>
                </ul>
                <ul>
                    <li>
                        <a class="info_link" th:href="${r'${addPage}'}"
                           th:if="${r'${T('+manageController+').contains(session?.rights_session,addPage)}'}">+
                            添加</a>
                    </li>
                </ul>
            </div>
            <div class="search_box">
                <form id="searchForm" th:action="${r'${listPage}'}" method="post">
                    <input class="search_page" type="hidden" id="orderBy" name="orderBy" th:value="${r'${search.orderBy}'}"/>
                    <input class="search_page" type="hidden" id="orderDesc" name="orderDesc" th:value="${r'${search.orderDesc}'}"/>

                    <input class="search_page" type="hidden" th:value="${r'${search.pageNumber}'}" id="pageNumber" name="pageNumber"/>
                    <ul class="container_fluid clear_box">
                        <#assign showSearchBtn=false>
                        <#list list as item>
                            <#if ((item.searchFlag!0)>0 && item.showListPage)>
                                <#include "search/${item.inputTypeEnum}.ftl">
                                <#assign showSearchBtn=true>
                            </#if>
                        </#list>
                        <#if showSearchBtn>
                        <li class="col_3 row search_button">
                            <button class="primary_btn" id="searchBtn" type="button">搜索</button>
                        </li>
                        </#if>
                    </ul>
                </form>
            </div>
            <div class="list_table">
                <table role="table">
                    <thead>
                        <tr>
                            <th role="gridcell" width="auto">编号</th>
                            <#list list as item>
                                <#if (item.showListPage!false)>
                            <th role="gridcell" width="auto"  class="orderClass" field="${item.dbName}">${item.descName}</th>
                                </#if>
                            </#list>
                            <th role="gridcell" width="150">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr th:each="item,stat:${r'${list}'}">
                            <td role="row">
                                <a th:href="${r'${detailPage}'}+'/'+${r'${item.id}'}" th:text="${r'${stat.count+page.begin}'}" th:title="查看详情"></a>
                            </td>


                            <#list list as item>
                                <#if (item.showListPage!false)>
                                    <#if item.javaType!='Date'>
                                        <#if item.valueList?? && (item.valueList?size > 0)>
                            <td role="row" th:text="${r'${item.'+item.name+'Desc}'}">${item.nameDesc!''}</td>
                                        <#else>
                            <td role="row" th:text="${r'${item.'+item.name+'}'}">${item.nameDesc!''}</td>
                                        </#if>
                                    <#else>
                            <td role="row" th:text="${r'${#dates.format(item.'+item.name+',\'yyyy-MM-dd HH:mm:ss\')}'}">${item.nameDesc!''}</td>
                                    </#if>
                                </#if>
                            </#list>
                            <td role="row">
                                <a th:if="${r'${T('+manageController+').contains(session?.rights_session,deleteAction)}'}"
                                   class="danger_link" href="#" onclick="dataChange(this);"
                                   th:attr="${r'data-url=${deleteAction},data-id=${item.id},data-confirmTxt=@{确定要删除吗},data-showTxt=@{删除成功},data-type=post'}"
                                >删除</a>
                                <a th:if="${r'${T('+manageController+').contains(session?.rights_session,modPage)}'}"
                                   class="warn_link" th:href="${r'${modPage}'}+'/'+${r'${item.id}'}">修改</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="pagers" id="pager"
                     th:attr="${r'data-current-page=${page.currentPage},data-total-page=${page.totalPage}'}"></div>
            </div>
        </div>
        <div th:replace="inc/footer::footer"/>
    </div>
</div>
</body>
</html>
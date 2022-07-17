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
                    <li class="active"><a href="#" th:text="${'修改密码'}">修改密码</a></li>
                </ul>
            </div>
            <div class="post_box">
                <form id="postForm" th:action="${r'${action}'}" method="post">
                    <div class="container_fluid clear_box">
                        <div class="row col_12">
                            <div class="form_control">
                                <label>原密码</label>
                                <div class="form_input">
                                    <input type="password"

                                           javatype="string"
                                           th:name="oldPassword" th:id="oldPassword" autocomplete="off" placeholder="请输入原密码"
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="row col_12">
                            <div class="form_control">
                                <label>新密码</label>
                                <div class="form_input">
                                    <input type="password"
                                           javatype="string"
                                           th:name="password" th:id="password" autocomplete="off" placeholder="请输入密码"
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="row col_12">
                            <div class="form_control">
                                <label>密码确认</label>
                                <div class="form_input">
                                    <input type="password"
                                           javatype="string"
                                           th:name="passwordConfirm" th:id="passwordConfirm" autocomplete="off"
                                           placeholder="请输入密码"
                                    />
                                </div>
                            </div>
                        </div>



                        <div class="row col_12">
                            <div class="form_control">
                                <label></label>
                                <div class="form_input">
                                    <p class="button primary_btn"
                                       th:attr="data-success-link=${r'${listPage}'}"
                                       data-msg-text="密码修改成功" id="postBtn"><span class="loading_icon"></span>提交</p>
                                    &nbsp;&nbsp;
                                    <p class="button cancel_btn reset_btn">重置</p>
                                    &nbsp;&nbsp;
                                    <p class="button info_btn" onclick="javascript:history.go(-1);">返回</p>

                                </div>
                            </div>
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
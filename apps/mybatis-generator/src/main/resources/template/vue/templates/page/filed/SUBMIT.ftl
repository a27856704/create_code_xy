                            <div class="form_control">
                                <label></label>
                                <div class="form_input">
                                    <p class="button primary_btn"
                                       th:attr="data-success-link=${r'${listPage}'}"
                                       data-msg-text="${add?string('添加成功','修改成功')}" id="postBtn"><span class="loading_icon"></span>提交</p>
                                    &nbsp;&nbsp;
                                    <p class="button cancel_btn reset_btn">重置</p>
                                    &nbsp;&nbsp;
                                    <p class="button info_btn" onclick="javascript:history.go(-1);">返回</p>

                                </div>
                            </div>

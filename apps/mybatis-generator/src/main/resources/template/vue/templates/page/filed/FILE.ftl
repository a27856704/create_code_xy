                            <div class="big_form_control">
                                <label>${item.descName}</label>
                                <div class="big_form_input">
                                    <div class="wu_uploader_list">
                                        <div class="wu_uploader_item"
                                             th:if="${r'${domain.'+item.name+'!=null}'}"
                                             th:each="item:${r'${#strings.listSplit(domain.'+item.name+',\''+','+'\')}'}">
                                            <div class="wu_uploader_thumb icon_font">
                                                <img th:src="${r'${imgWebSite+'+'\'/\''+'+item}'}"
                                                     th:if="${r'${T('+manageController+').isImgPrefix(item)}'}">

                                                <img src="/images/default_uploader_img.png"
                                                     th:if="${r'${!T('+manageController+').isImgPrefix(item)}'}">

                                            </div>
                                            <div class="wu_uploader_progress">
                                                <div class="wu_upload_state"><span th:attr="title=${r'${item}'}"
                                                                                   th:text="${r'${item}'}"></span><span
                                                            class="state icon_font success">已上传文件</span></div>
                                                <div class="progress progress-striped active">
                                                    <div class="progress-bar" role="progressbar" style="width: 100%"></div>
                                                </div>
                                            </div>
                                            <input name="${item.name}" type="hidden" th:value="${r'${item}'}">
                                        </div>
                                    </div>
                                    <div class="wu_uploader_btns">
                                        <div class="wu_uploader_picker" data-filename="${item.name}"
                                             data-savepath="${item.entityName}"
                                             data-multiple="${(item.inputType==9)?string('true','false')}">选择文件
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="big_form_control">
                                <label>${item.descName}</label>
                                <div class="big_form_input">
                                    <div class="wu_uploader_list">
                                        <div class="wu_uploader_item"
                                             th:if="${r'${domain.'+item.name+'!=null}'}"
                                             th:each="item:${r'${#strings.listSplit(domain.'+item.name+',\''+','+'\')}'}">
                                            <div  style="width: 100px;height: 100px;">
                                                <a th:href="${r'${imgWebSite+'+'\'/\''+'+item}'}" target="_blank" >
                                                    <img th:src="${r'${imgWebSite+'+'\'/\''+'+item}'}"
                                                         th:if="${r'${T('+manageController+').isImgPrefix(item)}'}"/>
                                                </a>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>

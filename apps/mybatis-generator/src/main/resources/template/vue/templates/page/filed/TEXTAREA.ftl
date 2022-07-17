                                <div class="big_form_control">
                                    <label for="${item.name}">${item.descName}</label>
                                    <div class="big_form_input">
                                        <textarea name="${item.name}" id="${item.name}" type="text"
                                        autocomplete="off"
                                        javatype="${item.javaType?lower_case}"
                                        <#if item.need> data-verify data-verify-rules="{required:true}"
                                        data-verify-message="{required:'${item.descName}不能为空'}"
                                        </#if>
                                        th:text="${r'${domain.'+item.name+'}'}"
                                        placeholder="请输入${item.descName}">
                                        </textarea>
                                    </div>
                                </div>
                                
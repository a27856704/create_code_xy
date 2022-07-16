                            <div class="form_control">
                                <label for="${item.name}">${item.descName}</label>
                                <div class="form_input">
                                    <input class="Wdate date" th:name="${item.name}" th:id="${item.name}" type="text"
                                           placeholder="请输入${item.descName}" autocomplete="off"
                                           javatype="${item.javaType?lower_case}"
                                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"
                                           th:value="${r'${#dates.format(domain.'+item.name+',\'yyyy-MM-dd HH:mm:ss\')}'}"
                                            <#if item.need> data-verify data-verify-rules="{required:true}"
                                        data-verify-message="{required:'${item.descName}不能为空'}"
                                            </#if>/>
                                </div>
                            </div>

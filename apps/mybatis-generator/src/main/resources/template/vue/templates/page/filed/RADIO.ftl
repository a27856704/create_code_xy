                            <div class="form_control">
                                <label for="${item.name}">${item.descName}</label>
                                <div class="form_input">
                                    <#if item.valueList?? && (item.valueList?size > 0) >
                                       <#-- <#list item.valueList as oneItem>-->
                                    <label th:each="${item.name+'Item:'+r'${'+item.name+'List}'}">
                                        <input type="radio"
                                               javatype="${item.javaType?lower_case}"
                                               th:name="${item.name}" th:value="${r'${'+item.name+'Item.type}'}"
                                               th:checked="${r'${((domain.'+item.name +'!=null)?domain.'+item.name+'+\'\''+':\''+(item.defaultValue!'')+'\')=='+item.name+'Item.type'+'+\'\'}'}"
                                               th:text="${r'${'+item.name+'Item.desc}'}"/>
                                    </label>
                                        <#--</#list>-->
                                    </#if>
                                </div>
                            </div>

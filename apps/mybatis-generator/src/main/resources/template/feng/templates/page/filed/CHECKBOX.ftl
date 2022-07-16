                            <div class="form_control">
                                <label>${item.descName}</label>
                                <div class="form_input">
                                    <#if item.valueList?? && (item.valueList?size > 0)>
                                        <#--<#list item.valueList as oneItem>-->
                                    <label th:each="${item.name+'Item:'+r'${'+item.name+'List}'}">
                                        <input type="checkbox" th:name="${item.name}s" th:value="${r'${'+item.name+'Item.type}'}" th:text="${r'${'+item.name+'Item.desc}'}"
                                               javatype="${item.javaType?lower_case}"
                                                <#if item.searchFlag==7>
                                            th:checked="${r'${T('+NumberUtil+').bitwise(domain.'+item.name+','+item.name+'Item.type)}'}"
                                        <#else>
                                            <#if item.javaType!="String">
                                                th:checked="${r'${((domain.'+item.name +'!=null)?domain.'+item.name+':\''+(item.defaultValue!'')+'\')'+'+\'\''+'==\''+ item.name+'Item.type\'}'}"
                                            <#else>
                                                th:checked="${r'${#strings.contains(((domain.'+item.name +'!=null)?domain.'+item.name+':\''+(item.defaultValue!'')+'\'),\','+ item.name+'Item.type,\')}'}"
                                            </#if>
                                                </#if>/>
                                    </label>
                                        <#--</#list>-->
                                    </#if>
                                </div>
                            </div>

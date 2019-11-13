<div class="form_control">
    <label>${item.descName}</label>
    <div class="form_input">
        <#if item.valueList?? && (item.valueList?size > 0)>
            <#list item.valueList as oneItem>
                <label>
                    <input type="checkbox" th:name="${item.name}s" th:value="${oneItem.value}" th:text="${oneItem.desc}"
                           javatype="${item.javaType?lower_case}"
                            <#if item.searchFlag==7>
                        th:checked="${r'${T('+NumberUtil+').bitwise(domain.'+item.name+','+oneItem.value+')}'}"
                    <#else>
                        <#if item.javaType!="String">
                            th:checked="${r'${((domain.'+item.name +'!=null)?domain.'+item.name+':\''+(item.defaultValue!'')+'\')'+'+\'\''+'==\''+ oneItem.value+'\'}'}"
                        <#else>
                            th:checked="${r'${#strings.contains(((domain.'+item.name +'!=null)?domain.'+item.name+':\''+(item.defaultValue!'')+'\'),\','+ oneItem.value+',\')}'}"
                        </#if>
                            </#if>/>
                </label>
            </#list>
        </#if>
    </div>
</div>
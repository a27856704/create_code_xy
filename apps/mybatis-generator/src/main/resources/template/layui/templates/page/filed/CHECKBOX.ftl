<div class="layui-form-item">
    <label class="layui-form-label">${item.descName}</label>
    <div class="layui-input-block">
        <#if item.valueList?? && (item.valueList?size > 0)>
            <#list item.valueList as oneItem>
                <input th:type="checkbox" th:name="${item.name}" th:value="${oneItem.value}" th:title="${oneItem.desc}"
                       lay-skin="primary" javaType="${item.javaType}"

                        <#if item.searchFlag==7>
                            th:checked="${r'${T('+NumberUtil+').bitwise(domain.'+item.name+','+oneItem.value+')}'}"
                        <#else>
                            <#if item.javaType!="String">
                                th:checked="${r'${((domain.'+item.name +'!=null)?domain.'+item.name+':\''+(item.defaultValue!'')+'\')'+'+\'\''+'==\''+ oneItem.value+'\'}'}"
                            <#else>
                                th:checked="${r'${#strings.contains(((domain.'+item.name +'!=null)?domain.'+item.name+':\''+(item.defaultValue!'')+'\'),\','+ oneItem.value+',\')}'}"
                            </#if>

                        </#if>
                >
            </#list>
        </#if>
    </div>
</div>

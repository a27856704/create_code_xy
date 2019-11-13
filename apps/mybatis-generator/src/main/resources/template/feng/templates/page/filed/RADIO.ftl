<div class="form_control">
    <label for="${item.name}">${item.descName}</label>
    <div class="form_input">
        <#if item.valueList?? && (item.valueList?size > 0) >
            <#list item.valueList as oneItem>
                <label>
                    <input type="radio"
                           javatype="${item.javaType?lower_case}"
                           th:name="${item.name}" th:value="${oneItem.value}"
                           th:checked="${r'${((domain.'+item.name +'!=null)?domain.'+item.name+'+\'\''+':\''+(item.defaultValue!'')+'\')==\''+ oneItem.value+'\'}'}"
                           th:text="${oneItem.desc}"/>
                </label>
            </#list>
        </#if>
    </div>
</div>